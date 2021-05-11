package com.nanny.controller.supplier;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.supplier.SupplierShopBiz;
import com.nanny.dto.Dto;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 供应商与商户联系
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class SupplierShopController {
		
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource 
	private SupplierShopBiz ssb;
	
	@RequestMapping("supplier/supshopth.html")
	public String supshop(	@RequestParam(required = false) Integer pageIndex,
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
		
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_SUPPLIER);
		String shname=request.getParameter("many");
		
		JSONObject obj=new JSONObject();
		obj.element("sid", json.getLong("supplierID"));
		obj.element("shname",shname);
		
		
		
		JSONObject shbean=ssb.getsupshop(obj, pageUtil);
		
		request.setAttribute("shbean", shbean);
		request.setAttribute("shopname", request.getParameter("many"));
		
		int url = (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/supshoplist";
	}
}
