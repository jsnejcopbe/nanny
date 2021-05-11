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

import com.nanny.biz.global.store.ShopProTypeBiz;
import com.nanny.dto.Dto;

/**
 * 总后台商品一级分类排序
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class UserClassSortController {

	//载入资源
	@Resource(name="shopProTypeBiz")
	private ShopProTypeBiz g_ProTypeBiz;
	
	/**
	 * 前往类别页面排序
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("shop/classsort")
	public ModelAndView toClassSortPage(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//0.获得平台所有商品的一级分类
		JSONArray firstClassList=g_ProTypeBiz.getFirstClassList((long)0);
		
		//1.返回数据
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/classSort");
		mav.addObject("classList", firstClassList);
		return mav;
	}
	
	/**
	 * 显示商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("shop/showclass")
	public void showClassSort(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 获得数据
		String id = request.getParameter("classID");
		// 更新状态
		g_ProTypeBiz.showClassSort(Long.valueOf(id));
		
		// 返回信息
		JSONObject msg = new JSONObject();
		msg.element("msg","显示商品" );
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 隐藏商品
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/hideclass")
	public void hideClassSort(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 获得数据
		String id = request.getParameter("classID");
		// 更新状态
		g_ProTypeBiz.hideClassSort(Long.valueOf(id));
		// 返回
		JSONObject msg = new JSONObject();
		msg.element("msg","隐藏商品" );
		Dto.printMsg(response, msg.toString());
	}
	
	
}
