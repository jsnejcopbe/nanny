package com.nanny.controller.shop;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.product.ProTypeBiz;
import com.nanny.biz.shop.ShopDispatchingBiz;
import com.nanny.biz.shop.orders.Refund;
import com.nanny.dto.Dto;
import com.zhuoan.util.TimeUtil;

@Controller
@RequestMapping("shop")
public class ShopJumpController {
	
	@Resource(name="shopDispatchingBiz")
	private ShopDispatchingBiz g_dispatchBiz;
	@Resource
	private ProTypeBiz g_TypeBiz;
	@Resource
	private Refund refund;
	
	
	private static final String TEMP_SHOPID = "temp_shopID";
	//商户主页跳转
//	@RequestMapping("/shopIndex")
//	public String jump_index(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
//		return Dto.getPagePath(plat_type)+"/shopIndex";
//	}
	
	//商品列表跳转
	@RequestMapping("/productList")
	public String jump_product(HttpServletRequest request){
		String checkType=request.getParameter("cs");
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		if(plat_type==1)
		{
			JSONArray parClass=g_TypeBiz.getFirstProCla((long)0, (long)0);
			request.setAttribute("parClass", parClass);
		}	
		request.setAttribute("checkType", checkType);
		return Dto.getPagePath(plat_type)+"/shopProduct";
	}

	//商户注册
	@RequestMapping("/exp_{type}.html")
	public String jump_apply(@PathVariable() String type,HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute(Dto.SHOP_ADD_TYPE, type);
		session.setMaxInactiveInterval(1000*10);
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(plat_type)+"/shopApply";
	}
	
	@RequestMapping("/shop_exp_{type}.html")
	public String jump_apply_shop(@PathVariable() Long type,HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		request.setAttribute("shopID", type);
		return Dto.getPagePath(plat_type)+"/shopApply";
	}
	
	/** 一键铺货
	 * @return
	 */
	@RequestMapping("/goods")
	public String jump_goods(HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(plat_type)+"/shopgoods";
	} 
	
	
	/**商户信息
	 * @return
	 */
	@RequestMapping("/shopInfo")
	public String jump_shopinfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//0.查询商户的基础信息&配送地址列表
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		JSONArray dispacthList=g_dispatchBiz.getShopDispatchByShopID(userMsg.getLong("shopID"));
		request.setAttribute("dispacthList", dispacthList);
		
		return Dto.getPagePath(plat_type)+"/shopinfo";
	}
	
	/** 粉丝页
	 * @param request
	 * @return
	 */
	@RequestMapping("/fansinit")
	public String jump_fansinit(HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		session.removeAttribute(TEMP_SHOPID);
		
		
		return Dto.getPagePath(plat_type)+"/merchants_fans";
	}

	/** 查看指定的粉丝页
	 * @param shopID
	 * @param request
	 * @return
	 */
	@RequestMapping("/fans/findby_{shopID}.html")
	public String jump_fansinit(@PathVariable() String shopID,HttpServletRequest request) {
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		session.setAttribute(TEMP_SHOPID, shopID);
		return Dto.getPagePath(plat_type)+"/merchants_fans";
	}
	
	/** 退款页
	 * @param request
	 * @return
	 */
	@RequestMapping("/refund")
	public String jump_refund(HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		// 获取退款信息
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		JSONArray orderMsg = refund.getRefunMsg(json.getLong("shopID"));
		
		if(!"".equals(orderMsg) && orderMsg!=null){
			orderMsg=TimeUtil.transTimestamp(orderMsg , "createTime" , "yyyy-MM-dd HH:mm:ss");
		}
		request.setAttribute("orderMsg", orderMsg);
		
		return Dto.getPagePath(plat_type)+"/refund_list";
	}
	
	
	//=========================手机专用
	@RequestMapping("/setDelivery")
	public String jump_setDelivery(){
		return "mobile/setDelivery";
	}
	
	@RequestMapping("/getsecclassajax")
	public void getSecClass(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
		Long type=Long.valueOf(request.getParameter("type"));
		//1.查询二级分类
		JSONArray parClass=g_TypeBiz.getFirstProCla((long)0, type);
		//2.返回结果
		Dto.printMsg(response, parClass.toString());
	}
	
	@RequestMapping("/etitshoptel")
	public String shoptel(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
			String shoptel=	json.getString("shopTel");
			 String[]  tel=shoptel.split(",");
			 String tel1=null;
			 String tel2=null;
			 if(tel.length>1){
				 
				 tel1= shoptel.split(",")[0];
				 tel2= shoptel.split(",")[1];
			 }else{
				 tel1=shoptel;
			 }
			 request.setAttribute("tel1", tel1);
			request.setAttribute("tel2", tel2);
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/shopedittel";
	}

	
}
