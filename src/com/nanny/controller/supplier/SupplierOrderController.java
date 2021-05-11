package com.nanny.controller.supplier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TicketPrintUtil;
import com.nanny.biz.supplier.SupplierOrderBiz;

/**
 * 供应商订单
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Controller
public class SupplierOrderController {
	
	@Resource
	private SupplierOrderBiz spb;
	@Resource
	private SSHUtilDao dao;
	/**
	 * 供货订单管理
	 * 查询
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	@RequestMapping("supplier/order")
	public String supplierOrder(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response){
		
		PageUtil pageUtil = new PageUtil();
		
		if(pageIndex==null){
			
			pageIndex = 1;
		}
		
		if(pageSize==null){
			
			pageSize = 10;
		}
		pageUtil.setPageIndex(pageIndex);
		
		pageUtil.setPageSize(pageSize);
		
		String query=request.getParameter("query");
		
		
		
		String  sta=request.getParameter("sta");
		
		JSONObject bean=new JSONObject();
		
		bean.element("sta",sta);
		bean.element("orderCode", query);
		JSONObject jsonobject=spb.searchOrder(bean, pageUtil);
		
		JSONArray arr2=jsonobject.getJSONArray("value");
		
		request.setAttribute("jso", arr2);
		
		request.setAttribute("sta", sta);
		request.setAttribute("query", query);
		request.setAttribute("jsopage", jsonobject);
		

		return "pc/supplierOrderList";
	}
	
	/**
	 * 订单详情
	 * @param orderCode
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping("supplier/orderDet")
	public String toOderDetail(HttpServletRequest request,HttpServletResponse response)
	{
		int supplierID=Integer.parseInt(request.getParameter("suOrderId"));
		JSONObject jso=new JSONObject();
		
		jso=spb.supplierDetById(supplierID);
		
		JSONObject jsoShop=jso.getJSONObject("supplierDet");
		JSONArray pro=jso.getJSONArray("shopDet");

		request.setAttribute("jsoShop", jsoShop);

		request.setAttribute("supro", pro);
		
		return "pc/supplierOrderDet";
	}
	
	/**
	 * 订单确认和取消
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @memo  发送站内信
	 */
	@RequestMapping("supplierorder/Confirm")
	public void orderConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		int orderID=Integer.parseInt(request.getParameter("orderID"));
		
		int status=Integer.parseInt(request.getParameter("sta"));
	
		String text=request.getParameter("text");
		
		JSONObject bean=new JSONObject();
		bean.element("status", status);

		spb.updateSupplier(bean, orderID);
		
		if(text!=null){
			JSONObject msg=new JSONObject();
			msg.element("msg", text);
			spb.updateSupplier(msg, orderID);
			Dto.printMsg(response, msg.toString());
		}
		else{
		int payShopID=Integer.parseInt(request.getParameter("payShopID"));
		JSONObject msg1=new JSONObject();
		msg1.element("supplierId", orderID);
		msg1.element("payShopID",payShopID);
		spb.checkRelation(msg1);
		msg1.element("msg1", "操作成功");
		
		Dto.printMsg(response, msg1.toString());
		}
		
		
	}
	

}
