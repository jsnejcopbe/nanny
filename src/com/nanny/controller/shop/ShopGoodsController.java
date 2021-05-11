 package com.nanny.controller.shop;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nanny.biz.admin.ShopList;
import com.nanny.biz.shop.ShopGoods;
import com.nanny.dto.Dto;
import com.nanny.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/** 市场选货
 * @author syl
 *
 */
@Controller
@RequestMapping("shop")
public class ShopGoodsController {  
	@Resource
	private ShopGoods good;
	@Resource
	private ShopList shop;
	
	@RequestMapping("/init_shop")
	@ResponseBody
	public String init(String data,HttpServletRequest request){
		Map<String, String> mapStr = data==null ? new HashMap<String, String>() : JsonUtil.getMapStr(data);
		JSONObject obj = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		return shop.commodity_init(mapStr,"0", obj.get("shopID").toString() ,obj.getString("isAdmin"),"0");
	}
	
	@ResponseBody
	@RequestMapping("/goods/add")
	public String start_goods(String data,HttpServletRequest request){
//		List<String> list = JsonUtil.getList(data);
		JSONArray list=JSONArray.fromObject(data);
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String str = json.get("shopID").toString();
		String shop_id = "null".equals(str) ? "0" : str;
		return good.start_goods(list,shop_id);
	}
	/************************
	 * 16.03.17
	 * HXQ 
	 * 
	 * *********************************************************************************************************************************************************************/
	
	/**
	 * 商品ID 查看供应商
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("pro_supplier")
	public void shop_supinfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String proid=request.getParameter("proid");
			
		JSONArray  suplist  =good.pro_sup(Integer.valueOf(proid));
		
		JSONObject msg = new JSONObject();
		msg.element("msg",suplist );
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());	
	}
	
}
