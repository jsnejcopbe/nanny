package com.nanny.controller.supplier;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanny.biz.shop.ShopProApplyBiz;
import com.nanny.biz.supplier.SupplierProductBiz;
import com.nanny.controller.product.bean.BatchInven;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProApply;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.DateUtils;

/**
 * 供应商商品设置控制层
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
@Controller
public class SupplierProductController {
	
		//载入资源
		@Resource
		private SupplierProductBiz spb;
		
		@Resource
		private ShopProApplyBiz shopProApplyBiz;
		
		/**
		 * 供应商品
		 * @param pageIndex
		 * @param pageSize
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("supplier/suproduct.html")
		public String spprolist(@RequestParam(required = false) Integer pageIndex,
				@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
			PageUtil pageUtil = new PageUtil();
			
			if(pageIndex==null){
				
				pageIndex = 1;
			}
			
			if(pageSize==null){
				
				pageSize = 15;
			}
			
			pageUtil.setPageIndex(pageIndex);
			pageUtil.setPageSize(pageSize);
			
			HttpSession session = request.getSession();
			JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_SUPPLIER);
			
			String name=request.getParameter("pname");
			String typeid1=request.getParameter("typeid1");
			String typeid2=request.getParameter("typeid2");
			String brid=request.getParameter("brid");
			
		
			
			
			JSONObject bean=new JSONObject();
			
			bean.element("name", name);
			bean.element("typeid1", typeid1);
			bean.element("typeid2", typeid2);
			bean.element("brid", brid);
			bean.element("suID", json.getLong("supplierID"));
			
			 JSONObject obj=spb.dosupro(bean, pageUtil);
			 
			 
			 request.setAttribute("objpro", obj);
			 request.setAttribute("type1", request.getParameter("typeid1"));
				request.setAttribute("type2", request.getParameter("typeid2"));
				request.setAttribute("brID", request.getParameter("brandID"));
				request.setAttribute("pname", request.getParameter("pname"));
			 
			 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/supProduct";
		}
		
		
		/**
		 * 选择铺货商品
		 * @param pageIndex
		 * @param pageSize
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("supplier/supshoplist.html")
		public String getshoplist(@RequestParam(required = false) Integer pageIndex,
				@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
			PageUtil pageUtil = new PageUtil();
			
			if(pageIndex==null){
				
				pageIndex = 1;
			}
			
			if(pageSize==null){
				
				pageSize = 15;
			}
			
			pageUtil.setPageIndex(pageIndex);
			pageUtil.setPageSize(pageSize);
			
			HttpSession session = request.getSession();
			JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_SUPPLIER);
			
			String name=request.getParameter("pname");
			String typeid1=request.getParameter("typeid1");
			String typeid2=request.getParameter("typeid2");
			String brid=request.getParameter("brid");
			
		
			
			
			JSONObject bean=new JSONObject();
			
			bean.element("name", name);
			bean.element("typeid1", typeid1);
			bean.element("typeid2", typeid2);
			bean.element("brid", brid);
			bean.element("suID", json.getLong("supplierID"));
			
			 JSONObject obj=spb.getshoplist(bean, pageUtil);
			 
			 
			 request.setAttribute("objpro", obj);
			 request.setAttribute("type1", request.getParameter("typeid1"));
				request.setAttribute("type2", request.getParameter("typeid2"));
				request.setAttribute("brID", request.getParameter("brandID"));
				request.setAttribute("pname", request.getParameter("pname"));
			 
			 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/supgoods";
			 
		}
		
		
		
		/**
		 * 铺货
		 * @param ids
		 * @param request
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping("supplier/supgoods.html")
		public void start_goods(String ids,HttpServletRequest request,HttpServletResponse response) throws IOException{
			List<String> list = JsonUtil.getList(ids);
			JSONObject json = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_SUPPLIER);
			String dd=	spb.start_supgoods(list, json.getInt("supplierID"));
					JSONObject msg=new JSONObject();
			msg.element("msg", dd);
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
		@RequestMapping("supplier/addproApply.html")
		public String jump_addpro(HttpServletRequest request,HttpServletResponse response) {
			
			
			request.setAttribute("addtype", "1");
			int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			
			return Dto.getPagePath(url)+"/addproApply";
			
		}
		
		
		/**
		 * 商户商品添加申请（pc）
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("supplier/addpcApply.html")
		public void addpcApply(HttpServletRequest request, HttpServletResponse response) throws IOException {
			HttpSession session =request.getSession();
			JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_SUPPLIER);
			
			
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
			spa.setMemo("1");
			spa.setStatus(0);
			String mag=shopProApplyBiz.addProApply(spa);
			
			
			JSONObject msg=new JSONObject();
			msg.element("msg", mag);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		}
		
		
		
		
		/**
		 * 批量更新库存
		 * @param request
		 * @param response
		 * @throws IOException 
		 */
		@RequestMapping("supplier/batch_inven.html")
		@ResponseBody
		public String batchPro(@RequestParam String ids,String proPrice,HttpServletRequest request,HttpServletResponse response) throws IOException {
			
		
			List<String> Price = JsonUtil.getList(proPrice);
			List<String> id = JsonUtil.getList(ids);
	 		
			return spb.updatebatchin(id,Price);
			
			
			//批量不同数据更新
			/*UPDATE shop_product
		    SET inventory = CASE id
		        WHEN 6 THEN 454
		        WHEN 7 THEN 565
		        WHEN 8 THEN 787
		        
		    	END
			WHERE id IN (6,7,8)*/
		
		}
		
		
		/**
		 * 批量更新上下架
		 * @param request
		 * @param response
		 */
		@RequestMapping("supplier/batch_isUse.html")
		@ResponseBody
		public String batchisUse(@RequestParam String ids,String isUse,HttpServletRequest request,HttpServletResponse response) {
			
			
			List<String> id = JsonUtil.getList(ids);
			
			
			
			return spb.updatebatchis(id,isUse);
		
		
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
		@RequestMapping("supplier/batch_delpro.html")
		@ResponseBody
		public String batchdelPro(@RequestParam String ids,HttpServletRequest request,HttpServletResponse response) {
			
			
			List<String> id = JsonUtil.getList(ids);
	
			
			return spb.delbatchpro(id);
			
		
			
		}
		
		
		
}
