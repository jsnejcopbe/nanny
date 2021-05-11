package com.nanny.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.user.AdminadveritsBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.TimeUtil;

@Controller
public class adminadvertisController {
	@Resource
	AdminadveritsBiz biz;
	
	@RequestMapping("/admin/adverits")
	public String businessList(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) {

	  PageUtil pageUtil = new PageUtil();
	
	   if(pageIndex==null){
		
	  	pageIndex = 1;
     	}
	
	   if(pageSize==null){
		
		pageSize = 10;
     	}
	String id="";
	pageUtil.setPageIndex(pageIndex);
	pageUtil.setPageSize(pageSize);
	String bname=request.getParameter("bname");
	JSONArray con=biz.getweelcon(id);
	JSONArray wheel=biz.getweel(pageUtil,bname);//分页
	JSONArray baseshop=biz.getbaseshop();
	request.setAttribute("bname", bname);
	request.setAttribute("wheel", wheel);
	request.setAttribute("con", con.size());
	request.setAttribute("baseshop", baseshop);
	request.setAttribute("pageIndex", pageIndex);
	request.setAttribute("pageSize", pageSize);
	int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
	return Dto.getPagePath(url)+"/advertisement";
}
	//添加
	@RequestMapping("/admin/adveritsinsert")
	public void adveritsinsert(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String imgStc=request.getParameter("fi");
		String jumpSrc=request.getParameter("jumpSrc");
		Long bank=Long.valueOf(request.getParameter("bank"));
		String isall=request.getParameter("isUse");
		String isUse=request.getParameter("isCom");
	
		String time=TimeUtil.getNowDate("yyyy-MM-dd");
		biz.inertweel(bank, imgStc, jumpSrc, isall, isUse, time);
		JSONObject msg=new JSONObject();
		msg.element("msg", "成功");
		Dto.printMsg(response, msg.toString());
	}
	
	//操作
	@RequestMapping("/admin/operation")
	public void operation(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String Disid=request.getParameter("Disid");
		String Enid=request.getParameter("Enid");
		String Delid=request.getParameter("Delid");
		JSONObject msg=new JSONObject();
		if (Disid!=null) {
			msg.element("msg", "已停用");
		}
		else if (Enid!=null) {
			msg.element("msg", "已启用");
		}
		else {
			msg.element("msg", "已删除");
		}
		biz.operation(Disid, Enid, Delid);
		Dto.printMsg(response, msg.toString());
	}
	
	//进入编辑
	@RequestMapping("/admin/adminadveredit")
	public String adminadveredit(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException {
		  String id=request.getParameter("id");
		  JSONArray wheel=biz.getweelcon(id);
		  JSONArray baseshop=biz.getbaseshop();
		request.setAttribute("wheel", wheel);
		request.setAttribute("shopID", wheel.getJSONObject(0).getDouble("shopID"));
		request.setAttribute("imgStc", wheel.getJSONObject(0).getString("imgStc"));
		request.setAttribute("jumpSrc", wheel.getJSONObject(0).getString("jumpSrc"));
		request.setAttribute("isAllUser", wheel.getJSONObject(0).getString("isAllUser"));
		request.setAttribute("isUser", wheel.getJSONObject(0).getString("isUser"));
		request.setAttribute("id", wheel.getJSONObject(0).getLong("id") );
		request.setAttribute("baseshop", baseshop);
		int type=(Integer)request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
	   return Dto.getPagePath(type)+"/advertisingSettings";
	}
	
	//编辑保存
	@RequestMapping("/admin/editpreservation")
	public void editpreservation(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException {
	  String imgStc=request.getParameter("fi");
	  String jumpSr=request.getParameter("jumpSrc");
	 String isAllUser=request.getParameter("isUse");
	 String isUser=request.getParameter("isCom");
	 String time=TimeUtil.getNowDate("yyyy-MM-dd");
	 String shopID=request.getParameter("bank");
	 Long wheelid=Long.valueOf(request.getParameter("id"));
	 biz.updatewheel(imgStc, jumpSr, isAllUser, isUser, time, wheelid,shopID);
	 JSONObject msg=new JSONObject();
	 msg.element("msg", "修改成功");
	 Dto.printMsg(response, msg.toString());
	}
	
	
}