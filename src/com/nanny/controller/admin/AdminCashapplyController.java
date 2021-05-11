package com.nanny.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.admin.CashapplyBiz;
import com.nanny.dto.Dto;


import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.TimeUtil;

/**
 * 登陆控制层
 * 
 * @author lpc
 * 
 */
@Controller
public class AdminCashapplyController {

	// 资源载入
	@Resource(name = "CashapplyBiz")
	private CashapplyBiz cashapplyBiz;



	/**
	 * 查询商户申请列表操作
	 * 
	 * @param request
	 * @param reponse
	 * @throws ioexception
	 */
	@RequestMapping("cashapply")
	public ModelAndView getAdminCashApplyByuserID(@RequestParam(required = false) Integer pageIndex,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		JSONObject data1 =new JSONObject();
		JSONArray data2 =new JSONArray();
		JSONArray data =new JSONArray();
		ModelAndView mav = new ModelAndView(Dto.getPagePath(type)+ "/adminCashapply");
		// 1.获得传入参数		
		String status =request.getParameter("state");
		String tel =request.getParameter("tel"); 
		String logmin =request.getParameter("logmin");
		String logmax = request.getParameter("logmax");
		int page = 0;
		if (pageIndex == null) {
			page = 1;
		} else {
			page = pageIndex;
		};
		//1.组织查询数据 
		JSONObject condition=new JSONObject();
		if(!("3").equals(status)){if(!("").equals(status))condition.element("status", status);}
			
		if(!("").equals(tel)) condition.element("tel", tel);
		if(!("").equals(logmax)) condition.element("logmax", logmax);
		if(!("").equals(logmin)) condition.element("logmin", logmin);
			// 组织分页数据
			PageUtil pageUtil = new PageUtil();
			pageUtil.setPageIndex(page);
			pageUtil.setPageSize(5);

		
		//查询
     	if(condition.size()>0){
     		 data1 = cashapplyBiz.getAdminCashApplyByCondition(condition,pageUtil);
     		 data =data1.getJSONArray("data");
     		 data = TimeUtil.transTimestamp(data, "createTime");
            
			    int sdas =data1.getInt("count");
				request.setAttribute("pageSize",5);
	    		request.setAttribute("pageCount",sdas);
	    		request.setAttribute("pageIndex",page);
	    		mav.addObject("list", data);
		}
		else{			
				data2 = cashapplyBiz.getAdminCashApply(pageUtil);
	    		data2 = TimeUtil.transTimestamp(data2, "createTime");
	    		//查询当前页面数
	       		int sdas =cashapplyBiz.getAdminCashApplycount(pageUtil);
	    		request.setAttribute("pageSize",5);
	    		request.setAttribute("pageCount",sdas);
	    		request.setAttribute("pageIndex",page);
	    		mav.addObject("list", data2);
	    	
		}
     	request.setAttribute("statu", request.getParameter("state"));
     	request.setAttribute("lognmin", request.getParameter("logmin"));
		request.setAttribute("logbmax", request.getParameter("logmax"));
		request.setAttribute("tel", request.getParameter("tel"));


		
		return mav;
   
		
	
	}

}
