package com.nanny.controller.shop;

import java.io.IOException;
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

import com.nanny.biz.shop.ShopProApplyBiz;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProApply;
import com.nanny.model.ShopProBrand;
import com.nanny.model.UserAccountsRec;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;

@Controller
public class ShopProApplyController {

	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ShopProApplyBiz shopProApplyBiz;
	
	
	
	/**
	 * 商户商品添加申请（手机）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/jump_Apply.html")
	public String jumpApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		JSONArray brand=JSONArray.fromObject(sshUtilBiz.getObjectList(ShopProBrand.class, null, null));
		request.setAttribute("brand", brand);
		return Dto.getPagePath(url)+"/addproApply";
	}
	
	
	
	/**
	 * 商户商品添加申请（pc）
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/addpcApply.html")
	public void addpcApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		
		String img=request.getParameter("fi");
		String brandid=request.getParameter("brandId");
		String typeid1=request.getParameter("shoptype1");
		String typeid2=request.getParameter("shoptype2");
		
		ShopProApply spa=new ShopProApply();
		if(brandid!=null&&!"-1".equals(brandid)&&!"".equals(brandid)){
			spa.setBrandId(Long.valueOf(brandid));
		}
		spa.setShopImg(img);
		
		String typeid="".equals(typeid2)||"-1".equals(typeid2)?typeid1:typeid2;
		
		spa.setTypeId(Long.valueOf(typeid));
		spa.setCraeteTime(DateUtils.getTimestamp());
		spa.setShopId(json.getLong("shopID"));
		spa.setStatus(0);
		String mag=shopProApplyBiz.addProApply(spa);
		
		
		JSONObject msg=new JSONObject();
		msg.element("msg", mag);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	@RequestMapping("shop/proapply.html")
	public String proapplist(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 5;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		QueryParam queryParam = new QueryParam();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		String sta=request.getParameter("stats");
		if (!"".equals(sta) && sta != null && !"-1".equals(sta)) {
			paramMap.put("status", Integer.valueOf(sta));
			typeMap.put("status", "eq");
		}

		paramMap.put("shopId", json.getLong("shopID"));
		typeMap.put("shopId", "eq");
		
		orderMap.put("id", "desc");

		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);
		
		
		JSONArray app=shopProApplyBiz.doProApply(json.getInt("shopID"),sta,pageUtil);
		PageUtil page = sshUtilBiz.getPageCount(ShopProApply.class,queryParam, pageUtil);
		// 设置分页url
		page.setUrl("shop/proapply.html?");

		request.setAttribute("page", page);
		request.setAttribute("app", app);
		request.setAttribute("stats", request.getParameter("stats"));
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/proApplyList";
	}
	
	
	@RequestMapping("addProApply.html")
	public String jump_proapply(HttpServletRequest request,HttpServletResponse response){
		
		request.setAttribute("addtype", "0");
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/addproApply";
		
	}
	
	
	
}
