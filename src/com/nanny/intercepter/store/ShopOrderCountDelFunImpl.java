package com.nanny.intercepter.store;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.admin.ShopApplyMsgBiz;
import com.nanny.biz.product.ProductBiz;
import com.nanny.biz.shop.ShopProApplyBiz;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;

/**
 * 订单数量计算后置拦截
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("shopOrderCountDelFun")
public class ShopOrderCountDelFunImpl implements CommonDelFun {
	
	//载入资源
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderBiz;
	@Resource(name="shopApplyMsgBiz")
	private ShopApplyMsgBiz g_ApplyMsgBiz;
	@Resource
	private ShopProApplyBiz g_ProAppBiz;
	@Resource
	private ProductBiz g_ProBiz;

	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
		//0.获得session中的用户信息
		HttpSession session=request.getSession();
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		//1.当用户为商户时，进行订单数量判断
		//session.getAttribute(Dto.ORDER_COUNT_WAIT_DEL)==null && 
		if(userMsg!=null && !("null").equals(userMsg.getString("shopID")) && mav!=null)
		{
			Long shopID=userMsg.getLong("shopID");
			int count=g_OrderBiz.getCountOfWaitDetOrders(shopID);
			session.setAttribute(Dto.ORDER_COUNT_WAIT_DEL, count);
			
			int newshop= g_ProBiz.dodaynewprocount();
			mav.addObject("newshopCount", newshop);
		}
		//2.当商户为管理员时，进行待处理商户申请数和新增商品申请数统计
		//session.getAttribute(Dto.APPLY_COUNT_WAIT_DEL)==null && 
		if(userMsg!=null && ("1").equals(userMsg.getString("isAdmin")) && mav!=null)
		{
			//待处理商户申请数
			int count=g_ApplyMsgBiz.getWaitDealApplyCount();
			session.setAttribute(Dto.APPLY_COUNT_WAIT_DEL, count);
			//待处理新增商品申请数
			int count2=g_ProAppBiz.getWaitDelApplyCount();
			session.setAttribute(Dto.PRO_APPLY_COUNT_WAIT_DEL, count2);
		}
	}

	public boolean blReturnDelFu(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		return false;
	}

}
