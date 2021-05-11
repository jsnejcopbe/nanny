package com.nanny.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nanny.biz.admin.ShopList;
import com.nanny.dto.Dto;
import com.nanny.util.JsonUtil;

import net.sf.json.JSONObject;

/** 平台商品
 * @author syl
 *
 */
@Controller
@RequestMapping("admin")
public class ShopListController {
	@Resource
	private ShopList shop;
	
	/**
	 * 初始化商品数据
	 * @param data
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/init_shop")
	@ResponseBody
	public String init(String data,HttpServletRequest request){
		//0.获得传入参数
		Map<String, String> mapStr=JsonUtil.getMapStr(data);
		Set<String> keyList=mapStr.keySet();
		//1.根据查询类型 检查数据
		if((!("").equals(mapStr.get("cs")) && mapStr.get("cs")!=null) || (!("").equals(request.getParameter("cs")) && keyList.size()==2))
			if(request.getSession().getAttribute(Dto.PRO_QUERY_ITEMS)!=null)
				mapStr=(Map<String, String>) request.getSession().getAttribute(Dto.PRO_QUERY_ITEMS);
		
		if((("").equals(mapStr.get("cs")) || mapStr.get("cs")==null) && keyList.size()!=2)
			request.getSession().setAttribute(Dto.PRO_QUERY_ITEMS, mapStr);
				
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String str = json.get("shopID").toString();
		String shop_id = "null".equals(str) ? "0" : str;
		
		JSONObject obj = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		return shop.commodity_init(mapStr,shop_id,null,obj.getString("isAdmin"),mapStr.get("showDown")==null?"0":mapStr.get("showDown"));
	}
	
	@RequestMapping("/init_type")
	@ResponseBody
	public String getType(String type){
		return shop.getType(type);
	}

	@RequestMapping("/init_brand")
	@ResponseBody
	public String getBrand(HttpServletRequest request){
		JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String str = json.get("shopID").toString();
		//System.out.println(str);
		String shop_id = "null".equals(str) ? "0" : str;
		return shop.getBrand(shop_id);
	}
	
	@RequestMapping("/init_isExChange")
	public void exchange(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		int productId=Integer.parseInt(request.getParameter("productId"));
	
		
		shop.upIsexchange(productId);
		JSONObject msg=new JSONObject();
		
		msg.element("success","success");
		msg.element("msg", "操作成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
}
