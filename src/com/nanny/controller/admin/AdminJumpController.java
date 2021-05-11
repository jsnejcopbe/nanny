package com.nanny.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminJumpController {
	//首页跳转
	@RequestMapping("/adminIndex")
	public String jump_index(){
		return "pc/index-admin";
	}
	
	//商户申请列表跳转
	@RequestMapping("/applylist")
	public String jump_apply(){
		return "pc/applyList";
	}
	
	//推广员跳转
	@RequestMapping("/expandlist")
	public String jump_expand(){
		return "pc/expandList";
	}
	
	//商品跳转
	@RequestMapping("/productlist")
	public String jump_product(HttpServletRequest request){
		String checkType=request.getParameter("cs");
		request.setAttribute("checkType", checkType);
		return "pc/shopProduct";
	}
}
