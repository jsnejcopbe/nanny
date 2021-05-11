package com.nanny.controller.shop;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.shop.ShopSupplierBiz;
import com.nanny.biz.supplier.SupplierProductBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;

@Controller
public class ShoSupplierController {
	
	@Resource
	private ShopSupplierBiz ssb;
	
	@Resource
	private SSHUtilBiz ssgb;
	
	
	@Resource
	private SupplierProductBiz spb;
	
	/**
	 * 供应商列表（商户后台）
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("shop/shopsup.html")
	public String supplierList(@RequestParam(required = false) Integer pageIndex,@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response){
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 10;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String name=request.getParameter("name");
		
		BaseShop bs=(BaseShop) ssgb.getObjectById(BaseShop.class, json.getLong("shopID"));
		
		
		JSONObject bean=new JSONObject();
		bean.element("adressID", bs.getAdressId());
		bean.element("sname", name);
		
		
		JSONObject suplist=ssb.getshopsup(bean, pageUtil);
		
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("suplist", suplist);
		
		int url = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url) + "/shopSupplier";
		
	}
	
	
	
	/**
	 * 供应商-商品列表（商户后台）
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("shop/shsupro.html")
	public String supprolist(@RequestParam(required = false) Integer pageIndex,@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) {
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 15;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		String suid=request.getParameter("suid");
		String name=request.getParameter("pname");
		String typeid1=request.getParameter("typeid1");
		String typeid2=request.getParameter("typeid2");
		String brid=request.getParameter("brid");
		
		
		
		JSONObject bean=new JSONObject();
		bean.element("suID", suid);
		bean.element("name", name);
		bean.element("typeid1", typeid1);
		bean.element("typeid2", typeid2);
		bean.element("brid", brid);
		
		
		JSONObject obj=spb.dosupro(bean, pageUtil);
		
		request.setAttribute("obj", obj);
		
		
		
		 request.setAttribute("objpro", obj);
		 request.setAttribute("type1", request.getParameter("typeid1"));
		 request.setAttribute("type2", request.getParameter("typeid2"));
		 request.setAttribute("brID", request.getParameter("brandID"));
		 request.setAttribute("pname", request.getParameter("pname"));
		 request.setAttribute("suid", request.getParameter("suid"));
		 request.setAttribute("jsarry",  request.getParameter("checkarry"));
			System.out.println(request.getParameter("checkarry"));
		 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/shopSupPro";
	
	}
	
	
	/**
	 * 商户向供应商下单
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/supNextorder.html")
	public void shopsupor(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String supid=request.getParameter("supid");
		String total=request.getParameter("total");
		String arrydate=request.getParameter("data");
		
		
		JSONObject bean=new JSONObject();
		bean.element("arrydate", arrydate);
		bean.element("total", total);
		bean.element("supid", supid);
		bean.element("shopid", json.getLong("shopID"));
		
		String obj=ssb.doshnextro(bean);
		
		
		JSONObject msg=new JSONObject();
		msg.element("msg", obj);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
		
	}
	
	
	
	
}
