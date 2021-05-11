package com.nanny.controller.user;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nanny.biz.user.UserRecharge;
import com.nanny.dto.Dto;
import com.nanny.util.JsonUtil;

@Controller
@RequestMapping("users")
public class UserRechargeController {
	@Resource
	private UserRecharge ur;
	
	@RequestMapping("/recharge")
	public String jump_recharge(HttpServletRequest request){
		return "mobile/user_ recharge";
	}
	
	@RequestMapping("/recharge/init")
	@ResponseBody
	public String init(String data,HttpServletRequest request){
		String id = Dto.getUserID(request);
		Map<String, String> option = (data == null || data.trim().isEmpty()) ? null : JsonUtil.getMapStr(data);
		return ur.init(id, option);
	}
	
	@RequestMapping("/recharge/mo_{money}.html")
	public String go_recharge(@PathVariable String money,HttpServletRequest request){
		//System.out.println(money+"=============");
		HttpSession session = request.getSession();
		session.setAttribute(Dto.MONEY_OF_CHARGE, Double.valueOf(money));
		session.setAttribute(Dto.CALL_BACK_URL, "/nanny/index.html");
		return Dto.REDIRECT+"pay/weixinpay.html";
	}
}
