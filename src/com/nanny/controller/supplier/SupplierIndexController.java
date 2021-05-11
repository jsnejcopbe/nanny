package com.nanny.controller.supplier;

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

import com.nanny.biz.supplier.SupplierIndexBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SupplierOrder;
import com.nanny.model.SupplierProduct;
import com.nanny.model.SupplierShopTouch;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.QueryParam;

@Controller
public class SupplierIndexController {
	
	@Resource
	private SSHUtilBiz ssb;
	
	@Resource
	private SupplierIndexBiz sib;
	
	@RequestMapping("supplier/supplierIndex.html")
	public String supindex(HttpServletRequest request,HttpServletResponse response) {
		
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_SUPPLIER);
		
		QueryParam queryParam1 = new QueryParam();
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		Map<String, String> typeMap1 = new HashMap<String, String>();
		Map<String, String> orderMap1 = new HashMap<String, String>();
		
		
		paramMap1.put("recSupplierId", json.getLong("supplierID"));
		typeMap1.put("recSupplierId", "eq");
		
		queryParam1.setParamMap(paramMap1);
		queryParam1.setTypeMap(typeMap1);
		queryParam1.setOrderMap(orderMap1);
		
		//订单总数
		String colName="id";
		int order=ssb.getObjectCount(SupplierOrder.class, colName, queryParam1);
		
		paramMap1.clear();
		typeMap1.clear();
		paramMap1.put("supplierId", json.getLong("supplierID"));
		typeMap1.put("supplierId", "eq");
		
		queryParam1.setParamMap(paramMap1);
		queryParam1.setTypeMap(typeMap1);
		
		//来往商户数
		int shop=ssb.getObjectCount(SupplierShopTouch.class, colName, queryParam1);
		
		//商品数
		int product= ssb.getObjectCount(SupplierProduct.class, colName, queryParam1);
		
		
		 JSONArray orlist=JSONArray.fromObject(sib.inorder(json.getInt("supplierID")))  ;
		
		 	request.setAttribute("shop", shop);
		
		 	request.setAttribute("order", order);
		
			request.setAttribute("product", product);
		
			request.setAttribute("orlist", orlist);
		
		 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/supplierIndex";
	}
}
