package com.nanny.controller.admin;


import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.nanny.biz.admin.AdminSupplierBiz;
import com.nanny.biz.supplier.SupplierOrderBiz;
import com.nanny.model.BaseSupplier;
import com.zhuoan.ssh.bean.PageUtil;



@Controller
public class AdminSupplierController {
	
		@Resource
		private AdminSupplierBiz asb;
		
		@Resource
		private SupplierOrderBiz spb;
		/**
		 * 供应商统计
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("admin/supplier")
		public String AdminSupplier(@RequestParam(required = false) Integer pageIndex,
				@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) {
			
			PageUtil pageUtil = new PageUtil();
			
			if(pageIndex==null){
				
				pageIndex = 1;
			}
			
			if(pageSize==null){
				
				pageSize = 10;
			}
			pageUtil.setPageIndex(pageIndex);
			
			pageUtil.setPageSize(pageSize);
			
			String supplierInfo=request.getParameter("supplierInfo");
			String status=request.getParameter("status");
			
		
			JSONObject bean=new JSONObject();
			
			bean.element("supplierInfo",supplierInfo);
			bean.element("status", status);
			
			JSONObject jso=asb.getBaseSupplierInfo(bean,pageUtil);
			
			JSONArray baseSupplierInfo=jso.getJSONArray("bsInfo");
			
			
			
			

			request.setAttribute("status",status);
		
			request.setAttribute("supplierInfo",supplierInfo );
			request.setAttribute("baseSupplierInfo", baseSupplierInfo);
			request.setAttribute("baseSupplierPage", jso);
			
		
			return "pc/adminSupplierList";
		}
		/**
		 * 添加供应商
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		@RequestMapping("admin/supplierAdd")
		public void AdminAddSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
			
			String username=request.getParameter("username");
			String tel=request.getParameter("tel1");
			String pass=request.getParameter("pass1");
			String address=request.getParameter("des");
			
			BaseSupplier bs=new BaseSupplier();
			JSONObject bean=new JSONObject();
			bean.element("username",username);
			bean.element("tel", tel);
			
			JSONObject msg=new JSONObject();
			if(asb.checkSupplierExi(bean)){
				msg.element("msg", "用户名或密码已存在");
			}
			else{
			
			bs.setSupplierName(username);
			bs.setTel(tel);
			bs.setPassword(pass);
			bs.setStatus(1);
			bs.setSupplierDes(address);
			bs.setSupplierIcon("/nanny/images/defalut.jpg");
			asb.addSupplier(bs);
			
			
			msg.element("success", "success");
			msg.element("msg", "添加成功");
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		
		}
		
		/**
		 * 开关店
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		
		@RequestMapping("admin/supplierConfirm")
		public void AdminCheckSuStatus(HttpServletRequest request,HttpServletResponse response) throws IOException{
			
			int supplierId=Integer.parseInt(request.getParameter("supplierId"));
			int status=Integer.parseInt(request.getParameter("status"));
			
			asb.changeSupplierStatus(supplierId, status);
			
			
			JSONObject msg=new JSONObject();
			msg.element("msg","操作成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
			
		}
		
		/**
		 * 供货商总订单
		 * @param pageIndex
		 * @param pageSize
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("admin/supplierOrder")
		public String AdminSupplierOrder(@RequestParam(required = false) Integer pageIndex,
				@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) {
			PageUtil pageUtil = new PageUtil();
			
			if(pageIndex==null){
				
				pageIndex = 1;
			}
			
			if(pageSize==null){
				
				pageSize = 10;
			}
			pageUtil.setPageIndex(pageIndex);
			
			pageUtil.setPageSize(pageSize);
			
			JSONObject bean =new JSONObject();
			
			String query=request.getParameter("query");
			
			String sta=request.getParameter("sta");
			
			
			bean.element("query", query);
			bean.element("sta", sta);
			
			int supplierId=Integer.parseInt(request.getParameter("supplierId"));
			
			bean.element("supplierId", supplierId);
			JSONObject jso=asb.getOrder(bean, pageUtil);
			
			System.out.println("query="+query+"sta="+sta);
			JSONArray orderList=jso.getJSONArray("orderList");
			
			jso.element("supplierId",supplierId);
			jso.element("query", query);
			jso.element("sta", sta);
			request.setAttribute("orderList", orderList);
			request.setAttribute("jso", jso);
			return "pc/adminSupplierOrderList";
		}
		
		/**
		 * 订单详情
		 * @param request
		 * @param response
		 * @return
		 */
		
		@RequestMapping("admin/supplierOrderDet")
		public String AdminOderDetail(HttpServletRequest request,HttpServletResponse response)
		{
			int supplierID=Integer.parseInt(request.getParameter("suOrderId"));
			JSONObject jso=new JSONObject();
			
			jso=spb.supplierDetById(supplierID);
			
			JSONObject jsoShop=jso.getJSONObject("supplierDet");
			JSONArray pro=jso.getJSONArray("shopDet");

			request.setAttribute("jsoShop", jsoShop);

			request.setAttribute("supro", pro);
			
			return "pc/adminSupplierOrderDet";
		}
	
		/**
		 * 更新供货商详情公告
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping("admin/supplierUpdate")
		public void AdminSupplierUpdate(HttpServletRequest request,HttpServletResponse response) throws IOException
		{
	
			String shopcon=request.getParameter("shopcon");
			int id=Integer.parseInt(request.getParameter("supplierId"));
		
			asb.UpdateSupplierInfo(id,shopcon);
			
			JSONObject msg=new JSONObject();
			
				msg.element("msg","msg");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		}
	
		
		
		
}


