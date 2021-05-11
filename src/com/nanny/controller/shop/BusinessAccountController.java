package com.nanny.controller.shop;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.admin.BusinessBiz;
import com.nanny.biz.shop.BusinessAccountBiz;
import com.nanny.biz.shop.MerchantsFans;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.UserAccountsRec;
import com.nanny.util.JsonUtil;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.TimeUtil;

/**
 * 商户账目
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Controller
@RequestMapping("shop")
public class BusinessAccountController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private BusinessAccountBiz bab;

	/**
	 * 商户后台账目列表（pc）
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={"account/bussac-{id:\\d*}"})
	public String businessAccountlist(@PathVariable Long id,
			@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		PageUtil pageUtil = new PageUtil();
		if (pageIndex == null) {
			pageIndex = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		String shopname = bab.getShopName(id);
		String userID = bab.getShopId(id);
		
		String logmin = request.getParameter("logmin"); 
		String logmax = request.getParameter("logmax");
		String status = request.getParameter("state");

		JSONObject rec = bab.dobussaccount(userID, status, logmin, logmax, pageUtil);	
		
		request.setAttribute("rec", rec);
		request.setAttribute("lognmin", request.getParameter("logmin"));
		request.setAttribute("logbmax", request.getParameter("logmax"));
		request.setAttribute("statu", request.getParameter("state"));
		request.setAttribute("shopid", id);
		request.setAttribute("suserid", userID);
		request.setAttribute("statu", status);
		request.setAttribute("shopname", shopname);
		
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/businessAccount";
	}

}
