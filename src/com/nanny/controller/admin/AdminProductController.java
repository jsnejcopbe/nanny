package com.nanny.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.admin.AdminProductBiz;
import com.nanny.dto.Dto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 总后台商品管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class AdminProductController {

	@Resource(name="adminProductBiz")
	private AdminProductBiz g_AdProBiz;
	
	
	/**
	 * 设置商品的推荐状态
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/updateprotorecommond")
	public void updateProToRecommond(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
		JSONArray data=JSONArray.fromObject(request.getParameter("jsonData"));
		
		//1.更新商品状态
		g_AdProBiz.updateProRecSta(data);
		
		//2.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "设置成功");
		Dto.printMsg(response, msg.toString());
	}
}
