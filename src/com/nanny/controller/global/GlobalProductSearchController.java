package com.nanny.controller.global;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.global.store.ShopProDataBiz;
import com.nanny.biz.global.store.ShopProTypeBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 店铺商品查询控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class GlobalProductSearchController {

	//载入资源
	@Resource(name="shopProTypeBiz")
	private ShopProTypeBiz g_ProTypeBiz;
	@Resource(name="shopProDataBiz")
	private ShopProDataBiz g_ProDataBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopMsgBiz;
	
	/**
	 * 前往商品查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"search-shop-{shopID:\\d*}"})
	public ModelAndView toGloProSearchPage(@PathVariable Long shopID,HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		String proCarList=request.getParameter("proCarList");
		
		//0.获得商品种类树状数据
		JSONArray treeData=g_ProTypeBiz.getClassTreeData(shopID);
		
		//1.获得商户基础信息
		JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(shopID);
		
		//1.返回页面
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopProSearch");
		mav.addObject("treeData", treeData);
		mav.addObject("shopID", shopID);
		mav.addObject("pageSize", 20);
		mav.addObject("proCarList", proCarList);
		mav.addObject("shopMsg", shopMsg);
		mav.addObject("methodName", "storepage");
		return mav;
	}
	
	/**
	 * 查询商品数据
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("proquery")
	public void queryProMsg(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.接收传递的参数
		String name=request.getParameter("query");
		String classID=request.getParameter("classID");
		Long shopID=Long.valueOf(request.getParameter("shopID"));
		int page=Integer.valueOf(request.getParameter("page"));
		int pageSize=Integer.valueOf(request.getParameter("pageSize"));
		
		//1.组织查询数据
		JSONObject condition=new JSONObject();
		if(!("").equals(name))
			condition.element("name", name);
		if(!("").equals(classID))
			condition.element("classID", classID);
		condition.element("shopID", shopID);
		
		//2.组织分页数据
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(page);
		pageUtil.setPageSize(pageSize);
		
		//3.查询数据
		JSONArray data=g_ProDataBiz.getProDataByCondition(condition, pageUtil);
		
		Dto.printMsg(response, data.toString());
	}
}
