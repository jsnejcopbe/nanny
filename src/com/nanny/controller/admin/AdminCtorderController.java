package com.nanny.controller.admin;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.user.UsersOrderBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;

@Controller
public class AdminCtorderController {
	@Resource
	private UsersOrderBiz uob;	

	
	
	@RequestMapping("admin/userOrderList.html")
	public String showUserOr(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 10;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		String uid=request.getParameter("uid");
		String sta=request.getParameter("sta");
		
		JSONObject  uorder=uob.doUserorder(Integer.valueOf(uid), sta, null, pageUtil);
		
		request.setAttribute("uid",request.getParameter("uid"));
		request.setAttribute("sta", request.getParameter("sta"));
		request.setAttribute("uorder", uorder);
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/adminUserOrder";
		
	}
	
	
	
	/**
	 * 订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/userOrDetails.html")
	public String Orderdetails(HttpServletRequest request,HttpServletResponse response) {
		
		PageUtil pageUtil = new PageUtil();
	
		pageUtil.setPageIndex(1);
		pageUtil.setPageSize(10);
	
		String uid=request.getParameter("uid");
		String orid=request.getParameter("orid");

		JSONObject order= uob.doUserorder(Integer.valueOf(uid), null,orid,pageUtil);
		
		JSONArray order1=order.getJSONArray("order");
		request.setAttribute("uid",request.getParameter("uid"));
		request.setAttribute("order", order1.get(0));
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/adminUserOrderDet";
		
	}
}
