package com.nanny.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanny.biz.shop.ShopBasis;
import com.nanny.biz.user.Salesman;
import com.nanny.dto.Dto;
import com.nanny.model.BaseSaleman;
import com.nanny.model.SysGlobalArea;
import com.nanny.util.JsonUtil;
import com.zhuoan.util.CookiesUtil;

import net.sf.json.JSONObject;

/**
 * @author syl
 * 业务员
 */
@Controller
@RequestMapping("salesman")
public class SalesmanController {
	@Resource
	private Salesman sale;
	@Resource
	private ShopBasis shop;

	/** 
	 * 业务员页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String jump_salesman_index(HttpServletRequest request,HttpServletResponse response,Model model){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		BaseSaleman bs = (BaseSaleman) session.getAttribute(Dto.LOGIN_SALEMAN);
		if(bs == null){
			
			return Dto.REDIRECT+"salesman.html";
		}else{
		sale.init(model,bs.getId());
		
		CookiesUtil.addCookie(Dto.SALES_LOGIN_USERNAME,bs.getTel(), response);
		CookiesUtil.addCookie(Dto.SALES_LOGIN_PASSWORD,bs.getPassword(), response);
		
		
		String username=CookiesUtil.getCookie(Dto.SALES_LOGIN_USERNAME, request);
		String password=CookiesUtil.getCookie(Dto.SALES_LOGIN_PASSWORD, request);
		
		System.out.println(username+","+password);
		return Dto.getPagePath(plat_type)+"/salesman_index";
		}
	}
	
	@RequestMapping("/jumpaudit.html")
	public String jump_store_audit(HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(plat_type)+"/store_audit";
	}
	
	@RequestMapping("/auditlist.html")
	@ResponseBody
	public String get_auditlist(HttpServletRequest request,String data){
		HttpSession session = request.getSession();
		BaseSaleman bs = (BaseSaleman) session.getAttribute(Dto.LOGIN_SALEMAN);
		Map<String, String> mapStr = JsonUtil.getMapStr(data);
		return shop.apply_init_list(mapStr,bs.getGuid());
	}
	
	@RequestMapping("/arealist.html")
	public String jump_area_html(HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(plat_type)+"/areahtml";
	}
	 
	
	@RequestMapping("/getAdresslist")
	@ResponseBody
	public String getShopAdress(String data){
		return shop.getShopAdress(data);
	}
	
	
	@RequestMapping("/adress/getAdresslist")
	@ResponseBody
	public String getAdresslist(HttpServletRequest request,String data){
		Map<String, String> map = data == null ? null : JsonUtil.getMapStr(data.replace(" ", ""));
		/*JSONObject login = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String id = login.getString("shopID");*/
		return shop.getAdresslist(null,map);
	}
	
	@RequestMapping("/adress/add")
	@ResponseBody
	public String adressAdd(String data,String shop_id,HttpServletRequest request){
		return shop.adressAdd((SysGlobalArea) JsonUtil.getBean(data, SysGlobalArea.class),shop_id);
	}

	@RequestMapping("/adress/edit")
	@ResponseBody
	public String adressEdit(String adressID,String shopID){
		return shop.adressEdit(adressID,shopID);
	}
	
	@RequestMapping("/adress/getAdress")
	@ResponseBody
	public String getAdress(){
		return shop.getAdress();
	}
}
