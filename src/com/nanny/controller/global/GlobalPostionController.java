package com.nanny.controller.global;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.nanny.dto.Dto;

/**
 * 全局wifi定位controller
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
@Controller
public class GlobalPostionController extends GlobalCommonExtend {

	/**
	 * 获得当前用户的位置信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getmyposition")
	public void getNowPosition(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		String lonStr=request.getParameter("lonStr");
		String city=request.getParameter("city");
		
		//1.解析地址
		JSONObject msg=helpGetPostion(lonStr, city);
		
		//2.返回结果
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 前往商铺列表页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("shopsearch")
	public ModelAndView toRecentShopList(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int type=(Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得传入参数
		String lonStr=request.getParameter("lonStr");
		String city=request.getParameter("city");
		
		//1.解析地址
		JSONObject backData=super.helpGetPosAll(lonStr, new String(city.getBytes("ISO-8859-1"), "UTF-8"));
		
		//2.返回结果
		if(backData==null)
		{
			response.sendRedirect("/nanny/global_city.html");
			return null;
		}else{
			ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopselect");
			mav.addObject("backData", backData);
			return mav;
		}
	}
}