package com.nanny.controller.shop;

import java.text.DecimalFormat;
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
import com.nanny.biz.shop.ShopIntegralDetailBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.UserAccountsRec;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.TimeUtil;

/**
 * 积分明细
 * @author chen
 *
 */
@Controller
public class ShopIntegralDetailController {
	@Resource
	private SSHUtilBiz sshUtilBiz;

	@Resource
	private ShopIntegralDetailBiz sid;
	
	/**
	 * 商户端积分明细列表
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("shop/shopIntegralDetail")
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
		//标志用户的身份
		bean.element("flag",2);
		bean.element("shop_id", json.get("shopID"));
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
	/**
	 * 积分pc端
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("shop/shopIntegralDetailPc")
	public String shopIntegralDetailPc(@RequestParam(required = false) Integer pageIndex,
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
		bean.element("shop_id", json.get("shopID"));
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
		
		
		return "mobile/shopintegraldetail";
		
	}

}
