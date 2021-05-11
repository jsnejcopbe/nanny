package com.nanny.controller.admin;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserAccountsRec;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.TimeUtil;
/**
 * 总后台账目
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class AdminAccountController {
	@Resource
	private SSHUtilBiz sshUtilBiz;

	@Resource
	private ShopAccountBiz shopAccountBiz;
	
	
	
	/**
	 * 商户后台账目列表（pc）
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("admin/adminAccount.html")
	public String shopAccountlist(
			@RequestParam(required = false) Integer pageIndex,
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
		/*QueryParam queryParam = new QueryParam();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();*/

		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);

		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");

		String status = request.getParameter("state");

		/*paramMap.put("userId", 0L);
		typeMap.put("userId", "eq");

		paramMap.put("otherSide", 0L);
		typeMap.put("otherSide", "eq");

		if (logmin != null && !"".equals(logmin) && logmax != null
				&& !"".equals(logmax)) {

			Object[] param = {
					TimeUtil.StrTsfToDate(logmin, "yyyy-MM-dd"),
					TimeUtil.StrTsfToDate(logmax + " 23:59:59",
							"yyyy-MM-dd HH:mm:ss") };

			paramMap.put("createTime", param);
			typeMap.put("createTime", "bet");
		} else {

			if (logmin != null && !"".equals(logmin)) {

				paramMap.put("createTime",
						TimeUtil.StrTsfToDate(logmin, "yyyy-MM-dd"));
				typeMap.put("createTime", "ge");
			}

			if (logmax != null && !"".equals(logmax)) {

				paramMap.put("createTime", TimeUtil.StrTsfToDate(logmax
						+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
				typeMap.put("createTime", "le");
			}
		}
		if (!"".equals(status) && status != null && !"-1".equals(status)) {
			paramMap.put("type", Integer.valueOf(status));
			typeMap.put("type", "eq");
		}

		orderMap.put("id", "desc");

		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);*/

		JSONObject rec =shopAccountBiz.doshopaccount(
				0, status, logmin, logmax, pageUtil);

		/*PageUtil page = sshUtilBiz.getPageCount(UserAccountsRec.class,queryParam, pageUtil);
		// 设置分页url
		page.setUrl("admin/adminAccount.html?");

		request.setAttribute("page", page);*/
		
		request.setAttribute("rec", rec);
		request.setAttribute("lognmin", request.getParameter("logmin"));
		request.setAttribute("logbmax", request.getParameter("logmax"));

		request.setAttribute("statu", request.getParameter("state"));
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/adminAccount";
	}
}
