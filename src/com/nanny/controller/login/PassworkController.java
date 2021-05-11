package com.nanny.controller.login;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.login.PassworkBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseUsers;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.util.ShortMessageUtil;

@Controller
public class PassworkController {
	@Resource
	private SSHUtilBiz  sshUtilBiz;
	@Resource
	private PassworkBiz passWorkBiz;
	
	@RequestMapping("/pass_jump.html")
	public String passjump(HttpServletRequest request,HttpServletResponse response) {
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		request.setAttribute("sid", json.getString("shopID"));
		return Dto.getPagePath(url)+"/passwork";	
		
	}
	@RequestMapping("/phone_jump.html")
	public String phonejump(HttpServletRequest request,HttpServletResponse response) {
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/phonework";	
		
	}
	
	@RequestMapping("/fgpass_jump.html")
	public String fgpassjump(HttpServletRequest request,HttpServletResponse response) {
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/forgetpass";	
		
	}
	
	
	/**
	 * 发送短信验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/obtainCode.html")
	public void verifyMag(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		String tel=request.getParameter("tell");
		JSONObject msg=new JSONObject();
		String mag = null;
		if(json!=null){
				tel=json.getString("tel");
			  mag=ShortMessageUtil.getCode( tel, request);
		}else if(tel!=null){
		
			JSONArray pw=passWorkBiz.dopawork(tel);
			if(pw.size()<1){
				mag="2";
			}else{
				mag=ShortMessageUtil.getCode( tel, request);
			}
		
		}else
			mag="请输入电话号码";
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	
	/**
	 * 修改账户密码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/editPasswork.html")
	public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String passWork=request.getParameter("pass1");
		String tels=request.getParameter("tell");
		String code=request.getParameter("mocode");
		String s= ShortMessageUtil.Contrast(code, request);
		JSONObject msg=new JSONObject();
		
		
			
			if("0".equals(s)){
				if(json!=null){
					passWorkBiz.updatepawork(json.getString("tel"), passWork);
				}else{
					passWorkBiz.updatepawork(tels, passWork);
						}
				msg.element("msg", "0");
			}else{
				msg.element("msg", "1");
			}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
		
	}
	/**
	 * 修改手机号码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	@RequestMapping("/editPhonework.html")
	public void phoneReset(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		System.out.println(json);
		String tel=request.getParameter("tel");
		String code=request.getParameter("mocode");
		String s= ShortMessageUtil.Contrast(code, request);
		
		JSONObject msg=new JSONObject();
		
		if(passWorkBiz.checkPhone(tel)){
			
			if("0".equals(s)){
				passWorkBiz.updatePhonework(json.getInt("id"), tel);
				//替换session中的手机号
				json.element("tel", tel);
				msg.element("msg", "0");
			}
			else{
				msg.element("msg", "1");
				msg.element("msg1", "验证码错误");
			}
		}else{
			msg.element("msg1", "手机已注册，请重新输入");
		}
				
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
		
	}
	
	
	/**
	 * 发送短信验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/phoneCode.html")
	public void verifyMagPhone(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		
		String tel=request.getParameter("tel");
		JSONObject msg=new JSONObject();
		
		if(passWorkBiz.checkPhone(tel)){
		String sta=ShortMessageUtil.getCode( tel, request);
		if(sta.equals("0")){
			msg.element("msg1","请等待,验证码会发送到您的手机");
			msg.element("msg2", "1");
		}
		else{
			msg.element("msg2", "3");
			msg.element("msg1", "发送失败，请重新发送");
		}
		}else{
			msg.element("msg1", "手机号码已存在，请重新输入");
			msg.element("msg2", "2");
		}
		
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
}
