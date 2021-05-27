package com.nanny.controller.global;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.nanny.biz.global.aopdel.GlobalCashRecBiz;
import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.login.LoginBiz;
import com.nanny.biz.login.RegisterBiz;
import com.nanny.biz.product.ProductBiz;
import com.nanny.biz.shop.ShopBasis;
import com.nanny.biz.user.UserViewShopRecBiz;
import com.nanny.biz.user.UsersBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserViewShopRec;
import com.zhuoan.util.CookiesUtil;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.Doweixin;
import com.zhuoan.util.weixin.WeixinServletTokenCheckUtil;
import com.zhuoan.util.weixin.WeixinUserMsgPush;
import com.zhuoan.util.weixin.XMLParser;

/**
 * 微信公众号消息接收控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class GlobalWeiXinMsgRecController extends GlobalCommonExtend {
	
	//载入资源
	@Resource
	private ShopBasis g_ShopMsgBiz;
	@Resource(name="userViewShopRecBiz")
	private UserViewShopRecBiz g_ViewRecBiz;
	@Resource
	private UsersBiz g_UserBiz;
	@Resource
	private ProductBiz g_ProBiz;
	@Resource(name="loginBiz")
	private LoginBiz g_LoginBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopBiz;
	@Resource(name="registerBiz")
	private RegisterBiz g_RegisterBiz;
	@Resource(name="globalCashRecBiz")
	private GlobalCashRecBiz g_CashBiz;
	
	/**
	 * 接收微信服务器的信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@RequestMapping("wxmsgrec")
	public void receiveWeiXinMsg(HttpServletRequest request,HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException{
		
		//0.读取传入参数
		String result =Dto.receivePostMsg(request.getInputStream());
		Dto.writeLog("into wxservicemsg recevie now time is: "+TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss")+" and receive msg is: "+ result);
		Dto.printMsg(response, "success");
		
		
		//1.xml转化
		Map<String,Object> mapXML = XMLParser.getMapFromXML(result);
		
		//2.判断类型
		String msgType=(String) mapXML.get("MsgType");//接收消息类型  普通:事件
		if(("event").equals(msgType))//接收消息为事件推送
		{
			String event=(String) mapXML.get("Event");
			if(("subscribe").equals(event) || ("SCAN").equals(event))//当推送事件为扫码关注或扫码进入公众号时
			{
				//当关注类型为扫码关注时
				if(mapXML.containsKey("Ticket")){
					String eventValue=(String) mapXML.get("EventKey");
					
					Dto.printMsg(response, msgActionForQrCode(request,response,eventValue, mapXML, event));
				}else{
					String passWord=createNewUserFromWeiXin(request,response,(String) mapXML.get("FromUserName"),null,null);//注册新用户
					
					if(passWord==null)
						Dto.printMsg(response,createWeiXinTextMsg("客官怎么才来呀，小掌在这里等候您多时了。", (String) mapXML.get("FromUserName")));
					else
						Dto.printMsg(response,createWeiXinTextMsg("客官怎么才来呀，小掌在这里等候您多时了。您的初始密码为"+passWord+"请及时修改", (String) mapXML.get("FromUserName")));
				}
			}
			else if(("LOCATION").equals(event))//当推送事件为用户上报地理位置信息时
			{
				String lonStr=(String)mapXML.get("Latitude")+","+(String)mapXML.get("Longitude");
				String openId=(String) mapXML.get("FromUserName");
				addNewLocation(openId, lonStr);
			}
		}
		else if(("text").equals(msgType) || ("voice").equals(msgType)){//接收消息为语音/文字查询
			//用户发起文字搜索或语音搜索
			//获得用户查询关键字与openID
			String keyWord=(String) (mapXML.containsKey("Recognition")?mapXML.get("Recognition"):mapXML.get("Content"));
			String openId=(String) mapXML.get("FromUserName");
			String backMsg=msgActionForTextQuery(keyWord, openId);
			
			System.out.println("voice check xml data is:"+backMsg.toString());
			
			if(backMsg.indexOf("xml")!=-1)
				Dto.printMsg(response, backMsg);
			else
				Dto.printMsg(response, createWeiXinTextMsg(backMsg, openId));
		}else
			Dto.printMsg(response, "received!");//返回消息，让微信停止推送
		
	}
	
	
//		服务器配置tocken验证
//		String signature=request.getParameter("signature");//验证签名
//		String timestamp=request.getParameter("timestamp");//时间戳
//		String nonce=request.getParameter("nonce");   
//		String echostr=request.getParameter("echostr"); 
//		
//		System.out.println("the sign send by weixin is "+signature+"\n");
//		
//		if(WeixinServletTokenCheckUtil.checkSignature(signature, timestamp, nonce))
//			Dto.printMsg(response, echostr);
//	}
/******************************************************辅助方法*********************************************************************/	
	
	/**
	 * 推送回复动作-----文字/语音查询
	 * @param value
	 * @param openId
	 * @return
	 */
	private String msgActionForTextQuery(String value,String openId){
		//0.获得用户位置信息&用户基本信息
		JSONObject userMsg=g_LoginBiz.getLoginUserMsgByOpenId(openId);
		JSONObject locale=g_ViewRecBiz.getViewRecByOpenID(openId);
		if(locale==null || ("null").equals(locale.getString("memo")))
			return "您尚未授权本公众号获取您的位置信息";
		else{
			JSONObject msg=super.helpGetPostion(locale.getString("memo"), null);
			
			if(msg.containsKey("addID")){
				//1.查询获得的地址ID下的商户列表
				JSONArray shopList=g_ShopBiz.getShopIDListByUserAdd(msg.getLong("addID"));
				
				Long shopID;
				if(userMsg!=null)
					shopID=getShopID(shopList, ("null").equals(userMsg.getString("recShopID"))?null:userMsg.getLong("recShopID"), locale);
				else
					shopID=getShopID(shopList, null, locale);
				
				//2.根据输入商品名查询指定商户下的商品
				if(shopID!=null)
				{
					JSONArray proList=g_ProBiz.getProMsgByCheckName(value, shopID);
					
					for(int i=0;i<proList.size();i++)
					{
						proList.getJSONObject(i)
							   .element("title", proList.getJSONObject(i).getString("name"))
							   .element("des", "")
							   .element("pic", "http://www.zsbaomu.com"+proList.getJSONObject(i).getString("cover"))
							   .element("url", "http://www.zsbaomu.com/nanny/pro-det-"+proList.getJSONObject(i).getLong("proID")+".html");
					}
					
					JSONObject lastMsg=new JSONObject();
					if(proList.size()!=0)
						lastMsg.element("title", "去附近的商家看看").element("des", "").element("pic", "").element("url", "http://www.zsbaomu.com/nanny/shoplist.html");
					else
						lastMsg.element("title", "啊哦，指定商家找不到您查询的商品，去附近的商家看看").element("des", "").element("pic", "http://www.zsbaomu.com/nanny/images/defalut.jpg").element("url", "http://www.zsbaomu.com/nanny/shoplist.html");
					
					proList.add(lastMsg);
					return createWeixinMsg(proList, openId);
				}else
					return "您所在的片区尚未开通服务";
			}else
				return "您所在的片区尚未收录到系统中";
		}
	}
	
	/**
	 * 推送回复动作-----二维码扫描事件
	 * @param requset
	 * @param eventValue
	 * @param mapXML
	 * @param event
	 * @return
	 */
	private String msgActionForQrCode(HttpServletRequest requset,HttpServletResponse response,String eventValue,Map<String,Object> mapXML,String event){
		String userID=eventValue.indexOf("qrscene")==-1?eventValue:eventValue.split("_")[1];
		
		JSONArray shopMsg=JSONArray.fromObject(g_ShopMsgBiz.baseinfo_init(userID));

		//3.添加新纪录&推荐店铺
		if(("subscribe").equals(event))
			g_UserBiz.updateUserRecShop((String) mapXML.get("FromUserName"), shopMsg.getJSONObject(0).getLong("id"));
		addNewRec(shopMsg.getJSONObject(0), mapXML);
		
		JSONArray msgData=new JSONArray();
		//4.添加新用户
		String password=createNewUserFromWeiXin(requset,response,(String) mapXML.get("FromUserName"),shopMsg.getJSONObject(0).getLong("id"),event);//注册新用户
		
		//5.返回消息
		JSONObject tmpObj=shopMsg.getJSONObject(0);
		tmpObj.element("title", "感谢您关注掌上保姆社区服务");
		tmpObj.element("des", "您进入的店铺是"+tmpObj.getString("shop_name")+",请点击开始购物");
		tmpObj.element("pic", "http://www.zsbaomu.com"+tmpObj.getString("shop_icon"));
		tmpObj.element("url", "http://www.zsbaomu.com/nanny/store-"+tmpObj.getString("id")+".html");
		msgData.add(tmpObj);
		
		if(password!=null && !("reFocus").equals(password))
			msgData.add((new JSONObject()).element("title", "您的登录密码是"+password+",请及时修改")
										  .element("des", "").element("pic", "").element("url", ""));
		
		if(shopMsg.getJSONObject(0).getInt("isVouchers")!=0)
			msgData.add((new JSONObject()).element("title", "您关注的店铺赠送您价值10元的抵用券，请在您的个人中心查看")
					  					  .element("des", "").element("pic", "").element("url", ""));
		
		String xmlMsg=createWeixinMsg(msgData, (String) mapXML.get("FromUserName"));
		System.out.println("response xml data is "+xmlMsg+"\n");

		return xmlMsg;
	}
	
	/**
	 * 添加访问新记录
	 * @param mapXML
	 */
	private void addNewRec(JSONObject shopMsg,Map<String,Object> mapXML){
		UserViewShopRec bean=new UserViewShopRec();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setOpenId((String) mapXML.get("FromUserName"));
		bean.setShopId(shopMsg.getLong("id"));
		bean.setUrl("http://www.zsbaomu.com/nanny/store-"+shopMsg.getString("id")+".html");
		g_ViewRecBiz.addUserViewShopRec(bean);
	}
	
	/**
	 * 创建菜单
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("wxcreatemenu")
	public void createMenu(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		JSONObject data=new JSONObject();
		JSONArray btnArray=new JSONArray();
		//0.拼接菜单数据---按钮1
		JSONObject btn1=new JSONObject();
		btn1.element("name", "闪送超市");
		btn1.element("type", "view");
		btn1.element("url", "http://www.zsbaomu.com/nanny/supermarket.html");
		
		//1.拼接菜单数据---按钮2
		JSONObject btn2=new JSONObject();
		btn2.element("name", "购物车");
		btn2.element("type", "view");
		btn2.element("url", "http://www.zsbaomu.com/nanny/users/shopcar.html");
		
		//2.拼接菜单数据---按钮3
		JSONObject btn3=new JSONObject();
		btn3.element("name", "个人中心");
		JSONArray subBtn=new JSONArray();//拼接字按钮
		JSONObject tmpBtn=new JSONObject();
		tmpBtn.element("name", "我是买家");
		tmpBtn.element("type", "view");
		tmpBtn.element("url", "http://www.zsbaomu.com/nanny/index.html");
		subBtn.add(tmpBtn);
		JSONObject tmpBtn2=new JSONObject();
		tmpBtn2.element("name", "我是商户");
		tmpBtn2.element("type", "view");
		tmpBtn2.element("url", "http://www.zsbaomu.com/nanny/index.html");
		subBtn.add(tmpBtn2);
		JSONObject tmpBtn3=new JSONObject();
		tmpBtn3.element("name", "我是业务员");
		tmpBtn3.element("type", "view");
		tmpBtn3.element("url", "http://www.zsbaomu.com/nanny/salesman.html");
		subBtn.add(tmpBtn3);
		btn3.element("sub_button", subBtn);
		
		btnArray.add(btn1);
		btnArray.add(btn2);
		btnArray.add(btn3);
		data.element("button", btnArray);
		System.out.println("create data is "+data.toString()+"\n");
		
		//3.制作菜单
		JSONObject backmsg=WeixinUserMsgPush.createWXMenu(data);
		System.out.println("back msg is "+backmsg.toString()+"\n");
	}
	
	/**
	 * 被动回复微信消息
	 * @param shopMsg
	 * @return
	 */
	private String createWeixinMsg(JSONArray msgList,String openID){
		Timestamp time=Timestamp.valueOf(TimeUtil.getNowDate());
		String xmlTemplate="<xml>" +
						   "<ToUserName><![CDATA[%s]]></ToUserName>" +
						   "<FromUserName><![CDATA[%s]]></FromUserName>" +
						   "<CreateTime>%s</CreateTime>" +
						   "<MsgType><![CDATA[%s]]></MsgType>" +
						   "<ArticleCount>%d</ArticleCount>" +
						   "<Articles>itemList</Articles>" +
						   "</xml>";
		String itemTemplat="	<item>" +
						   "		<Title>" +
						   "		<![CDATA[%s]]>" +
						   "		</Title> " +
						   "		<Description><![CDATA[%s]]></Description>" +
						   "		<PicUrl><![CDATA[%s]]></PicUrl>" +
						   "		<Url><![CDATA[%s]]></Url>" +
						   "	</item>";
	   String backXml=String.format(xmlTemplate,openID,"zsbomu",String.valueOf(time.getTime()),"news",msgList.size());
	   String itemXml="";
	   for(int i=0;i<msgList.size();i++)
	   {
		   JSONObject tmpObj=msgList.getJSONObject(i);
		   itemXml+=String.format(itemTemplat,tmpObj.getString("title"),tmpObj.getString("des"),tmpObj.getString("pic"),tmpObj.getString("url"));
	   }
	   return backXml.replace("itemList", itemXml);
	}
	
	/**
	 * 被动回复消息（文字）
	 */
	private String createWeiXinTextMsg(String msg,String openID)
	{
		Timestamp time=Timestamp.valueOf(TimeUtil.getNowDate());
		String xmlTemplate="<xml>" +
						   "	<ToUserName>" +
						   "		<![CDATA[%s]]>" +
						   "	</ToUserName>" +
						   "	<FromUserName>" +
						   "		<![CDATA[%s]]>" +
						   "	</FromUserName>" +
						   "	<CreateTime>%s</CreateTime>" +
						   "	<MsgType><![CDATA[text]]></MsgType>" +
						   "	<Content><![CDATA[%s]]></Content>" +
						   "</xml>";
		String backXml=String.format(xmlTemplate, openID,"zsbomu",String.valueOf(time.getTime()),msg);
		return backXml;
	}
	
	/**
	 * 持久化用户地址信息
	 * @param openID
	 * @param lonStr
	 */
	private void addNewLocation(String openID,String lonStr){
		UserViewShopRec bean=new UserViewShopRec();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setMemo(lonStr);
		bean.setOpenId(openID);
		g_ViewRecBiz.addUserViewLocation(bean);
	}
	
	/**
	 * 获得指定商户
	 * @param array
	 * @param recShopID
	 * @return
	 */
	private Long getShopID(JSONArray array,Long recShopID,JSONObject localeMsg)
	{
		Long shopID=null;
		if(!("null").equals(localeMsg.getString("shopID")))
			recShopID=localeMsg.getLong("shopID");
		
		for(int i=0;i<array.size();i++)
		{
			JSONObject tmpObj=array.getJSONObject(i);
			if(recShopID==tmpObj.getLong("id"))
			{
				shopID=recShopID;
				break;
			}
		}
		
		if(shopID==null)
			shopID=array.getJSONObject(0).getLong("id");
		return shopID;
	}
	
	/**
	 * 公众号关注自动注册
	 * @param request
	 * @param recShopID
	 * @return
	 */
	private String createNewUserFromWeiXin(HttpServletRequest request,HttpServletResponse response,String openID,Long recShopID,String event){
		HttpSession session=request.getSession();
		String passWord=MathDelUtil.getRandomStr(6);
		//0.获得session中的用户信息
		JSONObject access_token=Doweixin.getAccessToken();
		JSONObject wxUserMsg=Doweixin.getUserInfoWithoutOauth(access_token.getString("access_token"), openID);
		JSONObject checkUserMsg=g_LoginBiz.getLoginUserMsgByOpenId(wxUserMsg.getString("openid"));
		
		System.out.println("get userInfo by api data is "+wxUserMsg.toString()+",event is "+event+" \n");
		
		String token=MathDelUtil.getRandomCode(10);
		if(checkUserMsg==null)
		{ 
			//1.拼接用户信息
			BaseUsers bean=new BaseUsers();
			bean.setBalance(0D);
			bean.setBirthdate(null);
			bean.setCreateTime(DateUtils.getTimestamp());
			bean.setHeadImg(wxUserMsg.getString("headimgurl"));
			bean.setId(null);
			bean.setIsAdmin(0);
			bean.setMail(null);
			bean.setNickName(wxUserMsg.getString("nickname"));
			bean.setOrigin(null);
			bean.setOriginId(wxUserMsg.getString("openid"));
			bean.setPassword(passWord);
			bean.setPoint(0);
			bean.setQq(null);
			bean.setSex(null);
			bean.setTel(null);
			bean.setRecShopId(recShopID);
			bean.setToken(token);
			//2.添加新用户
			JSONObject oUserMsg = g_RegisterBiz.addNewUser(bean);
			oUserMsg.element("shopID", "null");
			
			//存入cookies
	    	CookiesUtil.addCookie(Dto.USER_TOKEN, token, response);
			
			session.setAttribute(Dto.REGISTER_USER, oUserMsg);
			session.setAttribute(Dto.LOGIN_USER, oUserMsg);
			
			//3.返回密码
			return passWord;
		}
		else if(("subscribe").equals(event)){
			
			System.out.println("into voucher method\n");
			
			BaseUsers bean=new BaseUsers();
			bean.setId(checkUserMsg.getLong("userID"));
			bean.setRecShopId(recShopID);
			g_CashBiz.addUserVoucher(bean);
			return "reFocus";
		}
		else
			return null;
	}
}
