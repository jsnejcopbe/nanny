package com.nanny.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanny.biz.shop.ShopBasis;
import com.nanny.dto.Dto;
import com.nanny.util.JsonUtil;
import com.zhuoan.util.ShortMessageUtil;

@Controller
@RequestMapping("admin")
public class AShopApplyController {
	@Resource
	private ShopBasis shop;
	
	@ResponseBody
	@RequestMapping("/list/init")
	public String init_list(String data){
		if(data == null || data.trim().isEmpty())return Dto.NULL_ERROR;
		Map<String, String> mapStr = JsonUtil.getMapStr(data);
		return shop.apply_init_list(mapStr,null);
	}	
	
	@ResponseBody
	@RequestMapping("/list/change")
	public String change_state(String type,String tel,String id,HttpServletRequest request) throws UnsupportedEncodingException{
				
		String passWord=shop.apply_change_state(type,tel,id);
		
		if(Integer.valueOf(type)==Dto.OK && passWord.indexOf("exist")==-1)
			ShortMessageUtil.sendMessage("【掌上保姆社区】您的初始密码为："+passWord+"。请及时登录您的账户进行更改。", tel);
		return passWord;
	}
	
	@ResponseBody
	@RequestMapping("")
	public String getPassword(String id){
		return shop.getPassword(id);
	}
}
