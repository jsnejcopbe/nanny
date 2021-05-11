package com.nanny.controller.global;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.nanny.biz.global.store.ProductDetailsBiz;
import com.nanny.biz.global.store.ShopProTypeBiz;
import com.nanny.dto.Dto;

@Controller
public class ProductDetailsController {

	// 加载资源
	@Resource(name="productdetails")
	private ProductDetailsBiz prodetails;
	@Resource(name="shopProTypeBiz")
	private ShopProTypeBiz g_ProTypeBiz;

	/**
	 * 商品详情页面
	 * 
	 * @param shopID
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return
	 */
	@RequestMapping(value={"pro-det-{id:\\d*}"})
	public ModelAndView toproductdetails(@PathVariable String id,  HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 以上获得shopID 
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		String proCarList=request.getParameter("proCarList");
		Long parID=null;
		ModelAndView mav = new ModelAndView(Dto.getPagePath(plat_type)+"/productdetails");
	
		// 根据id获得对应商品信息，装入容器
		JSONArray productdetailslist = prodetails.getProductDedailsByshopID(id);	
		
		//查询商品上级ID
		if(productdetailslist.size()>0)
		{
			Long classID=productdetailslist.getJSONObject(0).getLong("typeID");
			Long shopID=productdetailslist.getJSONObject(0).getLong("shopID");
			JSONObject classMsg=g_ProTypeBiz.getClassDetById(classID);
			if(classMsg.getLong("parID")==0)
				parID=classMsg.getLong("id");
			else
				parID=classMsg.getLong("parID");
			mav.addObject("shopID", shopID);
			mav.addObject("methodName", "storepage");
		}
		
		mav.addObject("pdlist", productdetailslist);
		mav.addObject("proID", id);
		mav.addObject("proCarList", proCarList);
		mav.addObject("parID", parID);
		return mav;
	}
}
