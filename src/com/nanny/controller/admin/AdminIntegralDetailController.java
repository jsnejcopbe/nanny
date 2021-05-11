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


import com.nanny.biz.shop.ShopIntegralDetailBiz;
import com.nanny.dto.Dto;

import com.zhuoan.ssh.bean.PageUtil;


/**
 * 积分明细
 * @author chen
 *
 */
@Controller
public class AdminIntegralDetailController {


	@Resource
	private ShopIntegralDetailBiz sid;
	
	/**
	 * 总后台积分明细列表
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("admin/IntegralDetail")
	public String shopIntegralDetail(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response)
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
		
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
	
		String status=request.getParameter("status");
		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");
		
		JSONObject bean=new JSONObject();
		
		bean.element("flag",2);
		bean.element("shop_id", json.getString("shopID"));
		bean.element("logmin", logmin);
		bean.element("logmax", logmax);
		bean.element("status",status);
		
	
		JSONObject obj=sid.doShopIngeralDetail(bean, pageUtil);
		
		JSONArray arr=obj.getJSONArray("shopIntgral");
		
		request.setAttribute("logmin",logmin );
		request.setAttribute("logmax",logmax );
		request.setAttribute("status", status);
		request.setAttribute("obj",obj);
		request.setAttribute("arr",arr);
		return "pc/shopIntegralDetail";
	}



}
