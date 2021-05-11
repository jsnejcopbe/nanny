package com.nanny.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.shop.ShopPrintDeviceBiz;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.biz.user.UserShopCarBiz;
import com.nanny.biz.user.UsersOrderBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseOrders;
import com.nanny.model.BaseUsers;
import com.nanny.model.ShopDiscuss;
import com.nanny.model.SysVoucher;
import com.nanny.model.SystemSetup;
import com.nanny.model.UserPayMsg;
import com.nanny.model.UserPointRedeem;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.TicketPrintUtil;
import com.zhuoan.util.TimeUtil;

/**
 * 买家个人中心订单
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Controller
public class UsersOrderController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	@Resource(name="shopPrintDeviceBiz")
	private ShopPrintDeviceBiz g_DeviceBiz;
	@Resource
	private UsersOrderBiz userOrderBiz;	
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderBiz;
	
	@Resource
	private UserShopCarBiz userShopCarBiz;
	
	/**
	 * 个人订单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/userOder.html")
	public String orderlist(@RequestParam(required = false) Integer pageIndex,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		PageUtil pageUtil = new PageUtil();
		if (pageIndex == null) {

			pageIndex = 1;
		}
		
	
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(10);
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		String status=request.getParameter("sta");//订单状态
		
		JSONObject order= userOrderBiz.doUserorder(json.getInt("userID"), status,null,pageUtil);
				
		request.setAttribute("order", order);
		request.setAttribute("sta", request.getParameter("sta"));
		int url= (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/usersOrder";
	}
	
	@RequestMapping("users/usersOrderFy.json")
	public void usersOrderFy(@RequestParam(required = false) Integer pageIndex,
			HttpServletRequest request, HttpServletResponse response)
					throws ParseException, IOException {
		PageUtil pageUtil = new PageUtil();
		
		if (pageIndex == null) {
			
			pageIndex = 1;
		}
		
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(10);
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		String status=request.getParameter("sta");//订单状态
		
		JSONObject order= userOrderBiz.doUserorder(json.getInt("userID"), status,null,pageUtil);
			JSONObject msg=new JSONObject();
			msg.element("order", order);
			msg.element("sta", request.getParameter("sta"));
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
	}
	
	
	/**
	 * 个人订单付款
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/orPayment.html")
	public void orderpayment(HttpServletRequest request,HttpServletResponse response) throws IOException {
			HttpSession session =request.getSession();
			JSONObject obj=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
			String orid=request.getParameter("orderID");
			JSONArray od=new JSONArray();
			od.add(orid);
			
			//订单ID 存入session
			request.getSession().setAttribute(Dto.ARRAY_SHOPID, od.toString());
			
		    String mae=  payment(od,obj.getLong("userID"), request, response);
		    
		 	JSONObject msg=new JSONObject();
		 	
		 	msg.element("msg", mae);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());

	}
	
	
	//购物车跳转
	@RequestMapping("users/Payment.html")
	public void adasd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session =request.getSession();
		JSONObject obj=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		String	oridarry=(String) session.getAttribute(Dto.ARRAY_SHOPID);
		String msg=payment(JSONArray.fromObject(oridarry),obj.getLong("userID"), request, response);
		if("余额不足".equals(msg)){
			
			response.sendRedirect("/nanny/pay/weixinpay.html");
		}else{
			response.sendRedirect("/nanny/users/userOder.html");
		}
		
	}
	
	
		//另类付款跳转
		@RequestMapping("/PayOrder.html")
		public void payorid(HttpServletRequest request,HttpServletResponse response) throws IOException{
			//HttpSession session =request.getSession();
			String uid=request.getParameter("userID");
			String	oridarry=request.getParameter("oridarry");
			String msg=payment(JSONArray.fromObject(oridarry),Long.valueOf(uid), request, response);
			JSONObject obj=new JSONObject();
			obj.element("msg", msg);
			Dto.printMsg(response, msg.toString());
		/*	if("余额不足".equals(msg)){
				
				response.sendRedirect("/nanny/pay/weixinpay.html");
			}else{
				response.sendRedirect("/nanny/users/userOder.html");
			}*/
			
		}
	
	//付款、支付操作
	public String payment(JSONArray orid,Long uid,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Dto.writeLog("Startpay");
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		JSONArray printList=new JSONArray();
		
		Dto.writeLog("payOrid："+orid);
		
		double TotalPrice=0;
		int point=0;
		for(int i=0;i<orid.size();i++){
			long bean=orid.getLong(i); 
			BaseOrders order=(BaseOrders) sshUtilBiz.getObjectById(BaseOrders.class, bean);
			
			//判断订单是否已经付款
			if(order.getStatus()==Dto.STATU_WAITPAY){
				//循环判断是否有积分商品
				JSONObject obj=userOrderBiz.PointGoods(bean);
				//是否有抵用卷
				if(order.getVcId()>0L){
					SysVoucher sv=(SysVoucher) sshUtilBiz.getObjectById(SysVoucher.class,order.getVcId());
					
					//总额-抵用卷
					TotalPrice=TotalPrice+obj.getDouble("gtotal")-sv.getMoney();
				}else{
					//总额
					TotalPrice=TotalPrice+obj.getDouble("gtotal");
				}
				//总积分
				point=point+obj.getInt("gpoint");
				
				
				JSONObject tmpObj=new JSONObject();
				tmpObj.element("orderID", bean);
				tmpObj.element("shopID", order.getRecShopId());
				printList.element(tmpObj);
				
			}
		}
		
		 BaseUsers user=(BaseUsers) sshUtilBiz.getObjectById(BaseUsers.class, uid);
		 SystemSetup sst=(SystemSetup) sshUtilBiz.getObjectById(SystemSetup.class, 1L);
		 
		 /*//如果积分不足，转换为余额付款
		 if(point>user.getPoint()){
			 TotalPrice=TotalPrice+(point*sst.getCash());
		 }*/
		 
		 TotalPrice=MathDelUtil.halfUp(TotalPrice);//四舍五入保留两位小数  wph
		 
		 if(TotalPrice>user.getBalance()){
			 //余额不足 跳转充值
			request.getSession().setAttribute(Dto.CALL_BACK_URL, "/nanny/users/Payment.html");
			request.getSession().setAttribute(Dto.MONEY_OF_CHARGE, TotalPrice);
			
			//订单ID 存入
			UserPayMsg upm=new UserPayMsg();
			upm.setUserId(uid);
			upm.setData(orid.toString());
			upm.setCreateTime(DateUtils.getTimestamp());
			upm.setTotalMoney(TotalPrice);
			userShopCarBiz.savePayMsg(upm);
			
			Dto.writeLog("RechargePayMoney："+TotalPrice);
			 Dto.writeLog("payEnd");
			 return "余额不足";
		 }else if(TotalPrice==0){
			 Dto.writeLog("Pay again");
			 Dto.writeLog("payEnd");
			 return "已经付过款项，请到个人中心进行查看订单";
		 }else{
			 String  msg= userOrderBiz.updateUserorder(orid,uid,TotalPrice,point);
			//System.out.println("payMoney："+TotalPrice);
			 printOrderTicket(printList);
			 Dto.writeLog("BalancePay："+TotalPrice);
			 Dto.writeLog("payEnd");
			 return msg;
		 }
		
	}
	
	
	
	
	/**
	 * 确认收货
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/confirm.html")
	public void Confirmreceipt(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String orid=request.getParameter("orderID");
		
		  String mag=  userOrderBiz.updateShoporder(Long.valueOf(orid),json.getLong("userID"));
		 
		 JSONObject msg=new JSONObject();
		 msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	
	/**
	 * 订单退款
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/orRefund.html")
	public void orderefund(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String orid=request.getParameter("orderID");
		String memo=request.getParameter("memo");
		
		
		 String mag= userOrderBiz.ordeRefund(json.getLong("userID"),Long.valueOf(orid) , memo);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	/**
	 * 订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/orDetails.html")
	public String Orderdetails(HttpServletRequest request,HttpServletResponse response) {
		String orid=request.getParameter("orid");
		PageUtil pageUtil = new PageUtil();
	
		pageUtil.setPageIndex(1);
		pageUtil.setPageSize(10);
		HttpSession session =request.getSession();
		
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		JSONObject order= userOrderBiz.doUserorder(json.getInt("userID"), null,orid,pageUtil);
		JSONArray order1=order.getJSONArray("order");
		request.setAttribute("order", order1.get(0));
		request.setAttribute("sta", request.getParameter("sta"));
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/orderDetails";
		
	}
	
	/**
	 * 订单评论
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/addDiscuss.html")
	public void shopDiscuss(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		HttpSession session =request.getSession();
		
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String con=request.getParameter("con");
		//String img=request.getParameter("img");
		String score=request.getParameter("score");
		String shopid=request.getParameter("shopID");
		
		ShopDiscuss dis=new ShopDiscuss();
		dis.setCon(con);
		//dis.setImg(img);
		dis.setUserId(json.getLong("userID"));
		dis.setScore(Integer.valueOf(score));
		dis.setShopId(Long.valueOf(shopid));
		dis.setCreateTime(DateUtils.getTimestamp());
		sshUtilBiz.saveObject(dis);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "评论完成");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
	
	/**
	 * 货到付款
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/cod.html")
	public void Cash_on_delivery(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();

		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		String	oridarry=(String) session.getAttribute(Dto.ARRAY_SHOPID);
		
		JSONArray orid=  JSONArray.fromObject(oridarry);
		
		JSONArray printList=userOrderBiz.cod(orid,json.getLong("userID"));
		
		printOrderTicket(printList);
	    response.sendRedirect("/nanny/users/userOder.html");
	}
	
	/**
	 * 用户积分兑换商品
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("users/pointexchange")
	public void userExchangeProduct(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		JSONObject data=JSONObject.fromObject(request.getParameter("dataObj"));
		HttpSession session=request.getSession();
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		//1.组织数据
		UserPointRedeem bean=new UserPointRedeem();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setDes(data.getString("des"));
		bean.setMemo("0");
		bean.setPoint(data.getInt("point"));
		bean.setProId(data.getLong("proID"));
		bean.setShopId(data.getLong("shopID"));
		bean.setStatus(Dto.USER_DEDUCT);
		bean.setUserId(userMsg.getLong("userID"));
		bean.setNumber(data.getInt("count"));
		
		//2.保存数据
		userOrderBiz.updateUserPointForExc(bean);
		JSONObject msg=new JSONObject();
		msg.element("msg", "兑换成功,您可以自行到商店领取");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 打印订单小票
	 * @param orderID
	 * @throws UnsupportedEncodingException 
	 */
	private void printOrderTicket(JSONArray printList) throws UnsupportedEncodingException
	{
		for(int i=0;i<printList.size();i++)
		{
			JSONObject obj=printList.getJSONObject(i);
			JSONObject deviceMsg=g_DeviceBiz.getPrintMsg(obj.getLong("shopID")).getJSONObject(0);
			
			if(deviceMsg.getInt("printerType") == Dto.PRINTER_TYPE_WIFI){
				JSONObject baseOrder=g_OrderBiz.getOrderBaseByOrderID(obj.getLong("orderID"));
				if(("null").equals(baseOrder.getString("memo")))
					baseOrder.element("memo", "在线付款");
				JSONArray orderDet=g_OrderBiz.getShopOrdersByOrderID(obj.getLong("orderID"));
				baseOrder.element("detList", orderDet);
				baseOrder.element("proCount", orderDet.size());
				
				JSONObject result=TicketPrintUtil.printTickeyByCloud(baseOrder,deviceMsg);
				
				Dto.writeLog(result.toString());
			}
		}
	}
}
