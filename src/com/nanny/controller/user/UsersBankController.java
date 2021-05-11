package com.nanny.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.nanny.biz.global.bank.BankBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysBanks;
import com.nanny.model.UserAccount;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.ShortMessageUtil;
/**
 * 用户银行卡操作
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class UsersBankController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private BankBiz bankBiz;
	
	
	/**
	 * 选择银行列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/selectbank.html")
	public String banklist(HttpServletRequest request,HttpServletResponse response) {
		JSONObject json=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		JSONArray bank=JSONArray.fromObject(sshUtilBiz.getObjectList(SysBanks.class, null,null));
		
		request.setAttribute("bank", bank);
		request.setAttribute("sid",json.getString("shopID"));
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/bindBank";
	}
	
	
	/**
	 * 添加提款账户
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("users/bankBind.html")
	public void addbankcar(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String bank=request.getParameter("bank");
		String bankcar=request.getParameter("bankcard");
		String name=request.getParameter("uname");
		String code=request.getParameter("mocode");
		String s= ShortMessageUtil.Contrast(code, request);
		
		UserAccount ua=new UserAccount();
		ua.setAccName(name);
		ua.setAccount(bankcar);
		ua.setCreateTime(DateUtils.getTimestamp());
		ua.setUserId( json.getLong("userID"));
		ua.setBankId(Long.valueOf(bank));
		String mag=null;
		
		JSONArray basnk=bankBiz.bankinfo(json.getInt("userID"));
		
		if("0".equals(s)){
			
			if(basnk.size()==0){
				mag=bankBiz.addbank(ua);
			}else{
				mag="0";
			}
		}else{
			mag="1";
		}
		
		JSONObject msg=new JSONObject();
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	/**
	 * 提款账户展示
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/bankshow.html")
	public String editbank(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
			PageUtil pageUtil = new PageUtil();
			QueryParam queryParam = new QueryParam();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			
			paramMap.put("isUse", 0);
			typeMap.put("isUse", "eq");

			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			
		    JSONArray info= bankBiz.bankinfo(json.getInt("userID"));
		    JSONArray list=bankBiz.banklist(queryParam,null);
		    request.setAttribute("info", info);
		    request.setAttribute("list", list);
		    request.setAttribute("sid",json.getString("shopID")); 
		    int url= (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
			
			return Dto.getPagePath(url)+"/editBank";
	}
	
	/**
	 * 更新提款账户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("users/updbank.html")
	public void updbank(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String bank=request.getParameter("bank");
		String bankcar=request.getParameter("bankcard");
		String name=request.getParameter("uname");
		
		String code=request.getParameter("mocode");
		String s= ShortMessageUtil.Contrast(code, request);
		String mag=null;
		
		
		JSONObject msg=new JSONObject();
		if("0".equals(s)){
			 mag=bankBiz.updatebank(json.getInt("userID"), Integer.valueOf(bank), bankcar, name);
		}else{
			mag="1";
		}
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	@RequestMapping("/fasongd.html")
	public void fasong(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String tel=request.getParameter("tel");
		
		String sendMsg="【掌上保姆社区】收到请回复";
		
		String mag= ShortMessageUtil.getCode( tel, request);
		System.out.println(mag);
		JSONObject msg=new JSONObject();
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
}
