package com.nanny.controller.shop;

import java.io.IOException;
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
import com.nanny.biz.shop.ShopProductExchangeBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.UserAccountsRec;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.TimeUtil;

/**
 * 兑换明细
 * @author chen
 *
 */
@Controller
public class ShopProductExchangeController {


	@Resource
	private ShopProductExchangeBiz spx;
	
	/**
	 * 商户端兑换明细列表
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("shop/shopProductExchange")
	public String shopProductExchange(@RequestParam(required = false) Integer pageIndex,
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
		bean.element("shop_id", json.get("shopID"));
		bean.element("queryName", queryName);
		
		JSONObject obj=spx.doShopProductExchange(bean, pageUtil);
		
		JSONArray arr=obj.getJSONArray("shopProExc");
		
		request.setAttribute("obj",obj);
		request.setAttribute("arr", arr);
		request.setAttribute("logmin",logmin );
		request.setAttribute("logmax",logmax );
		request.setAttribute("memo",memo);
		request.setAttribute("queryName", queryName);
		//区分商家跟总后台标志
		request.setAttribute("is_shop",1);
		
		return "pc/shopProductExchange";
	}
	/**
	 * 兑换确认
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/ProductExchangeConfirm")
	public void shopUprMemoConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int  id=Integer.parseInt(request.getParameter("uprId"));
		spx.upUserPointredeemMemo(id);
		
		JSONObject msg=new JSONObject();
		msg.element("success", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	
	/**
	 * 拒绝兑换
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/UprRefuse")
	public void shopUprRefuse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int  id=Integer.parseInt(request.getParameter("uprId"));
			
		spx.delUserPointRefuse(id);
		
	
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println("已拒绝，退还积分");
	}
}
