package com.nanny.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.admin.AdminCouponBiz;
import com.nanny.biz.admin.AdminProductBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class AdminCouponController {
	
	@Resource
	private AdminCouponBiz acp;
	
	
	
	@RequestMapping("admin/coupon")
	public String adminCoupon(@RequestParam(required=false)Integer pageIndex,@RequestParam(required=false)Integer pageSize, HttpServletRequest request,HttpServletResponse response) {
		PageUtil pageUtil=new PageUtil();
		if(pageIndex==null){
			pageIndex=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		JSONObject bean=new JSONObject();
		String status=request.getParameter("status");
		String queryName=request.getParameter("queryName");
		String logmin=request.getParameter("logmin");
		String logmax=request.getParameter("logmax");
		bean.element("logmin", logmin);
		bean.element("logmax", logmax);
		bean.element("status", status);
		bean.element("queryName", queryName);
		bean.element("isShop", 0);
		
		request.setAttribute("jso",acp.getCouponList(pageUtil,bean));
		request.setAttribute("queryName", queryName);
		request.setAttribute("status", status);
		request.setAttribute("logmin", logmin);
		request.setAttribute("logmax", logmax);
		request.setAttribute("isAdmin", 1);
		return "pc/adminCoupon";
	}
}
