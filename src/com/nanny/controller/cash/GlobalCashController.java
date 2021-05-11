package com.nanny.controller.cash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.nanny.biz.cashrec.UserCashRecBiz;
import com.nanny.biz.user.UserShopCarBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserChargeRec;
import com.zhuoan.util.HttpReqUtil;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.Configure;
import com.zhuoan.util.weixin.HttpTookit;
import com.zhuoan.util.weixin.JsApiPay;
import com.zhuoan.util.weixin.WeiXinPayUtil;
import com.zhuoan.util.weixin.XMLParser;

/**
 * 全局充值提现控制
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class GlobalCashController {
	
	@Resource(name="userCashRecBiz")
	private UserCashRecBiz g_CashBiz;
	@Resource
	private UserShopCarBiz g_ShopCarBiz;

	/**
	 * 创建微信支付订单
	 * @param request
	 * @param response
	 * @return
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	@RequestMapping("pay/weixinpay")
	public ModelAndView createPayOrder(HttpServletRequest request,HttpServletResponse response) throws ParserConfigurationException, IOException, SAXException
	{
		Dto.writeLog("========== into global weixin charge method ========");
		//mall远程充值用户信息
		JSONObject louser=JSONObject.fromObject(request.getParameter("louser"));
		
		ModelAndView mav=new ModelAndView("wxpay");
		HttpSession session=request.getSession();
		//0.获得session中的用户信息与订单基础信息
		Double charge=session.getAttribute(Dto.MONEY_OF_CHARGE)==null?0:(Double) session.getAttribute(Dto.MONEY_OF_CHARGE);//充值金额
		String orderCode="CH"+TimeUtil.getNowDate("yyyyMMddHHmmssSS");//订单号
		
		//mall
		if(session.getAttribute(Dto.LOGIN_USER)==null){
			session.setAttribute(Dto.LOGIN_USER, louser);
			charge=Double.valueOf(request.getParameter("total"));
		}
		
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		if(userMsg!=null && charge>0)
		{
			//1.组织充值记录数据
			UserChargeRec bean=new UserChargeRec();
			bean.setChargeCode(orderCode);
			bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
			bean.setMoney(charge);
			bean.setStatus(Dto.CHARGE_UNPAY);
			bean.setUserId(userMsg.getLong("userID"));
			
			//2.添加充值记录
			g_CashBiz.addUserChargeBiz(bean);
			
			//3.统一下单
			String payID=createUrl(orderCode, request, 0,userMsg);
			String jsApiParameters = JsApiPay.getJsApiParameters(payID);
			
			Dto.writeLog("========== end method jsApiParameters is:"+jsApiParameters+" ========\n");
			
			//4.跳转页面
			session.removeAttribute(Dto.MONEY_OF_CHARGE);
			mav.addObject("orderCode", orderCode);
			mav.addObject("jsApiParameters", jsApiParameters);
		}
		else
			mav.addObject("error", "登录超时");
		
		return mav;
	}
	
	

	/**
	 * 回调函数
	 * @param request
	 * @param response
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	@RequestMapping("callbackfun")
	public void callbackfun(HttpServletRequest request,HttpServletResponse response) throws ParserConfigurationException, IOException, SAXException{
		String orderCode=request.getParameter("product_id");
		JSONObject msg=new JSONObject();
		
		System.out.println("========== into order confirm callback method,ordercode is:"+orderCode+" ========\n");
		
		//0.查询订单支付结果
		if(payQuery(orderCode, request)){
			//1.根据订单号查询下单记录
			JSONObject chargeRec=g_CashBiz.getChargeRec(orderCode);
			if(chargeRec.getInt("status")!=Dto.CHARGE_DONE)
			{
				System.out.println("check succeed change order.... orderdet is "+chargeRec.toString()+"\n");
				//2.更新订单状态&用户余额
				g_CashBiz.updateStaAfterCharge(chargeRec);
				msg.element("msg", "充值成功");
				msg.element("path",(String)request.getSession().getAttribute(Dto.CALL_BACK_URL));
			}
			System.out.println("========== end method bakdata is:"+msg.toString()+" ========\n");	
		}else
			msg.element("msg", "充值失败");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 支付成功返回通知
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	@RequestMapping("payNotify")
	public void payNotify(HttpServletRequest request,HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException{
		Dto.printMsg(response, "success");
		
		Dto.writeLog("into weixin payNotify method\n");
		
		//0.读取传入参数
		BufferedReader input=new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line=null;
		String result = "";
		try {
			while((line=input.readLine())!=null)
				result+=line+"\r\n";
		}catch(Exception e){
			e.printStackTrace();
		}finally{input.close();}
		//1.xml转化
		Map<String,Object> mapXML = XMLParser.getMapFromXML(result);
		String orderCode=(String) mapXML.get("out_trade_no");
//		System.out.println("payNotify xml data is "+mapXML.toString()+"\n");
		Dto.writeLog("payNotify xml data is "+mapXML.toString()+"\n");
		
		//2.查询订单支付结果
		if(payQuery(orderCode, request)){
			//3.根据订单号查询下单记录
			JSONObject chargeRec=g_CashBiz.getChargeRec(orderCode);
			if(chargeRec.getInt("status")!=Dto.CHARGE_DONE)
			{
//				System.out.println("check succeed change order.... orderdet is "+chargeRec.toString()+"\n");
				Dto.writeLog("check succeed change order.... orderdet is "+chargeRec.toString()+"\n");
				//4.更新订单状态&用户余额
				g_CashBiz.updateStaAfterCharge(chargeRec);
				
				//5.自动付款
				autoPayment(chargeRec);
			}
		}
	}
/**********************************************************************************************************************************************************/	
	/**
	 * 创建支付url
	 * @param code
	 * @param request
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private String createUrl(String code,HttpServletRequest request,int type,JSONObject userMsg) throws ParserConfigurationException, IOException, SAXException
	{
		//0.根据订单号查询下单记录
		JSONObject chargeRec=g_CashBiz.getChargeRec(code);
		if(request.getSession().getAttribute(Dto.WEIXIN_USER_OPENID)!=null)
			chargeRec.element("openid", request.getSession().getAttribute(Dto.WEIXIN_USER_OPENID));
		else
			chargeRec.element("openid", userMsg.getString("originID"));
		//1.生成支付链接
		String XMLData=type==1?WeiXinPayUtil.createXMLData(chargeRec,request,Configure.PAY_BY_QRCODE):
							   WeiXinPayUtil.createXMLData(chargeRec,request,Configure.PAY_BY_JSAPI);
		
		Dto.writeLog("XMLData:"+XMLData);
		
		String resultXML = HttpTookit.doPost(Configure.UNIFIEDORDER_API_URL, XMLData);
		String url="微信支付错误";
		String prepay_id="";
		
		Dto.writeLog("resultXML:"+resultXML);
		
		if(resultXML.indexOf("FAIL") == -1){
			Map<String,Object> mapXML = XMLParser.getMapFromXML(resultXML);
			if(mapXML.containsKey("code_url"))
				url=(String) mapXML.get("code_url");
			prepay_id=(String) mapXML.get("prepay_id");
		}
		return type==1? url : prepay_id;
	}
	
	/**
	 * 查询支付记录
	 * @param code
	 * @param request
	 * @return
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	private boolean payQuery(String code,HttpServletRequest request) throws ParserConfigurationException, IOException, SAXException{
		//0.组织数据 查询支付记录
		String XMLData=WeiXinPayUtil.createQueryXMLData(code, request);
		String resultXML = HttpTookit.doPost(Configure.ORDERQUERY_API_URL, XMLData);
		
		Dto.writeLog("resultXML:"+resultXML);
		
		if(resultXML.indexOf("FAIL") == -1){
			Map<String,Object> mapXML = XMLParser.getMapFromXML(resultXML);
			String return_code=(String) mapXML.get("return_code");//返回代码
			String result_code=(String) mapXML.get("result_code");//结果代码
			String trade_state=(String) mapXML.get("trade_state");//支付结果代码
			
			Dto.writeLog("resultCode is:return_code="+return_code+"&result_code="+result_code+"&trade_state="+trade_state);
			
			if(("SUCCESS").equals(return_code) && ("SUCCESS").equals(result_code) && ("SUCCESS").equals(trade_state))
				return true;
			else
				return false;
		}else
			return false;
	}
	
	/**
	 * 订单自动支付
	 * @param chargeRec
	 * @throws UnsupportedEncodingException 
	 */
	private void autoPayment(JSONObject chargeRec) throws UnsupportedEncodingException{
		//0.获得支付信息记录
		JSONObject payMsg=g_ShopCarBiz.getUserPayMsg(chargeRec.getLong("userID"),chargeRec.getDouble("money"));
		if(payMsg!=null)
		{
			Map<String,String> param=new HashMap<String,String>();
			param.put("oridarry", payMsg.getString("data"));
			param.put("userID", payMsg.getString("userID"));
			HttpReqUtil.doPost("http://119.29.154.90/nanny/PayOrder.html", param, null, "utf-8");
		}
	}
}
