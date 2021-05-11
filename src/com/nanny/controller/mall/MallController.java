package com.nanny.controller.mall;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.dto.Dto;


/**
 * 面向商城的
* @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1 
 */
@Controller
public class MallController {
	
	@RequestMapping("pay/paymall")
	public void paymall(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String totalPrice=request.getParameter("total");
		//String orid=request.getParameter("");
		
		//回调
		request.getSession().setAttribute(Dto.CALL_BACK_URL, "/mall/users/Payment.html");
		//存入金额
		request.getSession().setAttribute(Dto.MONEY_OF_CHARGE, totalPrice);
		//跳转订单充值
		response.sendRedirect("/nanny/pay/weixinpay.html");
	}

}
