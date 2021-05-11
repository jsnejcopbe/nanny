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
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.login.PcPassWordBiz;
import com.nanny.dto.Dto;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.util.ShortMessageUtil;


@Controller
public class PcPassWordController {
	
	@Resource
	private PcPassWordBiz ppwz;
	/**
	 * 跳转至密码修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/chpass_jump.html")
	public ModelAndView fgpassjump(HttpServletRequest request,HttpServletResponse response) {
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		ModelAndView mav = new ModelAndView(Dto.getPagePath(url)+"/forgetPassword");
		return mav;
		
	}
	
	/**
	 * 发送短信验证(PC端)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/pcobtainCode.html")
	public void verifyMag(HttpServletRequest request,HttpServletResponse response) throws IOException{

		String tel=request.getParameter("tell");
		JSONObject msg=new JSONObject();
		String mag = null;
		
		if(tel!=null && tel != ""){
			JSONArray pw = ppwz.dopawork(tel);
			if(pw.size()<1){
				mag="2";
			}else{
				mag=ShortMessageUtil.getCode( tel, request);
			}
		
		}
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 修改账户密码(PC端)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/pceditPasswork.html")
	public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String passWork=request.getParameter("pass1");
		String tels=request.getParameter("tell");
		String code=request.getParameter("mocode");
		String s= ShortMessageUtil.Contrast(code, request);
		JSONObject msg=new JSONObject();
		
			if("0".equals(s) && passWork != null && passWork != ""){
				ppwz.updatepawork(tels, passWork);		
				msg.element("msg", "0");
			}else{
				msg.element("msg", "1");
			}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
		
	}
}
