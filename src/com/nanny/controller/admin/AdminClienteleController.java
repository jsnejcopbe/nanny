package com.nanny.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.admin.BusinessBiz;
import com.nanny.biz.admin.ClienteleBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;

import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

/**
 * 总后台用户管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
@Controller
public class AdminClienteleController {

	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ClienteleBiz clienteleBiz;
	
	@RequestMapping("admin/clientele.html")
	public String businessList(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		PageUtil pageUtil = new PageUtil();
		
		if(pageIndex==null){
			
			pageIndex = 1;
		}
		
		if(pageSize==null){
			
			pageSize = 10;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
			/*QueryParam queryParam = new QueryParam();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			Map<String, String> orderMap = new HashMap<String, String>();*/
			
			String name=request.getParameter("many");
			String logmin=request.getParameter("logmin");
			String logmax=request.getParameter("logmax");
			//String situation=request.getParameter("state");
			
			
			/*paramMap.put("isAdmin", 0);
			typeMap.put("isAdmin", "eq");
			
			
			if(!"".equals(name) && name !=null){
				paramMap.put("tel", "%"+name+"%");
				typeMap.put("tel", "like");
			}
			if(!"".equals(situation) && situation !=null&&!"-1".equals(situation)){
				paramMap.put("situation", Integer.valueOf(situation));
				typeMap.put("situation", "eq");
			}
			
			orderMap.put("id", "desc");
			
			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			queryParam.setOrderMap(orderMap);*/
			
		JSONObject client=clienteleBiz.doClienteleList(name,logmin,logmax, pageUtil);
		
		 
		/* PageUtil page = sshUtilBiz.getPageCount(BaseUsers.class, queryParam, pageUtil);*/
		// 设置分页url
		 // pageUtil.setUrl("admin/clientele.html?");
		
		
		//request.setAttribute("page", page);
		request.setAttribute("client", client);
		
		request.setAttribute("name", request.getParameter("many"));
		request.setAttribute("lognmin", request.getParameter("logmin"));
		request.setAttribute("logbmax", request.getParameter("logmax"));
		//request.setAttribute("situation", request.getParameter("state"));
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/clienteleList";
	}
	

}
