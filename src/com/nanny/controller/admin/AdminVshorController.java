package com.nanny.controller.admin;

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
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;

@Controller
public class AdminVshorController {
	
		//载入资源
		@Resource(name="shopOrdersBiz")
		private ShopOrdersBiz g_OrderBiz;
		@Resource(name="shopMsgBiz")
		private ShopMsgBiz g_ShopMsgBiz;
		
		/**
		 * 获得商户订单列表
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping(value={"admin/order-{page:\\d*}-{shopid:\\d*}"})
		public ModelAndView getShopOrders(@PathVariable int page,@PathVariable int shopid,HttpServletRequest request,HttpServletResponse response) throws IOException
		{
			//0.获得传递参数
			HttpSession session=request.getSession();
			//JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
			int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
			String queryInput=request.getParameter("query");//输入查询
//			String orderCode=request.getParameter("orderCode");//订单编号
//			String userTel=request.getParameter("userTel");//下单用户电话
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
			condtion.element("shopID", shopid);
			
			//2.查询订单信息
			JSONObject orderData=g_OrderBiz.getOrdersIdByCon(condtion, pageUtil);
			orderData.element("size", 20);
			
			//3.数据处理
			orderData=createData(orderData);
			
			//4.跳转
			if(sendType==null)
			{
				ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/adminOrderList");
				mav.addObject("orderData", orderData);
				mav.addObject("sta", status);
				mav.addObject("shid", shopid);
				
				mav.addObject("queryInput", queryInput==null?"":queryInput);
				return mav;
			}else{
				Dto.printMsg(response, orderData.toString());
				return null;
			}
		}

		
		/**
		 * 前往订单详情
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value={"admin/order-det-{orderCode:\\d*}"})
		public ModelAndView toOderDetail(@PathVariable String orderCode,HttpServletRequest request,HttpServletResponse response)
		{
			HttpSession session=request.getSession();
			//String shopid=request.getParameter("shopId");
			int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
			//JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
			//0.获得订单基础信息
			JSONObject orderMsg=g_OrderBiz.getOrderBaseByOrderCode(orderCode);
			orderMsg=statusDefine(orderMsg);
			//1.获得订单详情
			JSONArray orderDet=g_OrderBiz.getShopOrdersByOrderID(orderMsg.getLong("id"));
			
			//2.获得店铺信息
			JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(orderMsg.getLong("recShopID"));
			
			//3.返回页面
			ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/adminOrderDet");
			mav.addObject("orderBase", orderMsg);
			mav.addObject("orderDet", orderDet);
			mav.addObject("shopMsg", shopMsg);
			return mav;
		}
		
		
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
}
