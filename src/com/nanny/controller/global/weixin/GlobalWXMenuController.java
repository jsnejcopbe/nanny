package com.nanny.controller.global.weixin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.dto.Dto;
import com.zhuoan.util.weixin.Doweixin;
import com.zhuoan.util.weixin.WeixinUserMsgPush;

/**
 * 全局微信自定义菜单控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class GlobalWXMenuController {

	/**
	 * 前往微信菜单管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/wxmenu")
	public ModelAndView toWxMenuManager(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得access_token
		JSONObject access_token=Doweixin.getAccessToken();
		//2.查询当前服务器的菜单内容
		JSONObject menuData=WeixinUserMsgPush.getWxMenuByApi(access_token);
		//3.组织数据传递
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/adminWeixinMenu");
		if(menuData.containsKey("errcode")){
			JSONArray tmpArray=new JSONArray();
			tmpArray.element((new JSONObject()).element("name", "菜单名称")
											   .element("url", ""));
			mav.addObject("menuList", (tmpArray));
			mav.addObject("menuListStr", tmpArray.toString());
		}
		else{
			mav.addObject("menuList", menuData.getJSONObject("menu").getJSONArray("button"));
			mav.addObject("menuListStr", menuData.getJSONObject("menu").getJSONArray("button").toString());
		}
		return mav;
	}
	
	/**
	 * 更新微信菜单
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/updatewxmenu")
	public void updateWeixinMenu(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject access_token=Doweixin.getAccessToken();
		//0.获得传入参数
		JSONArray array=JSONArray.fromObject(request.getParameter("json"));
		
		//1.检查参数
		for(int i=0;i<array.size();i++)
		{
			JSONObject obj=array.getJSONObject(i);
			if(obj.getJSONArray("sub_button").size()==0)
				obj.remove("sub_button");
			else{
				obj.remove("type");
				obj.remove("view");
				obj.remove("url");
			}
		}
		
		//2.组织数据
		JSONObject data=new JSONObject();
		data.element("button", array);
		System.out.println("create data is "+data.toString()+"\n");
		
		//3.删除旧菜单
		WeixinUserMsgPush.deleteWxMenuByApi(access_token);
		
		//4.添加新菜单
		JSONObject backmsg=WeixinUserMsgPush.createWXMenu(data);
		System.out.println("back msg is "+backmsg.toString()+"\n");
		
		//5.返回消息
		Dto.printMsg(response, backmsg.toString());
	}
}
