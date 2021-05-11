package com.nanny.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.shop.ShopProApplyBiz;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProApply;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;

@Controller
public class AdminProApplyController {
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ShopProApplyBiz shopProApplyBiz;
	
	
	@RequestMapping("admin/adminapply.html")
	public String proapplist(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 5;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		QueryParam queryParam = new QueryParam();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		String sta=request.getParameter("stats");
		if (!"".equals(sta) && sta != null && !"-1".equals(sta)) {
			paramMap.put("status", Integer.valueOf(sta));
			typeMap.put("status", "eq");
		}
		
		orderMap.put("id", "desc");

		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);
		
		JSONArray app=shopProApplyBiz.doProApply(0,sta,pageUtil);
		PageUtil page = sshUtilBiz.getPageCount(ShopProApply.class,queryParam, pageUtil);
		// 设置分页url
		page.setUrl("admin/adminapply.html?");

		request.setAttribute("page", page);
		request.setAttribute("app", app);
		request.setAttribute("stats", request.getParameter("stats"));
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/adminProApply";
	}
	
	@RequestMapping("admin/updProApply.html")
	public void updproapply(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String id=request.getParameter("id");
		String sta=request.getParameter("sta");
		JSONObject msg=new JSONObject();
		
		ShopProApply pro=(ShopProApply) sshUtilBiz.getObjectById(ShopProApply.class, Long.valueOf(id));
		pro.setStatus(Integer.valueOf(sta));
		
		sshUtilBiz.updateObject(pro);
		if("1".equals(sta)){
			msg.element("msg", 1);
		}else{
			msg.element("msg", 2);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
}
