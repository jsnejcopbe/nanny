package com.nanny.controller.supplier;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanny.biz.supplier.SupplierInfoBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseSupplier;
import com.zhuoan.shh.biz.SSHUtilBiz;

/**
 * 供应商信息
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */

@Controller
public class SupplierInfoController {
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private SupplierInfoBiz sib;
	
	@RequestMapping("supplier/supinfo.html")
	public String supplierinfo(HttpServletRequest request,HttpServletResponse response) {
		
		
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_SUPPLIER);
		//页面需要 供应商主表大部分信息  左联地址表   供应商地址   
		
		 JSONObject  obj =sib.getsupinfo(json.getInt("supplierID"));
		
		 request.setAttribute("obj", obj);
		
		 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/supplierinfo";
		
	}
	
	/**
	 * 更新供应商信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("supplier/editinfo.html")
	public void editsupinfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_SUPPLIER);
		
		String sname=request.getParameter("sname");
		String des=request.getParameter("des");
		String simg=request.getParameter("simg");
		String pass1=request.getParameter("pass1");
		String qq=request.getParameter("qq");
		
		JSONObject obj=new JSONObject();
		obj.element("sname", sname);
		obj.element("des", des);
		obj.element("simg", simg);
		obj.element("pass", pass1);
		obj.element("qq", qq);
		obj.element("sid", json.getLong("supplierID"));
		
		
		String mag=sib.updinfo(obj);
		
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
	}
	
	
	
	
	/**
	 * 供应商添加配送地址
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("supplier/addglobal.html")
	public void addglo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_SUPPLIER);
		
		
		String province=request.getParameter("province");
		String city=request.getParameter("city");
		String area=request.getParameter("area");
		String memo=request.getParameter("memo");
		
		
			JSONObject obj=new JSONObject();
			obj.element("fid", json.getInt("supplierID"));
			obj.element("area", area);
			obj.element("city", city);
			obj.element("province", province);
			/*obj.element("memo", memo);*/
			
		String mag=sib.addGlobal(obj);
		 
		 
		 
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		
	}
	
	
	
	
	
	
	/**
	 * 供应商删除配送地址
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("supplier/delglobal.html")
	public void delglo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String gid=request.getParameter("gid");
		 String mag=sib.delGlobal(Integer.valueOf(gid));
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		
	}
	
	
	
	/**
	 * 省
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("supplier/getProvince.html")
	public void getprovince(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
	
		 String mag=  sib.getProvince();
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
	}

	/**
	 * 市
	 * @param pid
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("supplier/getCity.html")
	public void getcity(@RequestParam String pid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		
		String mag=  sib.getCity(pid);;
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
	}
	
	/**
	 * 县区
	 * @param cityid
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("supplier/getArea.html")
	public void getarea(@RequestParam String cityid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		
		String mag= sib.getArea(cityid);
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
	}
	
}
