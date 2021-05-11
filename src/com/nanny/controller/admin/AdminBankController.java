package com.nanny.controller.admin;

import java.io.IOException;
import java.text.ParseException;
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

import com.nanny.biz.global.bank.BankBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysBanks;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;
/**
 * 总后台转账银行设置
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class AdminBankController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private BankBiz bankBiz;
	
	@RequestMapping("admin/adminbank.html")
	public String banklist(
		@RequestParam(required = false) Integer pageIndex,
		@RequestParam(required = false) Integer pageSize,
		HttpServletRequest request, HttpServletResponse response)
		throws ParseException {
	PageUtil pageUtil = new PageUtil();

	if (pageIndex == null) {

		pageIndex = 1;
	}

	if (pageSize == null) {

		pageSize = 10;
	}

	pageUtil.setPageIndex(pageIndex);
	pageUtil.setPageSize(pageSize);
		
	QueryParam queryParam = new QueryParam();

	Map<String, Object> paramMap = new HashMap<String, Object>();
	Map<String, String> typeMap = new HashMap<String, String>();
	Map<String, String> orderMap = new HashMap<String, String>();

	HttpSession session = request.getSession();
	JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
	
	String bname = request.getParameter("bname");
	String status = request.getParameter("state");
	if(bname!=null&&!"".equals(bname)){
	paramMap.put("bankName", "%"+bname+"%");
	typeMap.put("bankName", "like");
	}
	if (!"".equals(status) && status != null && !"-1".equals(status)) {
		paramMap.put("isUse", Integer.valueOf(status));
		typeMap.put("isUse", "eq");
	}
	queryParam.setParamMap(paramMap);
	queryParam.setTypeMap(typeMap);
	queryParam.setOrderMap(orderMap);
		
	JSONArray bank=bankBiz.banklist(queryParam, pageUtil);
		
	PageUtil page = sshUtilBiz.getPageCount(SysBanks.class,queryParam, pageUtil);
	
	// 设置分页url
	page.setUrl("admin/adminbank.html?");

	request.setAttribute("page", page);
	
	request.setAttribute("bank", bank);

	request.setAttribute("statu", request.getParameter("state"));
	request.setAttribute("baname", request.getParameter("bname"));
	int url = (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
	return Dto.getPagePath(url) + "/adminBank";
		
	}
	
	
	/**
	 * 添加银行
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/addbanks.html")
	public void addbanks(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String img=request.getParameter("fi");
		String bname=request.getParameter("bmname");
		String sta=request.getParameter("bt");
		SysBanks  bs= new SysBanks();
		bs.setBankName(bname);
		bs.setIcon(img);
		bs.setIsUse(Integer.valueOf(sta));
		bs.setCreateTime(DateUtils.getTimestamp());
		 String mag= bankBiz.addsysBank(bs);
		 
		 JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		
	}
	
	/**
	 * 修改银行信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updbanks.html")
	public void updbank(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String img=request.getParameter("fi");
		String bname=request.getParameter("bmname");
		String sta=request.getParameter("bt");
		String bid=request.getParameter("bid");
		SysBanks  bs= (SysBanks) sshUtilBiz.getObjectById(SysBanks.class, Long.valueOf(bid));
		bs.setBankName(bname);
		bs.setIcon(img);
		bs.setIsUse(Integer.valueOf(sta));
		sshUtilBiz.updateObject(bs);
		 
		 JSONObject msg=new JSONObject();
			msg.element("msg", "修改成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		
	}
	
	
	
	
	
	
	
	
	
}

