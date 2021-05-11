package com.nanny.controller.user;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.user.UserBaseMsgBiz;
import com.nanny.biz.user.UserShoppingCarBiz;
import com.nanny.dto.Dto;

/**
 * 买家购物车控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class UsersShoppingCarController {

	//载入资源
	@Resource(name="userShoppingCarBiz")
	private UserShoppingCarBiz g_CarBiz;
	@Resource(name="userBaseMsgBiz")
	private UserBaseMsgBiz g_UserMsgBiz;
	
	/**
	 * 向买家购物车添加商品记录
	 * @throws IOException 
	 */
	@RequestMapping("users/addprotocar")
	public void addProducToCar(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONObject msg=new JSONObject();
		JSONObject tmpObj=new JSONObject();
		//0.获得传入参数
		JSONArray proData=JSONArray.fromObject(request.getParameter("arrayData"));
		Long shopID=Long.valueOf(request.getParameter("shopID"));
		String userID=request.getParameter("userID");
		
		//1.向购物车添加商品
		if(!("none").equals(userID)){
			JSONObject userMsg=g_UserMsgBiz.getUserMsgByUserID(Long.valueOf(userID));//检测用户账户类型
			
			if(("null").equals(userMsg.getString("shopID")))
			{
				g_CarBiz.addProduct(proData,Long.valueOf(userID),shopID);
				msg.element("jump", "/users/shopcar.html");
			}else
				msg.element("msg", "检测到您为商家账户，\n为避免刷单,\n您无法下单");
		}
		else{
			tmpObj.element("data", proData);
			tmpObj.element("shopID", shopID);
			msg.element("jump", "/newUserFor.html");
		}
		
		//2.返回消息
		request.getSession().setAttribute("tmpShopCarData", tmpObj);
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 特殊方法  初次注册用户添加商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/adddprofornew")
	public void addProForRegister(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONObject msg=new JSONObject();
		if(request.getSession().getAttribute("tmpShopCarData")!=null)
		{
			//0.获得数据
			JSONObject tmpObj=(JSONObject) request.getSession().getAttribute("tmpShopCarData");
			Long userID=Long.valueOf(request.getParameter("userID"));
			
			//1.向购物车添加商品
			if(!tmpObj.isEmpty()){
				g_CarBiz.addProduct(tmpObj.getJSONArray("data"),Long.valueOf(userID),tmpObj.getLong("shopID"));
				msg.element("msg", "1");
			}else
				msg.element("msg", "0");
				
		}else
			msg.element("msg", "0");
		
		//2.返回消息
		Dto.printMsg(response, msg.toString());
	}
}
