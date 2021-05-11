package com.nanny.controller.shop.orders;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanny.biz.shop.orders.Refund;
import com.nanny.dto.Dto;
import com.nanny.util.JsonUtil;

@Controller
@RequestMapping("shop")
public class RefundController {
	@Resource
	private Refund refund;
	
	@RequestMapping("refund/init")
	@ResponseBody
	public String init(String data,HttpServletRequest request){
		Map<String, String> option = data == null ? null : JsonUtil.getMapStr(data);
		JSONObject obj = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		return refund.init(option,obj.get("shopID").toString());
	}
	
}
