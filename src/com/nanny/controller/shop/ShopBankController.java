package com.nanny.controller.shop;

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
 * 商户户银行卡操作(pc)
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Controller
public class ShopBankController {


		@Resource
		private SSHUtilBiz sshUtilBiz;
		
		@Resource
		private BankBiz bankBiz;
		
		
		/**
		 * 提款账户展示
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("shop/banklist.html")
		public String bankshow(HttpServletRequest request,HttpServletResponse response) {
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
			    int url= (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
				
				return Dto.getPagePath(url)+"/bankseting";
		}
		
		/**
		 * 更新提款账户
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping("shop/updbankset.html")
		public void updbanklist(HttpServletRequest request,HttpServletResponse response) throws IOException {
			HttpSession session =request.getSession();
			JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
			
			String bank=request.getParameter("bank");
			String bankcar=request.getParameter("bankcard");
			String name=request.getParameter("uname");
			String code=request.getParameter("mocode");
			String s= ShortMessageUtil.Contrast(code, request);
			String mag=null;
			if("0".equals(s)){
				 mag=bankBiz.updatebank(json.getInt("userID"), Integer.valueOf(bank), bankcar, name);
			
			}else{
				mag="1";
			}
			
			
			JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		}
	
		
		@RequestMapping("shop/addbankBind.html")
		public void addbankcar(HttpServletRequest request,HttpServletResponse response) throws IOException {
			HttpSession session =request.getSession();
			JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
			
			String bank=request.getParameter("bank");
			String bankcar=request.getParameter("bankcard");
			String name=request.getParameter("uname");
			String code=request.getParameter("mocode");
			UserAccount ua=new UserAccount();
			ua.setAccName(name);
			ua.setAccount(bankcar);
			ua.setCreateTime(DateUtils.getTimestamp());
			ua.setUserId( json.getLong("userID"));
			ua.setBankId(Long.valueOf(bank));
			
			String s= ShortMessageUtil.Contrast(code, request);
			String mag=null;
			
			JSONArray basnk=bankBiz.bankinfo(json.getInt("userID"));
			
			
		
			if("0".equals(s)){
				
					bankBiz.delbank(json.getInt("userID"));
					mag=bankBiz.addbank(ua);
					
				/*if(basnk.size()==0){
					mag=bankBiz.addbank(ua);
				}else{
					mag="0";
				}*/
			}else{
				mag="1";
			}
			JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		}
}
