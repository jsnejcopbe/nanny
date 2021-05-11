package com.nanny.controller.admin;

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
public class AdminClassSortController {

	//载入资源
	@Resource(name="shopProTypeBiz")
	private ShopProTypeBiz g_ProTypeBiz;
	
	/**
	 * 前往类别页面排序
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/classsort")
	public ModelAndView toClassSortPage(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//0.获得平台所有商品的一级分类
		JSONArray firstClassList=g_ProTypeBiz.getFirstClassList((long)0);
		
		//1.返回数据
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/adminClassSort");
		mav.addObject("classList", firstClassList);
		return mav;
	}
	
	/**
	 * 更新页面类别排序
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/updatesort")
	public void updateClassSort(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得数据
		JSONArray data=JSONArray.fromObject(request.getParameter("json"));
		//1.更新排序
		g_ProTypeBiz.updateClassSort(data);
		//2.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "排序修改成功");
		Dto.printMsg(response, msg.toString());
	}
	
	
	/***************************************************************************************************************
	 * 2016.03.03  hxq  新增 总后台子类排序展示
	 */
	/**
	 * 总后台子类排序展示
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/childsort")
	public void toChildsort(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
	
		String parid=request.getParameter("parid");
		//0.获得平台所有商品的一级分类
		JSONArray childList=g_ProTypeBiz.getChildClassList(Integer.valueOf(parid));
		
		//1.返回数据
		JSONObject msg=new JSONObject();
		msg.element("msg", childList);
		Dto.printMsg(response, msg.toString());
	}
	
}
