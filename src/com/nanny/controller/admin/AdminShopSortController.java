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

import com.nanny.biz.admin.SysGlaAreaBiz;
import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.shop.ShopDispatchingBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 总后台商户推荐排序
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class AdminShopSortController {

	//载入资源
	@Resource(name="sysGlaAreaBiz")
	private SysGlaAreaBiz g_AreaBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopBiz;
	@Resource(name="shopDispatchingBiz")
	private ShopDispatchingBiz g_DispaBiz;
	
	/**
	 * 前往店铺排序页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/shopsort")
	public ModelAndView toShopSortPage(HttpServletRequest request,HttpServletResponse response){
		JSONObject data=new JSONObject();
		HttpSession session=request.getSession();
		int pathType=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得传入参数
		String type=request.getParameter("type");
		String name=request.getParameter("name");
		String page=request.getParameter("page");
		
		//1.组织pageutil
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageSize(20);
		pageUtil.setPageIndex(page==null?1:Integer.valueOf(page));
		
		//2.根据条件查询记录
		if(type!=null)
			data=g_AreaBiz.getUsedAddressList(name, pageUtil);//地址列表
		else
			data=g_ShopBiz.getSysShopList(pageUtil, name);//商户列表
		
		//3.前往页面
		ModelAndView mav=new ModelAndView(Dto.getPagePath(pathType)+"/adminShopSort");
		mav.addObject("backData", data);
		mav.addObject("name", name==null?"":name);
		mav.addObject("type", type);
		return mav;
	}
	
	/**
	 * 查询指定地址下的商户信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/shopaddlist")
	public void findShopInAddress(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
		String shopID=request.getParameter("shopID");
		String addID=request.getParameter("addID");
		
		//1.组织地址数据
		JSONArray addList=new JSONArray();
		if(shopID!=null && !("").equals(shopID))
			addList=g_DispaBiz.getShopDispatchByShopID(Long.valueOf(shopID));
		else if(addID!=null && !("").equals(addID))
			addList.add((new JSONObject()).element("adressID", addID));
		
		//2.查询指定地址下的商户信息
		JSONArray data=new JSONArray();
		for(int i=0;i<addList.size();i++)
		{
			JSONObject tmpObj=new JSONObject();
			JSONArray addArray=g_DispaBiz.getShopListByAddID(addList.getJSONObject(i).getLong("adressID"));
			tmpObj.element("addName", addArray.getJSONObject(0).getString("addName"));
			tmpObj.element("addDet", addArray.getJSONObject(0).getString("detAdd"));
			tmpObj.element("addID", addList.getJSONObject(i).getLong("adressID"));
			tmpObj.element("addArray", addArray);
			data.add(tmpObj);
		}
		
		//3.返回消息
		Dto.printMsg(response, data.toString());
	}
	
	/**
	 * 更新商户排序
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/shopareasort")
	public void updateShopSort(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
		JSONArray array=JSONArray.fromObject(request.getParameter("dataArray"));
		
		//1.循环更新配送地区排序
		for(int i=0;i<array.size();i++)
		{
			JSONObject tmpObj=array.getJSONObject(i);
			g_DispaBiz.updateShopDispatchSortByID(tmpObj.getLong("dispaID"), tmpObj.getInt("index"));
		}
		
		//2.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "更新成功");
		
		Dto.printMsg(response, msg.toString());
	}
}
