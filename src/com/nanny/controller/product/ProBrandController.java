package com.nanny.controller.product;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.product.ProBrandBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;
import com.nanny.model.ShopProBrand;


import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;



/**
 * 商品品牌管理
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class ProBrandController {
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ProBrandBiz proBrandBiz;
	
	
	/**
	 * 商品品牌列表
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping("admin/probrand.html")
	public String brandlist(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request){
		PageUtil pageUtil = new PageUtil();
		
		if(pageIndex==null){
			
			pageIndex = 1;
		}
		
		if(pageSize==null){
			
			pageSize = 12;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		QueryParam queryParam = new QueryParam();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		
		String name=request.getParameter("name");
		
		if(!"".equals(name) && name !=null && !"0".equals(name) ){
			
			paramMap.put("name", "%"+name+"%");
			typeMap.put("name", "like");
			
			}
		
		orderMap.put("id", "desc");
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);
		
		
		JSONArray Probrand=proBrandBiz.BrandList(null, name, pageUtil);
		PageUtil page = sshUtilBiz.getPageCount(ShopProBrand.class, queryParam, pageUtil);
		// 设置分页url
		page.setUrl("admin/probrand.html?");
		request.setAttribute("Probrand", Probrand);
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("page", page);
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/proBrand";
	}
	
	/**
	 * 商品品牌更新
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updbrand.html")
	public void updbrand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String id=request.getParameter("id");
		String name=request.getParameter("bname");
		String url=request.getParameter("fi");
		
		
		JSONObject bean=new JSONObject();
		bean.element("id", id);
		bean.element("name", name);
		bean.element("url", url);

		
		proBrandBiz.updateProbrand(bean);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 商品品牌添加
	 */
	
	@RequestMapping("admin/addbrand.html")
	public void addProbrand(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		 JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 long shopid;
		 
		 int isAdmin=obj.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid=0L;
		 	}else{
			 shopid=obj.getLong("shopID");
		 }
		 
		
		//获取页面数据
		String name=request.getParameter("bname");
		String url=request.getParameter("fi");
	
		JSONObject msg=new JSONObject();
		
		//组织保存
		if(name!=null&&!"".equals(name)){
		ShopProBrand bean=new ShopProBrand();
		bean.setIcon(url);
		bean.setName(name);
		bean.setCreateTime(DateUtils.getTimestamp());
		
		bean.setShopId(shopid);
		sshUtilBiz.saveObject(bean);
			msg.element("msg", "success");
		}else{
			msg.element("msg", "没填品牌名称");
		}
		//返回页面消息
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 删除品牌
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/delbrand.html")
	public void delProject(@RequestParam long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		ShopProBrand bean=new ShopProBrand();
		bean.setId(id);
		sshUtilBiz.deleteObject(bean);
		
		
		JSONObject msg=new JSONObject();
	 	msg.element("msg", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
 }

