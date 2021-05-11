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


import com.nanny.biz.shop.ShopProductExchangeBiz;
import com.nanny.dto.Dto;

import com.zhuoan.ssh.bean.PageUtil;


/**
 * 商品兑换明细
 * @author chen
 *
 */
@Controller
public class AdminProductExchangeController {


	@Resource
	private ShopProductExchangeBiz spx;
	
	/**
	 * 总后台兑换明细
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("admin/ProductExchangeDetail")
	public String AdminProductExchange(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 10;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		String memo=request.getParameter("memo");
		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");
		String queryName=request.getParameter("queryName");
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		
		JSONObject bean=new JSONObject();
		bean.element("flag",2);
		bean.element("logmin", logmin);
		bean.element("logmax", logmax);
		bean.element("memo",memo);
		bean.element("shop_id", json.getString("shopID"));
		bean.element("queryName", queryName);
	
		JSONObject obj=spx.doShopProductExchange(bean, pageUtil);
		
		JSONArray arr=obj.getJSONArray("shopProExc");
		
		request.setAttribute("obj",obj);
		request.setAttribute("arr", arr);
		request.setAttribute("logmin",logmin );
		request.setAttribute("logmax",logmax );
		request.setAttribute("memo",memo);
		request.setAttribute("queryName",queryName);
		request.setAttribute("is_shop",2);
		
		return "pc/shopProductExchange";
	}


}
