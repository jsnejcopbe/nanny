package com.nanny.controller.product;

import java.io.IOException;
import java.sql.Timestamp;
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

import com.nanny.biz.product.ProBrandBiz;
import com.nanny.biz.product.ProTypeBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;
import com.nanny.model.ShopProBrand;
import com.nanny.model.ShopProType;

import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;



/**
 * 商品品牌管理
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class ProTypeController {
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ProTypeBiz proTypeBiz;

	/**
	 * 商品分类列表
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @return
	 */
	
	@RequestMapping("admin/protype.html")
	public String brandlist(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request){
		/*PageUtil pageUtil = new PageUtil();
		
		if(pageIndex==null){
			
			pageIndex = 1;
		}
		
		if(pageSize==null){
			
			pageSize = 10;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		*/
		
		QueryParam queryParam = new QueryParam();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		
		
		
		
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);
		
	
		
		JSONArray typelist=JSONArray.fromObject(sshUtilBiz.getObjectList(ShopProType.class, queryParam, null));
		
		JSONArray ulist = new JSONArray();
		JSONArray clist = new JSONArray();
		
		
		for(int i=0;i<typelist.size();i++){
			JSONObject bean=typelist.getJSONObject(i);
		
			if(bean.getInt("parId")==0){
				ulist.add(bean);
			}else{
				clist.add(bean);
			}
			
		}
		
		
		request.setAttribute("ulist", ulist);
		request.setAttribute("clist", clist);

		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/proType";
	}
	
	/**
	 * 商品品牌更新
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updtype.html")
	public void updbrand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		
		
		JSONObject bean=new JSONObject();
		bean.element("id", id);
		bean.element("name", name);
	
		
		proTypeBiz.updProType(bean);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 商品分类添加
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("admin/addtype.html")
	public void addProbrand(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		/*BaseUsers user=(BaseUsers) request.getSession().getAttribute(Dto.LOGIN_USER);
		//通过userID 查询 shopID
		QueryParam queryParam = new QueryParam();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();

		long uid=1;
		paramMap.put("userId", uid);
		typeMap.put("userId", "eq");
		
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
			
		List<BaseShop> shop=(List<BaseShop>) sshUtilBiz.getObjectList(BaseShop.class, queryParam, null);
		*/
		 JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		 long shopid;
		 
		 int isAdmin=obj.getInt("isAdmin");
		 if(isAdmin==Dto.IS_ADMIN){
			  shopid=0L;
		 	}else{
			 shopid=obj.getLong("shopID");
		 }
		
		//获取页面数据
		String name=request.getParameter("name");
		String pid=request.getParameter("pid");
		String id=request.getParameter("id");
		
		//组织保存
		ShopProType bean=new ShopProType();
		if(!"".equals(pid)&&pid!=null&&pid!=null){
			bean.setParId(Long.valueOf(pid));
		}else{
			bean.setParId(0L);
		}
		bean.setName(name);
		bean.setCreateTime(DateUtils.getTimestamp());
		bean.setShopId(shopid);
		sshUtilBiz.saveObject(bean);
		
		//返回页面消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 删除品牌
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/deltype.html")
	public void delProject(@RequestParam long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		ShopProType bean=new ShopProType();
		bean.setId(id);
		sshUtilBiz.deleteObject(bean);
		
		
		JSONObject msg=new JSONObject();
	 	msg.element("msg", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
 }

