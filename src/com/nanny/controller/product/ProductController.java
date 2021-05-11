package com.nanny.controller.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.nanny.biz.product.ProductBiz;
import com.nanny.controller.product.bean.BatchInven;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProApply;
import com.nanny.model.ShopProAttribute;
import com.nanny.model.ShopProBrand;
import com.nanny.model.ShopProType;

import com.nanny.util.JsonUtil;

import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.QueryParam;

/**
 * 商品
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Controller
public class ProductController {

	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ProductBiz productBiz;
	
	/**
	 * 添加商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/add_product.html")
	public void addproduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 JSONObject obje=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 String shopid;
		 int isAdmin=obje.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid="0";
		 	}else{
			 shopid=obje.getString("shopID");
		 }
		
		
		//data1
		String name=request.getParameter("shName");
		String costPrice=request.getParameter("costPrice");
		String price=request.getParameter("shopPrice");
		String disPrice=request.getParameter("shopDisprice");
		String inventory=request.getParameter("Inventory");
		String isUse=request.getParameter("isUse");
		String isCommission=request.getParameter("isCom");
		//data2
		String cover=request.getParameter("fi");
		String proDes=request.getParameter("prodes");
		//data3
		String fatyId=request.getParameter("shoptype1");
		String typeId=request.getParameter("shoptype2");
		String brandId=request.getParameter("brandId");
		String proTag=request.getParameter("proTag");
		String data4= request.getParameter("data4");
		
		JSONObject bean=new JSONObject();
		bean.element("name", name);
		bean.element("costPrice",costPrice);
		bean.element("price", price);
		bean.element("disPrice", disPrice);
		bean.element("inventory", inventory);
		bean.element("isUse", isUse);
		bean.element("isCommission", isCommission);
		bean.element("cover", cover);
		bean.element("proDes", proDes);
		bean.element("typeId", typeId);
		bean.element("fatyId", fatyId);
		bean.element("brandId", brandId);

		bean.element("proTag", proTag);
		bean.element("shopid", shopid);
		bean.element("data4", data4);
		
		
		
		String sd= productBiz.addProduct(bean);
		  
		JSONObject msg=new JSONObject();
		
		msg.element("msg", sd);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
	
	/**
	 * 提取商品数据
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/edit_product.html")
	public String editproduct(@RequestParam String id,HttpServletRequest request,HttpServletResponse response) {
		JSONObject obje=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 String shopid;
		 int isAdmin=obje.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid="0";
		 	}else{
			 shopid=obje.getString("shopID");
		 }
		
		JSONArray pro=productBiz.edProduct(Integer.valueOf(id));
		
		request.setAttribute("pro", pro.get(0));
		request.setAttribute("shopid", shopid);
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/editproduct";	
	}
	
	/**
	 * 提取商品属性
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("admin/show_attribute")
	public void showattr(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		QueryParam queryParam= new QueryParam();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		
		paramMap.put("proCode", request.getParameter("code"));
		typeMap.put("proCode", "eq");
		
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);

		List<ShopProAttribute> attr=(List<ShopProAttribute>) sshUtilBiz.getObjectList(ShopProAttribute.class, queryParam, null);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", attr);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	
	/**
	 * 更新商品信息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("admin/upd_product.html")
	public void updproduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
				//data1
				String name=request.getParameter("shName");
				String costPrice=request.getParameter("costPrice");
				String price=request.getParameter("shopPrice");
				String disPrice=request.getParameter("shopDisprice");
				String inventory=request.getParameter("Inventory");
				String isUse=request.getParameter("isUse");
				String isCommission=request.getParameter("isCom");
				//data2
				String cover=request.getParameter("fi");
				String proDes=request.getParameter("prodes");
				//data3
				String fatyId=request.getParameter("shoptype1");
				String typeId=request.getParameter("shoptype2");
				String brandId=request.getParameter("brandId");
				String proTag=request.getParameter("proTag");
				String procode=request.getParameter("code");
				String id=request.getParameter("id");
				String  data4=request.getParameter("data4");
				
				JSONObject bean=new JSONObject();
				bean.element("name", name);
				bean.element("costPrice", costPrice);
				bean.element("price", price);
				bean.element("disPrice", disPrice);
				bean.element("inventory", inventory);
				bean.element("isUse", isUse);
				bean.element("isCommission", isCommission);
				bean.element("cover", cover);
				bean.element("proDes", proDes);
				bean.element("typeId", typeId);
				bean.element("fatyId", fatyId);
				bean.element("brandId", brandId);
		
				bean.element("proTag", proTag);
				bean.element("id", id);
				bean.element("procode", procode);
				bean.element("data4", data4);
				
				
				
				productBiz.updateProduct(bean);	
					
					
				JSONObject msg=new JSONObject();
				msg.element("msg", "success");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/plain");
				response.getWriter().println(msg.toString());
		
	}
	
	
	/**
	 * 添加商品跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("product/url_product.html")
	public String toProductAdd(HttpServletRequest request,HttpServletResponse response) {
		JSONObject obje=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 String aid=request.getParameter("aid");
		 if(aid!=null&&!"".equals(aid)){
			 ShopProApply proa=(ShopProApply) sshUtilBiz.getObjectById(ShopProApply.class, Long.valueOf(aid));
			 String bname = null;
			if(proa.getBrandId()!=null){
				ShopProBrand pb=(ShopProBrand) sshUtilBiz.getObjectById(ShopProBrand.class, proa.getBrandId());
				bname=pb.getName();
			}
			 ShopProType pt=(ShopProType) sshUtilBiz.getObjectById(ShopProType.class, proa.getTypeId());
			 request.setAttribute("pt",pt);
			 request.setAttribute("pbname",bname);
			 request.setAttribute("proa",proa);
		 }
		 String shopid;
		 int isAdmin=obje.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid="0";
		 	}else{
			 shopid=obje.getString("shopID");
		 }
		 request.setAttribute("shopid", shopid);
		 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/addproduct";	
		
	}
	
	
	/**
	 * 批量更新库存
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("product/batch_inven.html")
	@ResponseBody
	public String batchPro(@RequestParam String data,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject obje=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 String shopid;
		 int isAdmin=obje.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid="0";
		 	}else{
			 shopid=obje.getString("shopID");
		 }
		
		BatchInven bich= (BatchInven) JsonUtil.getBean(data, BatchInven.class);
		List<String> ids = bich.getIds();
		List<String> inventorys = bich.getInventorys();
		List<String> proPrice = bich.getProPrice();
		List<String> proCodes=bich.getProCodes();
 		
		return productBiz.updatebatchin(ids, inventorys,proPrice,proCodes,shopid);
		
		
		//批量不同数据更新
		/*UPDATE shop_product
	    SET inventory = CASE id
	        WHEN 6 THEN 454
	        WHEN 7 THEN 565
	        WHEN 8 THEN 787
	        ,
	    	END
		WHERE id IN (6,7,8)*/
	
	}
	
	
	/**
	 * 批量更新上下架
	 * @param request
	 * @param response
	 */
	@RequestMapping("product/batch_isUse.html")
	@ResponseBody
	public String batchisUse(@RequestParam String ids,String isUse,String proCodes,HttpServletRequest request,HttpServletResponse response) {
		JSONObject obje=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 String shopid;
		 int isAdmin=obje.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid="0";
		 	}else{
			 shopid=obje.getString("shopID");
		 }
		
		List<String> id = JsonUtil.getList(ids);
		List<String> proCode = JsonUtil.getList(proCodes);
		
		
		return productBiz.updatebatchis(id, proCode,isUse, shopid);
	
	
		/*//批量不同数据更新
		UPDATE shop_product
	    SET inventory = CASE id
	        WHEN 6 THEN 454
	        WHEN 7 THEN 565
	        WHEN 8 THEN 787
	    	END
		WHERE id IN (6,7,8)*/
	
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("product/batch_delpro.html")
	@ResponseBody
	public String batchdelPro(@RequestParam String ids,String proCodes,HttpServletRequest request,HttpServletResponse response) {
		JSONObject obje=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 String shopid;
		 int isAdmin=obje.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid="0";
		 	}else{
			 shopid=obje.getString("shopID");
		 }
		
		List<String> id = JsonUtil.getList(ids);
		List<String> proCode = JsonUtil.getList(proCodes);
		
		return productBiz.delbatchpro(id, proCode, shopid);
		
	
		
	}
	
	/**
	 * 根据字段名更新产品信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("product/updateprobycloum")
	public void updateProByCloum(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject msg=new JSONObject();
		//0.获得传入参数&获得用户session
		Object userMsg=request.getSession().getAttribute(Dto.LOGIN_USER);
		
		//1.组织更新数据
		if(userMsg!=null){
			JSONObject obj=(JSONObject)userMsg;
			JSONObject queryParam=new JSONObject();
			queryParam.element("colName", request.getParameter("colName"));
			queryParam.element("colValue", request.getParameter("colValue"));
			if(obj.getInt("isAdmin")==1)
				queryParam.element("proCode", request.getParameter("proCode"));
			else
				queryParam.element("proID", request.getParameter("proID"));
			
			//2.执行更新
			productBiz.updateProByCloum(queryParam);
			msg.element("msg", "success");
		}else
			msg.element("msg", "身份信息过期，请刷新页面");
		
		//3.返回消息
		Dto.printMsg(response, msg.toString());
	}
}
