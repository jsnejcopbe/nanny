package com.nanny.controller.shop.orders;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.global.voucher.voucherBiz;
import com.nanny.biz.shop.ShopPrintDeviceBiz;
import com.nanny.biz.shop.orders.Refund;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.TimeUtil;

/**
 * 商户订单管理控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
@Controller
public class ShopOrderController {
	//载入资源
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopMsgBiz;
	@Resource
	private Refund g_Refund;
	@Resource(name="shopPrintDeviceBiz")
	private ShopPrintDeviceBiz g_DeviceBiz;
	
	@Resource
	private voucherBiz vcb;
	
	/**
	 * 获得商户订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value={"shop/order-{page:\\d*}"})
	public ModelAndView getShopOrders(@PathVariable int page,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传递参数
		HttpSession session=request.getSession();
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		String queryInput=request.getParameter("query");//输入查询
//		String orderCode=request.getParameter("orderCode");//订单编号
//		String userTel=request.getParameter("userTel");//下单用户电话
		String sendType=request.getParameter("type");
		String status=request.getParameter("sta");//订单状态
		
		//1.组织查询参数
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(page);
		pageUtil.setPageSize(20);
		JSONObject condtion=new JSONObject();
		condtion.element("orderCode", queryInput==null?"":queryInput);
		condtion.element("userName", queryInput==null?"":queryInput);
		condtion.element("userTel", queryInput==null?"":queryInput);
		condtion.element("status", status==null?"":status);
		condtion.element("shopID", userMsg.getLong("shopID"));
		
		//2.查询订单信息
		JSONObject orderData=g_OrderBiz.getOrdersIdByCon(condtion, pageUtil);
		orderData.element("size", 20);
		JSONArray array=orderData.getJSONArray("data");
		JSONArray  data=vcb.doVoucher(array);
		
		orderData.element("data", data);
		//3.数据处理
		orderData=createData(orderData);
		
		//4.跳转
		if(sendType==null)
		{
			ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopOrderList");
			mav.addObject("orderData", orderData);
			mav.addObject("sta", status);
			mav.addObject("queryInput", queryInput==null?"":queryInput);
			return mav;
		}else{
			Dto.printMsg(response, orderData.toString());
			return null;
		}
	}

	/**
	 * 订单确认(发货)
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @memo  发送站内信
	 */
	@RequestMapping("shop/orderconfirm")
	public void orderConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONObject msg=new JSONObject();
		
		//0.获得传入参数
		Long orderID=Long.valueOf(request.getParameter("orderID"));
		int status=Integer.valueOf(request.getParameter("sta"));
		String isForce=request.getParameter("isForce");
		
		//1.查询该订单是否有取消申请
		if(g_OrderBiz.getRefundCountByOrderID(orderID)==0 || !("").equals(isForce))
		{
			//2.更新订单状态
			g_OrderBiz.updateOrderSta(orderID, status);
			
			//3.打印订单
			JSONObject orderMsg=printOrderTicket(orderID);
			JSONObject deviceMsg=g_DeviceBiz.getPrintMsg(orderMsg.getLong("recShopID")).getJSONObject(0);
			
//			if(deviceMsg.getInt("printerType") == Dto.PRINTER_TYPE_WIFI){
//				JSONObject result=TicketPrintUtil.printTickeyByCloud(orderMsg,deviceMsg);   //365小票打印机
//				System.out.println("back Msg is:"+result.getString("responseCode"));
//			}else if(deviceMsg.getInt("printerType") == Dto.PRINTER_TYPE_USB)
//				msg.element("orderMsg", orderMsg.toString());
			if(deviceMsg.getInt("printerType") == Dto.PRINTER_TYPE_USB)
				msg.element("orderMsg", orderMsg.toString());
			
			//4.返回消息
			msg.element("msg", "操作成功");
		}else
			msg.element("isRefund", "true");
		
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 用户退款
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @memo  发送站内信
	 */
	@RequestMapping("shop/orderrefund")
	public void orderRefund(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
//		Long orderID=Long.valueOf(request.getParameter("orderID"));
		int status=Integer.valueOf(request.getParameter("sta"));
		
		//新增-HXQ
		String shopmsg=request.getParameter("shopmsg");
		
		//1.获得订单信息
		JSONObject orderMsg=(JSONObject) request.getAttribute("orderMsg");
		
		//2.组织更新参数
		JSONObject param=new JSONObject();
		param.element("status", status);
		param.element("orderID", orderMsg.getLong("id"));
		param.element("cost", orderMsg.getDouble("totalPrice"));
		param.element("userID", orderMsg.getLong("payUserID"));
		param.element("shopID", orderMsg.getLong("recShopID"));
		param.element("orderCode", orderMsg.getString("orderCode"));
		//新增-HXQ
		if(shopmsg!=null)
			param.element("shopmsg", shopmsg);
		else
			param.element("shopmsg", "商户未填写理由");
		
		//3.更新操作
		if(("null").equals(orderMsg.getString("memo")))
			g_OrderBiz.updateOrderStaForRef(param);
		else
			g_OrderBiz.updateOrderStaForRefOfRecPay(param);
		
		//4.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "操作成功");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 拒绝用户提出的退款申请
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("shop/refuserefundapp")
	public void refundAppRefuse(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		Long orderID=Long.valueOf(request.getParameter("orderID"));
		
		//1.获得订单信息
		JSONObject orderMsg=g_OrderBiz.getOrderBaseByOrderID(orderID);
		
		//2.更新申请信息
		g_Refund.updateRefAppStaByOrderCode(orderMsg.getString("orderCode"), 2);
		
		//3.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "操作成功");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 前往订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"shop/order-det-{orderCode:\\d*}"})
	public ModelAndView toOderDetail(@PathVariable String orderCode,HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		//0.获得订单基础信息
		JSONObject orderMsg=g_OrderBiz.getOrderBaseByOrderCode(orderCode);
		orderMsg=statusDefine(orderMsg);
		orderMsg=vcb.doVoucher(new JSONArray().element(orderMsg)).getJSONObject(0);
		//1.获得订单详情
		JSONArray orderDet=g_OrderBiz.getShopOrdersByOrderID(orderMsg.getLong("id"));
		
		//2.获得店铺信息
		JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(userMsg.getLong("shopID"));
		
		//3.返回页面
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopOrderDet");
		mav.addObject("orderBase", orderMsg);
		mav.addObject("orderDet", orderDet);
		mav.addObject("shopMsg", shopMsg);
		return mav;
	}
	
	/**
	 * 查询新订单
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/ordercheckbytime")
	public void orderCheck(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject msg=new JSONObject();
		//0.获得传入的参数与session中的用户信息
//		String time=request.getParameter("time");
		JSONObject userMsg=(JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		Dto.writeLog("进入语音提醒方法，当前时间"+TimeUtil.getNowDate()+"访问IP："+Dto.getLocalIp(request)+"，查询用户json信息："+userMsg.toString());
		
		//1.查询该时间段下的新订单数量
		if(!("null").equals(userMsg.getString("shopID"))){
			int count=g_OrderBiz.getCountOfWaitDetOrders(userMsg.getLong("shopID"));
			msg.element("count", count);
		}else
			msg.element("stop", "stop");
		
		//2.返回结果
		//Dto.writeLog("查询结束，查询结果为："+msg.toString());
		
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 查询新的退款申请
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/resappcheckbytime")
	public void resAppCheck(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject msg=new JSONObject();
		//0.获得session中的用户信息
		JSONObject userMsg=(JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		//1.查询是否有新的退款申请
		if(!("null").equals(userMsg.getString("shopID"))){
			int count=g_Refund.getNewAppCount(userMsg.getLong("shopID"));
			msg.element("count", count);
		}else
			msg.element("stop", "stop");
		//2.返回结果
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 打印订单
	 * @param request
	 * @param response
	 */
	@RequestMapping("printpage")
	public ModelAndView printPage(HttpServletRequest request,HttpServletResponse response){
		//0.获得参数
		JSONObject param=JSONObject.fromObject(request.getParameter("printProMsg"));
		
		ModelAndView mav=new ModelAndView("printTicket");
		mav.addObject("printProMsg", param);
		return mav;
	}
/********************************************************************************************************************/	
	
	/**
	 * 拼接数据
	 * @param data
	 * @return
	 */
	private JSONObject createData(JSONObject data)
	{
		JSONArray orderList=data.getJSONArray("data");
		for(int i=0;i<orderList.size();i++)
		{
			JSONObject tmpObj=orderList.getJSONObject(i);
			Long orderID=tmpObj.getLong("id");
			//判断订单状态
			tmpObj=statusDefine(tmpObj);
			tmpObj.element("detList", g_OrderBiz.getShopOrdersByOrderID(orderID));
		}
		data.element("data", orderList);
		return data;
	}
	
	/**
	 * 状态判断
	 * @param obj
	 * @return
	 */
	private JSONObject statusDefine(JSONObject obj){
		if(obj.getInt("status")==Dto.STATU_WAITPAY)
			obj.element("staName", "待付款");
		else if(obj.getInt("status")==Dto.STATU_WAITSEND)
			obj.element("staName", "待发货");
		else if(obj.getInt("status")==Dto.STATU_WAITREV)
			obj.element("staName", "已发货");
		else if(obj.getInt("status")==Dto.STATU_DONE)
			obj.element("staName", "交易完成");
		else if(obj.getInt("status")==Dto.STATU_REFUND)
			obj.element("staName", "订单取消");
		return obj;
	}
	
	/**
	 * 打印订单小票
	 * @param orderID
	 */
	private JSONObject printOrderTicket(Long orderID)
	{
		JSONObject baseOrder=g_OrderBiz.getOrderBaseByOrderID(orderID);
		if(("null").equals(baseOrder.getString("memo")))
			baseOrder.element("memo", "在线付款");
		JSONArray orderDet=g_OrderBiz.getShopOrdersByOrderID(orderID);
		baseOrder.element("detList", orderDet);
		baseOrder.element("proCount", orderDet.size());
		return baseOrder;
	}
}
