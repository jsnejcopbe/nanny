package com.nanny.controller.admin;

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.admin.SysGlaAreaBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysGlobalArea;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.GbkTransUtil;
import com.zhuoan.util.TimeUtil;

/**
 * 总后台营业地区设置控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class AdminGlobalAreaController {
	
	//载入资源
	@Resource(name="sysGlaAreaBiz")
	private SysGlaAreaBiz g_SysglaBiz;
	
	/**
	 * 前往地区管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"admin/area-{page:\\d*}"})
	public ModelAndView toAreaSelect(@PathVariable int page,HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//0.获得传入参数
		String province=request.getParameter("province");
		String city=request.getParameter("city");
		String areaName=request.getParameter("areaName");
		
		//1.组织查询数据
		JSONObject queryData=new JSONObject();
		if(province!=null && !("").equals(province))
			queryData.element("province", province);
		if(city!=null && !("").equals(city))
			queryData.element("city", city);
		queryData.element("addName", areaName==null?"":areaName);
		
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(page);
		pageUtil.setPageSize(10);
		
		//2.查询记录
		JSONObject data=g_SysglaBiz.getGlaArea(queryData, pageUtil);
		JSONArray provinceList=g_SysglaBiz.getSysProvince();
		JSONArray cityList=new JSONArray();
		if(province!=null && !("").equals(province))
			cityList=g_SysglaBiz.getSysCity(province);
		
		//3.返回数据
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/adminArea");
		mav.addObject("queryData", data);
		mav.addObject("provinceList", provinceList);
		mav.addObject("province", province==null?"":province);
		mav.addObject("city", city==null?"":city);
		mav.addObject("areaName", areaName==null?"":areaName);
		mav.addObject("nowPage", page);
		mav.addObject("cityList",cityList);
		return mav;
	}
	
	
	/**
	 * 添加新地图
	 * @param request
	 * @param response
	 */
	@RequestMapping("admin/addnewmap")
	public void addNewMap(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject msg=new JSONObject();
		//0.获得城市首字母
		String city=request.getParameter("city");
		String headNum=GbkTransUtil.getPinYinHeadChar(city);
		//1.组织数据
		SysGlobalArea bean=new SysGlobalArea();
		bean.setAddName(request.getParameter("name"));
		bean.setArea(request.getParameter("area"));
		bean.setCity(request.getParameter("city"));
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setDetAdd(request.getParameter("add_det"));
		bean.setLat(request.getParameter("lat"));
		bean.setLon(request.getParameter("lon"));
		bean.setProvince(request.getParameter("province"));
		bean.setMemo(headNum);
		//2.插入数据
		if(request.getParameter("nowId")==null)
		{
			Long id=g_SysglaBiz.addNewMap(bean);
			msg.element("id", id);
		}else{
			bean.setId(Long.valueOf(request.getParameter("nowId")));
			g_SysglaBiz.updateMapData(bean);
		}
		//3.返回数据
		msg.element("msg", "编辑成功");
		Dto.printMsg(response, msg.toString());
	}
	
	
}
