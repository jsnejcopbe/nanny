package com.nanny.controller.shop;

import java.io.IOException;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.shop.GroupMessage;
import com.nanny.dto.Dto;
import com.nanny.model.ShopSendmsgRec;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.WeiXinSerMsgUtil;
/**
 * 信息群发微信用户
 * @author My
 *
 */
@Controller
@RequestMapping("shop")
public class GroupMessageController {

	@Resource
	private GroupMessage gm;
	/**
	 * 群发消息
	 * @param request 请求
	 * @param response 响应
	 * @param pageIndex 分页初始页码
	 * @param pageSize 分页每页多少大小
	 * @return
	 */
	@RequestMapping("/groupmessage")
	public String jump_groupmessage(HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		
		//创建session 获得
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//获取session 登录 用户 的信息	
		JSONObject jsonSession= (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		String shopID=	jsonSession.getString("shopID");//session获得粉丝id
		//地址
		String adress=request.getParameter("memo");
		//名字&&电话
		String key=request.getParameter("more_search");
		
		//实例 出 pageutil
		PageUtil pageUtil = new PageUtil();
		if (pageIndex == null || "".equals(pageIndex)) {
			pageIndex = 1;		}
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = 10;		}
		//给 page index size 赋值
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		//获取地址消息
		Integer addID=null;
		if(adress!=null&&!("").equals(adress)&&!("null").equals(adress)){
			addID=Integer.valueOf(adress);}
		
		//查询
		JSONObject jsonObject= gm.getSelUserShow(shopID,addID,key,pageUtil);
		
		//分页 输出
		request.setAttribute("pageIndex",pageUtil.getPageIndex());
		request.setAttribute("pageSize",pageUtil.getPageSize());
		request.setAttribute("pageCount", jsonObject.get("count"));
		//展示输出
		request.setAttribute("jaDZ", jsonObject.get("jaDZ"));
		request.setAttribute("jsonArray", jsonObject.get("jsonArray"));
		//给原本输入框 赋 原输入值 &&选中值
		request.setAttribute("key",key);
		request.setAttribute("adress",adress);
		
//		调用WeiXinSerMsgUtil中的pushGroupMsg方法
//		WeiXinSerMsgUtil.pushGroupMsg(openIdList, pushMsg)
		
		return Dto.getPagePath(plat_type)+"/groupmessage";
	}
	/**
	 * 发送消息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/sentMessage")
	public void sentMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//获得用户信息
		JSONObject userMsg=(JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		//消息 文本  msg
		String text=request.getParameter("text");
		// 发送对象 ( 微信 对象 )
		String array=request.getParameter("array");
		// 转换
		JSONArray jsonArray=JSONArray.fromObject(array);
		//调用发送 接口
		JSONObject jsonObject= WeiXinSerMsgUtil.pushGroupMsg(jsonArray, text);
		
		//保存发送消息
		ShopSendmsgRec bean=new ShopSendmsgRec();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setMsgCon(text);
		bean.setShopId(userMsg.getLong("shopID"));
		bean.setSta(jsonObject.toString());
		gm.addNewMsgRec(bean);
		
		System.out.println(jsonObject.toString()+"-"+array+"-"+ text);
		//返回消息
		Dto.printMsg(response, jsonObject.toString());
	}
}
