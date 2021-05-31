package com.nanny.controller.global;

import java.io.IOException;
import java.text.DecimalFormat;

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

import com.nanny.biz.global.store.ShopDiscussBiz;
import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.global.store.ShopNoticeBiz;
import com.nanny.biz.global.store.ShopProDataBiz;
import com.nanny.biz.global.store.ShopProTypeBiz;
import com.nanny.biz.user.UserShoppingCarBiz;
import com.nanny.biz.user.UserViewShopRecBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 店铺商品展示控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class GlobalShopController {

	//载入资源
	@Resource(name="shopProTypeBiz")
	private ShopProTypeBiz g_ProTypeBiz;
	@Resource(name="shopProDataBiz")
	private ShopProDataBiz g_ProDataBiz;
	@Resource(name="userShoppingCarBiz")
	private UserShoppingCarBiz g_CarBiz;
	@Resource(name="shopNoticeBiz")
	private ShopNoticeBiz g_ShopNoticeBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopMsgBiz;
	@Resource(name="shopDiscussBiz")
	private ShopDiscussBiz g_ShopDisBiz;
	@Resource(name="userViewShopRecBiz")
	private UserViewShopRecBiz g_ViewRecBiz;
	
	private static final int defaultPageSize = 10;
	
	@RequestMapping("intro")
	public String jumpToIntro(HttpServletRequest request,HttpServletResponse response){
		return "intro/index.jsp";
	}
	
	/**
	 * 跳转至欢迎页
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("home")
	public String jumpToHome(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		if(type==0){
			response.sendRedirect("/nanny/index.html");
			return null;
		}
		else
			return Dto.getPagePath(type)+"/home";
	}
	
	/**
	 * 商户页面选择
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("supermarket")
	public void shopSelect(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		Long addID=(Long) session.getAttribute(Dto.SESSION_USER_AREA);//当前地址ID
		String openID=(String) session.getAttribute(Dto.WEIXIN_USER_OPENID);//当前用户openID
		//2.查到地址后，根据地址选择店铺
		if(addID==null)
			response.sendRedirect("/nanny/global_city.html");
		else{
			JSONArray shopList=g_ShopMsgBiz.getShopIDListByUserAdd(addID);
			if(shopList.size()==0){
				response.sendRedirect("/nanny/work/mobile/failure.jsp");
			}
			else if(("null").equals(shopList.getJSONObject(0).getString("id"))){
				request.getSession().setAttribute("failInfo", shopList.getJSONObject(0).getString("addName"));
				response.sendRedirect("/nanny/work/mobile/failure.jsp");
			}
			else{
				Object userMsg=session.getAttribute(Dto.LOGIN_USER);
				Long shopID;
				if(userMsg==null)
					shopID=getShopID(shopList,(long) 0,openID);
				else
				{
					JSONObject obj=(JSONObject)userMsg;
					if(!obj.containsKey("recShopID") || ("null").equals(obj.getString("recShopID")))
						shopID=getShopID(shopList,(long) 0,openID);
					else
						shopID=getShopID(shopList,obj.getLong("recShopID"),openID);
				}
				response.sendRedirect("/nanny/store-"+shopID+".html");
			}
		}
	}
	
	/**
	 * 前往指定的商铺页面
	 * @param shopID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"store-{shopID:\\d*}"})
	public ModelAndView toStorePage(@PathVariable Long shopID,HttpServletRequest request,HttpServletResponse response)
	{
		//0.初始化&获得传入参数
		JSONArray childClassList=new JSONArray();
		JSONArray proData=new JSONArray();
		String firstClassName="";
		JSONArray proCarList=new JSONArray();
		String strProData=request.getParameter("proCarList");//浏览记录中的购物车信息
		String proParID=request.getParameter("proParID");//商品详情页传来的商品一级类目ID
		
		HttpSession session=request.getSession();
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//1.获得平台所有商品的一级分类
		JSONArray firstClassList=g_ProTypeBiz.getFirstClassList((long)0);
		
		//2.获得首个一级分类下的二级分类
		Long firstClassID=Long.valueOf(0);
		
		PageUtil pageUtil = new PageUtil();
		pageUtil.setPageIndex(1);
		pageUtil.setPageSize(defaultPageSize);
		
		//3.获得商铺可兑换商品
		proData=g_ProDataBiz.getProlistIsex(shopID,pageUtil);
		
		//4.情景判断 
		if(proParID!=null)
			firstClassID=Long.valueOf(proParID);
		else if(firstClassList.size()>0 && proData.getJSONObject(0).getJSONArray("data").size()==0)
			firstClassID=firstClassList.getJSONObject(0).getLong("id");
		else
			firstClassID=(long)0;
		
		//5.获得当前选中的一级分类
		if(firstClassID!=0)
		{
			//获得二级分类与集合下的所有商品
			childClassList=g_ProTypeBiz.getChildClassByID(firstClassID);
			proData=g_ProDataBiz.getProListByClass(childClassList, firstClassID, shopID,pageUtil);
			firstClassName=getNowClassName(firstClassList, firstClassID);
		}else{
			firstClassName="";
		}
			
		//6.查询用户购物车中的购物记录
		if(strProData==null && session.getAttribute(Dto.LOGIN_USER)!=null)
		{
			JSONObject userMsg=(JSONObject)session.getAttribute(Dto.LOGIN_USER);
			proCarList=g_CarBiz.getCarProListByID(userMsg.getLong("userID"), shopID);
			strProData=proCarList.toString();
		}
		else if(strProData==null && session.getAttribute(Dto.LOGIN_USER)==null)
			strProData=proCarList.toString();
		
		//7.获得店铺公告
		JSONObject notice=g_ShopNoticeBiz.getLastNoticeByID(shopID);
		
		//8.获得店铺信息
		JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(shopID);
		//9获取轮波
		JSONArray wheel=g_ShopMsgBiz.getwheel(shopID);
		//9.跳转
		session.setAttribute(Dto.SHOPID_OF_RECOMMEND, shopID);
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/superMarket");
		mav.addObject("firstClassList", firstClassList);
		mav.addObject("firstClassID", firstClassID);
		mav.addObject("proData", proData);
		mav.addObject("firstClassName", firstClassName);
		mav.addObject("proCarList", strProData);
		mav.addObject("shopID", shopID);
		mav.addObject("notice", notice);
		mav.addObject("shopMsg", shopMsg);
		mav.addObject("methodName", "storepage");
		mav.addObject("wheel", wheel);
		return mav;
	}
	
	/**
	 * 查询某一大类下的商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("shopproajax")
	public void getShopPro(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONArray proData=new JSONArray();
		//0.获得传入参数
		Long classID=Long.valueOf(request.getParameter("classID"));
		Long shopID=Long.valueOf(request.getParameter("shopID"));
		PageUtil pageUtil = null;
		//分页信息
		String pageIndex=request.getParameter("pageIndex");
		if(pageIndex != null){
			
			pageUtil = new PageUtil();
			pageUtil.setPageIndex(Long.valueOf(pageIndex).intValue());

			String pageSize=request.getParameter("pageSize");
			if(pageSize == null)
				pageUtil.setPageSize(defaultPageSize);
			else
				pageUtil.setPageSize(Long.valueOf(pageSize).intValue());
		}
		
		if(classID!=0)
		{
			//1.查询二级分类集合
			JSONArray childClassList=g_ProTypeBiz.getChildClassByID(classID);
			//2.查询二级分类下的所有商品合集
			proData=g_ProDataBiz.getProListByClass(childClassList, classID, shopID,pageUtil);
			
			//3.返回数据
		}else
			proData=g_ProDataBiz.getProlistIsex(shopID,pageUtil);
		Dto.printMsg(response, proData.toString());
	}
	
	/**
	 * 前往店铺详情页
	 * @param shopID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"store-det-{shopID:\\d*}"})
	public ModelAndView toShopMsgPage(@PathVariable Long shopID,HttpServletRequest request,HttpServletResponse response)
	{
		JSONArray proCarList=new JSONArray();
		HttpSession session=request.getSession();
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//0.获得商户基础信息
		JSONObject shopMsg=g_ShopMsgBiz.getShopDetail(shopID);
		DecimalFormat df = new DecimalFormat("0.0");
		Double avgScore=g_ShopDisBiz.getShopScoreAvg(shopID);//店铺平均分
		//1.获得营业时间
		JSONArray timeList=g_ShopMsgBiz.getShopOfficeHours(shopID);
		//2.获得商户电话
		String[] telList=(shopMsg.getString("memo")).split(",");
		shopMsg.element("telList", telList);
		if(session.getAttribute(Dto.LOGIN_USER)!=null)
		{
			//3.获得用户在该商店的购物车信息
			JSONObject userMsg=(JSONObject)session.getAttribute(Dto.LOGIN_USER);
			proCarList=g_CarBiz.getCarProListByID(userMsg.getLong("userID"), shopID);
		}
		
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopViewMsg");
		mav.addObject("shopMsg", shopMsg);
		mav.addObject("timeList", timeList);
		mav.addObject("proCarList", proCarList);
		mav.addObject("shopID", shopID);
		mav.addObject("avgScore", df.format(avgScore));
		mav.addObject("methodName", "storepage");
		return mav;
	}
	
	/**
	 * 前往图片放大页面
	 * @param shopID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"store-photo-{shopID:\\d*}"})
	public ModelAndView toShopPhoto(@PathVariable Long shopID,HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);	
		
		// 获得商户基础信息
		JSONObject shopMsg=g_ShopMsgBiz.getShopDetail(shopID);
		
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopViewPhoto");
		mav.addObject("shopMsg", shopMsg);
		mav.addObject("shopID", shopID);
		
		return mav;
	}
	
	/**
	 * 获得用户附近的商户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("shoplist")
	public ModelAndView getNearbyShopList(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		HttpSession session=request.getSession();
		Long addID=(Long) session.getAttribute(Dto.SESSION_USER_AREA);//当前地址ID
		String page=request.getParameter("page");
		String type=request.getParameter("type");
		int path = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		if(addID==null){
			response.sendRedirect("/nanny/global_city.html");
			return null;
		}else{
			//1.组织查询数据
			PageUtil pageUtil=new PageUtil();
			pageUtil.setPageIndex(page==null?1:Integer.valueOf(page));
			pageUtil.setPageSize(20);
			JSONArray shopList=g_ShopMsgBiz.getShopDetListByUserAdd(addID, pageUtil);
			
			//2.页面跳转
			if(type==null)
			{
				ModelAndView mav=new ModelAndView(Dto.getPagePath(path)+"/shopList");
				mav.addObject("shopList", shopList);
				mav.addObject("methodName", "storepage");
				return mav;
			}else{
				Dto.printMsg(response, shopList.toString());
				return null;
			}
		}
	}
	
	/**
	 * 前往店铺评论页
	 * @param shopID
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value={"store-dis-{shopID:\\d*}"})
	public ModelAndView toShopDisPage(@PathVariable Long shopID,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		String sendType=request.getParameter("type");
		
		//0.获得传入参数
		String page=request.getParameter("page");
		
		//1.组织分页数据
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(Integer.valueOf(page==null?"1":page));
		pageUtil.setPageSize(20);
		
		//2.查询评论数据
		JSONObject disData=g_ShopDisBiz.getShopDiscuss(pageUtil, shopID);
		
		//3.返回页面
		if(sendType==null)
		{
			ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopDisMsg");
			mav.addObject("disData", disData);
			mav.addObject("methodName", "storepage");
			mav.addObject("shopID", shopID);
			return mav;
		}else{
			Dto.printMsg(response, disData.toString());
			return null;
		}
	}
/***********************************************************************************************************************************************/	
	
	/**
	 * 获得商铺地址
	 * @param array
	 * @param recShopID
	 * @return
	 */
	private Long getShopID(JSONArray array,Long recShopID,String openID)
	{
		Long shopID=null;
		if(recShopID==0 && openID!=null)
		{
			JSONObject obj=g_ViewRecBiz.getViewRecByOpenID(openID);
			if(obj!=null && !("null").equals(obj.getString("shopID")))
				recShopID=obj.getLong("shopID");
		}
		
		for(int i=0;i<array.size();i++)
		{
			JSONObject tmpObj=array.getJSONObject(i);
			if(recShopID==tmpObj.getLong("id"))
			{
				shopID=recShopID;
				break;
			}
		}
		
		if(shopID==null)
			shopID=array.getJSONObject(0).getLong("id");
		return shopID;
	}
	
	/**
	 * 遍历集合获得当前大类的大类名
	 * @param classList
	 * @param nowClassID
	 * @return
	 */
	private String getNowClassName(JSONArray classList,Long nowClassID)
	{
		String rtnStr="";
		for(int i=0;i<classList.size();i++)
		{
			JSONObject tmpObj=classList.getJSONObject(i);
			if(tmpObj.getLong("id")==nowClassID)
			{
				rtnStr=tmpObj.getString("name");
				break;
			}
		}
		return rtnStr;
	}
}
