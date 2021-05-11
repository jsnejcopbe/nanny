package com.nanny.controller.shop;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.nanny.biz.product.ProductBiz;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProApply;
import com.nanny.model.ShopProduct;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

/**
 * 商户商品设置控制层
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class ShopProductController {
	
	//载入资源
	@Resource
	private ProductBiz g_ProBiz;
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	
	/**
	 * 手机端商品上下架
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("shop/proupandremove")
	public void productUpAndRemove(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得参数
		JSONArray array=JSONArray.fromObject(request.getParameter("jsonData"));
		String type=request.getParameter("proSta");
		
		//1.组织数据
		List<String> IDList=new ArrayList<String>();
		for(int i=0;i<array.size();i++)
		{
			JSONObject tmpObj=array.getJSONObject(i);
			IDList.add(tmpObj.getString("id"));
		}
		
		//2.更新数据
		if(("up").equals(type))
			g_ProBiz.updatebatchis(IDList, null, String.valueOf((Dto.IS_SALE)), "1");
		else
			g_ProBiz.updatebatchis(IDList, null, "1", "1");
		
		//3.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "修改成功");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 前往价格编辑
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("shop/price_edit")
	public ModelAndView toProPriceEdit(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得参数
		JSONArray array=JSONArray.fromObject(request.getParameter("jsonData"));
		
		//1.前往页面
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopPriceEdit");
		mav.addObject("proList", array);
		return mav;
	}
	
	/**********************************************************************新增
	 * @throws ParseException *********************************************************/
	
	/**
	 * 每日新商品
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("shop/everydaypro")
	public String daynewpro(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 10;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		JSONObject json=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String typeid1=request.getParameter("typeid1");
		String typeid2=request.getParameter("typeid2");
		String brandID=request.getParameter("brandID");
		String more=request.getParameter("more");
		
		String days=request.getParameter("days");
		if(days==null||"null".equals(days)){
			days="0";
		}
		JSONObject bean=new JSONObject();
		bean.element("type1", typeid1);
		bean.element("type2", typeid2);
		bean.element("brand", brandID);
		bean.element("more", more);
		bean.element("shopid", json.getString("shopID"));
		bean.element("days", days);
		 JSONObject pro=g_ProBiz.dodaynewpro(bean,pageUtil);
		 
		 
		    String  nowtime =TimeUtil.addDaysBaseOnNowTime(TimeUtil.getNowDate(), -Integer.valueOf(days), "yyyy-MM-dd");
			request.setAttribute("pro", pro);
			request.setAttribute("type1", request.getParameter("typeid1"));
			request.setAttribute("type2", request.getParameter("typeid2"));
			request.setAttribute("brID", request.getParameter("brandID"));
			request.setAttribute("more", request.getParameter("more"));
			request.setAttribute("days", days);
			request.setAttribute("nowtime", nowtime);
			
		 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/shopeverday";
		
	}
	
}
