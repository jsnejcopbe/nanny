package com.nanny.controller.user;

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

import com.nanny.dto.Dto;
import com.nanny.model.BaseSaleman;
import com.nanny.biz.user.SalesPasswordBiz;


@Controller
@RequestMapping("salesman")
public class SalesPassowrdController {
	@Resource
	private SalesPasswordBiz upb;
	
	
	
	/**
	 * 业务员密码修改
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/editSalesPasswork.html")
	public void userPasswordUpd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获得業務員参数
		HttpSession session=request.getSession();
		BaseSaleman object = (BaseSaleman) session.getAttribute(Dto.LOGIN_SALEMAN);
		String tel =object.getTel();
		String passWord = request.getParameter("pass1");
		
		JSONObject bean=new JSONObject();
		bean.element("password", passWord);
		bean.element("tel", tel);
		
		String  mesg=upb.updatePassword(bean);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", mesg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	/**
	 * 跳轉至密碼修改頁面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/password.html")
	public String getMsgSaleUpdate(HttpServletRequest request,HttpServletResponse  response)throws IOException {
		HttpSession session=request.getSession();
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		BaseSaleman json = (BaseSaleman) session.getAttribute(Dto.LOGIN_SALEMAN);
		request.setAttribute("username", json.getTel());
		return Dto.getPagePath(url)+"/sales_password";
	}

}
