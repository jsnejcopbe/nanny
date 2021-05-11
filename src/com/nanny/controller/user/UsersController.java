package com.nanny.controller.user;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.nanny.biz.user.UserCollectBiz;
import com.nanny.biz.user.UserViewShopRecBiz;

import com.nanny.biz.login.LoginBiz;
import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.biz.shop.ShopIntegralDetailBiz;
import com.nanny.biz.shop.ShopProductExchangeBiz;

import com.nanny.biz.user.UsersBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserCollection;
import com.nanny.model.UserSign;


import com.nanny.model.UserReceiveAdd;
import com.zhuoan.shh.biz.SSHUtilBiz;

import com.zhuoan.util.DateUtils;

import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.TimeUtil;


/**
 * 买家个人中心
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @change wph
 * @version 0.1
 */
@Controller
public class UsersController {
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	@Resource
	private UsersBiz usersBiz;
	@Resource(name="loginBiz")
	private  LoginBiz loginBiz;
	@Resource(name="usercollectBiz")
	private UserCollectBiz userCollectBiz;
	@Resource
	private ShopAccountBiz shopAccountBiz;
	@Resource(name="userViewShopRecBiz")
	private UserViewShopRecBiz g_ViewRecBiz;
	@Resource
	private ShopIntegralDetailBiz sid;
	@Resource
	private ShopProductExchangeBiz spe;
	
	
	/**
	 * 用户(买家)登录跳转
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("users/userIndex.html")
	public String userlogjump(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 
		BaseUsers user=(BaseUsers) sshUtilBiz.getObjectById(BaseUsers.class, obj.getLong("userID"));
		 JSONArray ua=  usersBiz.doUseraddress(obj.getInt("userID"));
		 int uad=1;
		 
		 if(ua.size()>0){
			 uad=0;
		 }
		if(!("null").equals(obj.getString("shopID"))){
			response.sendRedirect("/nanny/"+Dto.LOGIN_SHOP_SEND);
			return null;
		}else{
			//页面判断有无地址
			request.setAttribute("uad", uad);
			request.setAttribute("user", user);
			int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/userIndex";
		}
	}
	
	
	/**
	 * 用户(买家)个人信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/userProfile.html")
	public String user(HttpServletRequest request,HttpServletResponse response) {
		
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		 BaseUsers profile=(BaseUsers) sshUtilBiz.getObjectById(BaseUsers.class, obj.getLong("userID"));
		
		request.setAttribute("profile", profile);
		 int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/userProfile";
	}
	
	/**
	 * 用户资料更新
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/UpdateUser.html")
	public void upduser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		
		String qq=request.getParameter("qq");
		String mail=request.getParameter("mail");
		String name=request.getParameter("nickName");
		String id=request.getParameter("id");
		String sex= request.getParameter("sex");
		String logoSrc= request.getParameter("logoSrc");
		String birthdate= request.getParameter("birthdate");
		
		JSONObject bean=new JSONObject();
		
		bean.element("qq", qq);
		bean.element("mail", mail);
		bean.element("name", name);
		bean.element("id", id);
		bean.element("sex", sex);
		bean.element("logoSrc", logoSrc);
		bean.element("birthdate", birthdate);
		 String  mesg=usersBiz.updateUsers(bean);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", mesg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	/**
	 * 收货地址展示
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/useraddress.html")
	public String useraddre(HttpServletRequest request,HttpServletResponse response) {
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		
		//用来判断mall 还是nanny
		String type=request.getParameter("type");
		
		if (type==null||type=="") {
			//存入session  1为nanny
	    	request.getSession().setAttribute(Dto.PLAT_ADDRESS,"1");
		}else{
			//存入session  0为mall
			request.getSession().setAttribute(Dto.PLAT_ADDRESS,"0");
		}
		
		
		JSONArray  address=usersBiz.doUseraddress(obj.getInt("userID"));
		
		request.setAttribute("address", address);
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/userAddress";
		
	}
	
	
	/**
	 * 购物车跳转新增用户(无拦截)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/newUserFor.html")
	public String addUseressFor(HttpServletRequest request,HttpServletResponse response) {
		String shopcar=request.getParameter("shopcar");
		
		request.setAttribute("shopcar", shopcar);
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/addUseRess";
		
		
		
	}
	
	
	
	
	/**
	 * 跳转到新增收货地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/users/addUseRess.html")
	public String addUseress(HttpServletRequest request,HttpServletResponse response) {
		String shopcar=request.getParameter("shopcar");
		if(shopcar==null||"".equals(shopcar)){
			shopcar="1";
		}
		request.setAttribute("shopcar", shopcar);
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/addUseRess";
		
		
		
	}
	
	/**
	 * 新增收货地址
	 * @param request
	 * @param response
	 */
	@RequestMapping("users/adduseress.html")
	public void address(HttpServletRequest request,HttpServletResponse response) {
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		String shop=request.getParameter("shop");
		String uid=obj.getString("userID");
		
		//String passw=request.getParameter("passw");
		String address=request.getParameter("address");
		String areaID=request.getParameter("areaID");
		String recName=request.getParameter("recName");
		String tel= request.getParameter("tel");
		String community= request.getParameter("community");
		String doorplate= request.getParameter("doorplate");
		
		JSONObject bean=new JSONObject();
		
		JSONObject msg=new JSONObject();//返回ajax
		
		bean.element("uid", uid);
		bean.element("address", address);
		bean.element("areaID", areaID);
		bean.element("recName", recName);
		bean.element("tel", tel);
		bean.element("community", community);
		bean.element("doorplate", doorplate);
	
		String mesg=usersBiz.addUseraddress(bean);
		
		
		if(!"0".equals(shop)){
			msg.element("msg", mesg);
		}else{
			msg.element("msg", 0);
			msg.element("userId", uid);
		}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		try {
			response.getWriter().println(msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 新增收货地址及新增用户(无拦截)
	 * @param request
	 * @param response
	 */
	@RequestMapping("/newuseress.html")
	public void newaddress(HttpServletRequest request,HttpServletResponse response) {
		String obj= (String) request.getSession().getAttribute(Dto.WEIXIN_USER_OPENID);
		String openid=null;
		if(obj!=null){
			openid= obj;
		}
		String shop=request.getParameter("shop");
		
		String passw=request.getParameter("passw");
		String address=request.getParameter("address");
		String areaID=request.getParameter("areaID");
		String recName=request.getParameter("recName");
		String tel= request.getParameter("tel");
		String community= request.getParameter("community");
		String doorplate= request.getParameter("doorplate");
		
		JSONObject bean=new JSONObject();
		
		JSONObject msg=new JSONObject();//返回ajax
		
		
		bean.element("address", address);
		bean.element("areaID", areaID);
		bean.element("recName", recName);
		bean.element("tel", tel);
		bean.element("community", community);
		bean.element("doorplate", doorplate);
		bean.element("passw", passw);
		bean.element("openId", openid);
		bean.element("recShopID", getRecShopID(request.getSession(), openid));
		
		
		long userId=usersBiz.douser(bean);
		bean.element("uid", userId);
		msg.element("userId", userId);
		
		
		String mesg=usersBiz.addUseraddress(bean);
		
		JSONObject oUserObj=loginBiz.getLoginUserMsg(tel, passw);
		//存入Session
		request.getSession().setAttribute(Dto.LOGIN_USER, oUserObj);
		
		if("".equals(shop)||shop==null){
			msg.element("msg", 0);
			
		}else{
			msg.element("msg", mesg);
		}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		try {
			response.getWriter().println(msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据ID 提取地址数据
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/edituseress.html")
	public String editaddress(@RequestParam long id,HttpServletRequest request,HttpServletResponse response) {
		UserReceiveAdd sga=  (UserReceiveAdd) sshUtilBiz.getObjectById(UserReceiveAdd.class, id);
		request.setAttribute("sga", sga);
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/editUseRess";
		
		
	}
	
	/**
	 * 编辑收货地址
	 * @param request
	 * @param response
	 */
	@RequestMapping("users/upduseress.html")
	public void updaddress(HttpServletRequest request,HttpServletResponse response) {
		
		String id=request.getParameter("id");
		String address=request.getParameter("address");
		String areaID=request.getParameter("areaID");
		String recName=request.getParameter("recName");
		String tel= request.getParameter("tel");
		String community= request.getParameter("community");
		String doorplate= request.getParameter("doorplate");
		
		JSONObject bean=new JSONObject();
		
		bean.element("id", id);
		bean.element("address", address);
		bean.element("areaID", areaID);
		bean.element("recName", recName);
		bean.element("tel", tel);
		bean.element("community", community);
		bean.element("doorplate", doorplate);
		String mesg=usersBiz.updateUseraddress(bean);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", mesg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		try {
			response.getWriter().println(msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除收货地址
	 * @param request
	 * @param response
	 */
	@RequestMapping("users/deluseress.html")
	public void deladdress(@RequestParam long id,HttpServletRequest request,HttpServletResponse response) {
		 String mesg= usersBiz.delUseraddress(id);
		 
			JSONObject msg=new JSONObject();
			msg.element("msg", mesg);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			try {
				response.getWriter().println(msg.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * 用户收藏
	 * @Copyright Copyright (c) 2016
	 * @Company zhouan
	 * @author LPC
	 * @version 0.1
	 * @throws IOException 
	 */	
	@RequestMapping("addcollection")
	public void addcollection(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//获得参数
		HttpSession session=request.getSession();
		JSONObject object = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
        Long userID =object.getLong("userID");
	     Long shopID =Long.valueOf(request.getParameter("shopID"));
       
 
    
		//查询用户是否收藏过	
		JSONObject check =userCollectBiz.cheUserCollectByuserID(userID,shopID);
		if(check==null){
		//收藏	
			  // 设置参数（填入表格  基础信息）
		     //1、new一张对应表格
			UserCollection bean = new UserCollection();
			 //2、填入信息
			bean.setShopId(shopID);
			bean.setUserId(userID);
			bean.setCreateTime(DateUtils.getTimestamp());		
			
			
	    String  usercolltion=userCollectBiz.addUserCollect(bean);	
	    JSONObject msg = new JSONObject();
		msg.element("msg",usercolltion);
		msg.element("success","success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());	
		} else{
		//取消收藏
		String usercolltion =userCollectBiz.delUserCollectByuserIDandshopID(userID, shopID);
		 JSONObject msg = new JSONObject();
			msg.element("msg",usercolltion);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());	
		} 
	   	  
	}	
	
	/**
	 * 用户账目列表（手机）
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("users/userAccount.html")
	public String userAccountlist(
			@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
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

		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");

		String status = request.getParameter("state");

		
		BaseUsers uar=(BaseUsers) sshUtilBiz.getObjectById(BaseUsers.class, json.getLong("userID"));
		
		JSONObject rec = shopAccountBiz.doshopaccount(
				json.getInt("userID"), status, logmin, logmax, pageUtil);

		
		request.setAttribute("uar", uar);
		
		request.setAttribute("rec", rec);
		request.setAttribute("lognmin", request.getParameter("logmin"));
		request.setAttribute("logbmax", request.getParameter("logmax"));
		
		request.setAttribute("statu", request.getParameter("state"));
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/userAccount";
	}
	
	
	@RequestMapping("users/userAccountFy.json")
	public void userAccountFy(
			@RequestParam(required = false) Integer pageIndex,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException {
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(10);
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);

		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");

		String status = request.getParameter("status");

		
		BaseUsers uar=(BaseUsers) sshUtilBiz.getObjectById(BaseUsers.class, json.getLong("userID"));
		
		JSONObject rec = shopAccountBiz.doshopaccount(
				json.getInt("userID"), status, logmin, logmax, pageUtil);
		
		JSONObject msg=new JSONObject();
		msg.element("uar", uar);
		msg.element("rec", rec);
		msg.element("logmin", request.getParameter("logmin"));
		msg.element("logmax", request.getParameter("logmin"));
		msg.element("statu",request.getParameter("state"));
		msg.element("userID", json.getInt("id"));
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("text/plain");
		 response.getWriter().println(msg);
	}
	
	
	/**
	 * 签到跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/Signjump.html")
	public String Signjump(HttpServletRequest request,HttpServletResponse response) {
		JSONObject object = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(1);
		pageUtil.setPageSize(5);
		
		 JSONArray  usign= usersBiz.dousersign(object.getInt("userID"),pageUtil);
		
		
		  String newda3= TimeUtil.getNowDate("yyyy年MM月dd日");
		 
		 String day1=DateUtils.calendared(-1, "dd");
		  String day2=DateUtils.calendared(1, "dd");;
		
		  request.setAttribute("day1", day1);
		  request.setAttribute("day2", day2);
		 request.setAttribute("newdate", newda3);
		 request.setAttribute("usign", usign);
		 
		int url = (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/userSign";	
	}
	
	
	/**
	 * 签到记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/signlist.html")
	public void signlist(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject object = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		//Integer pageIndex=Integer.valueOf(request.getParameter("pain"));
		//Integer pageSize=Integer.valueOf(request.getParameter("pasi"));
		
		
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 5;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		 JSONArray  usign= usersBiz.dousersign(object.getInt("userID"),pageUtil);

		 JSONObject msg=new JSONObject();
		
		 msg.element("usign", usign);
		 msg.element("pageIndex", pageIndex);
		 msg.element("pageSize", pageSize);
		 
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("text/plain");
		 response.getWriter().println(msg.toString());
	}
	
	
	/**
	 * 用户签到
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/updsign.html")
	public void usersign(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject object = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(1);
		pageUtil.setPageSize(5);
		
		JSONArray  usign= usersBiz.dousersign(object.getInt("userID"),pageUtil);
		//最近一次的签到时间
		String day="2";
		if(usign.size()!=0){
		 String st=usign.getJSONObject(0).getString("signTime");
		 
		 //String st1=DateUtils.formatDate(st);
		 
		 String newda= TimeUtil.getNowDate("yyyy-MM-dd");
		 //两次时间相隔
		  day=TimeUtil.getDaysBetweenTwoTime(st+" 00:00:00", newda+" 00:00:00",Long.valueOf(1000 * 60 * 60 * 24));
		}
		 JSONObject msg=new JSONObject();
		 
		 UserSign us=new UserSign();
		 
		 if(Integer.valueOf(day)>=2){
			//不是连续签到
			 us.setScore(1);//此数据会变动
			 us.setSignCount(0);
			 us.setUserId(object.getLong("userID"));
			 us.setSignTime(DateUtils.gettimestamp());
			 
			 usersBiz.updateusersign(us, object.getLong("userID"));
			 msg.element("msg", 0);
			}else if(Integer.valueOf(day)==1){
				//连续签到
				 us.setScore(1);//此数据会变动
				 us.setSignCount(usign.getJSONObject(0).getInt("signCount")+1);
				 us.setUserId(object.getLong("userID"));
				 us.setSignTime(DateUtils.gettimestamp());
				 usersBiz.updateusersign(us, object.getLong("userID"));
				 
				 msg.element("msg", 0);
				
			}else if(Integer.valueOf(day)==0){
				//重复签到
				msg.element("msg", 1);
			}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	
	/**
	 * 辅助方法 获得推荐店铺ID
	 * @param session
	 * @param openID
	 * @return
	 */
	private Long getRecShopID(HttpSession session,String openID){
		Long rtnID=null;
		if(openID!=null){
			JSONObject obj= g_ViewRecBiz.getViewRecByOpenID(openID);
			if(obj!=null && obj.containsKey("shopID") && !("null").equals(obj.getString("shopID")))
				rtnID=obj.getLong("shopID");
		}
		else if(session.getAttribute(Dto.SHOPID_OF_RECOMMEND)!=null)
			rtnID=(Long) session.getAttribute(Dto.SHOPID_OF_RECOMMEND);
		return rtnID;
	}
	
	
	/**
	 * 用户积分明细
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("users/integralDetail.html")
	public String userIntegralList(
			@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 10;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		String status=request.getParameter("status");
		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);

		
		JSONObject bean=new JSONObject();

		bean.element("flag",1);
		bean.element("userid",json.get("userID") );
		bean.element("shop_id", json.get("shopID"));
		bean.element("logmin", logmin);
		bean.element("logmax", logmax);
		bean.element("status",status);
		
	
		JSONObject obj=sid.doShopIngeralDetail(bean, pageUtil);
		
		JSONArray arr=obj.getJSONArray("shopIntgral");
		
		
		request.setAttribute("logmin",logmin );
		request.setAttribute("logmax",logmax );
		request.setAttribute("status", status);
		request.setAttribute("obj",obj);
		request.setAttribute("arr",arr);
		
		
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/userIntegralDetail";
	}
	/**
	 * 用户兑换明细
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	
	@RequestMapping("users/ProductExchangeDetail.html")
	public String userProductExchangeList(
			@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 10;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
		String memo=request.getParameter("memo");
		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		
		JSONObject bean=new JSONObject();
		bean.element("flag", 1);
		bean.element("userId",json.get("userID"));
	
		bean.element("logmin", logmin);
		bean.element("logmax", logmax);
		bean.element("memo",memo);
		bean.element("shop_id", json.get("shopID"));
		
		JSONObject obj=spe.doShopProductExchange(bean, pageUtil);
		
		JSONArray arr=obj.getJSONArray("shopProExc");
	
		request.setAttribute("obj",obj);
		request.setAttribute("arr", arr);
		request.setAttribute("logmin",logmin );
		request.setAttribute("logmax",logmax );
		request.setAttribute("memo",memo);
		
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/userProductExchangelDetail";
	}
	
	/**
	 * 手机端兑换明细翻页
	 * @param pageIndex
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/ProductExchangeDetailFy.json")
	public void ProductExchangeDetailFy(
			@RequestParam(required = false) Integer pageIndex,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PageUtil pageUtil = new PageUtil();
		
		if (pageIndex == null) {
			
			pageIndex = 1;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(10);
		
		String memo=request.getParameter("memo");
		String logmin = request.getParameter("logmin");
		String logmax = request.getParameter("logmax");
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		
		JSONObject bean=new JSONObject();
		bean.element("flag", 1);
		bean.element("userId",json.get("userID"));
		
		bean.element("logmin", logmin);
		bean.element("logmax", logmax);
		bean.element("memo",memo);
		bean.element("shop_id", json.get("shopID"));
		
		JSONObject obj=spe.doShopProductExchange(bean, pageUtil);
		
		JSONArray arr=obj.getJSONArray("shopProExc");
		
		 JSONObject msg=new JSONObject();
		 msg.element("obj", obj);
		 msg.element("arr",arr );
		 msg.element("logmin",logmin );
		 msg.element("logmax",logmax );
		 msg.element("memo",memo );
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("text/plain");
		 response.getWriter().println(msg);
		
	}
	
	
	/**
	 * 用户优惠券跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/userCoupon.html")
	public String userCoupon(HttpServletRequest request, HttpServletResponse response){
		
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(1);
		pageUtil.setPageSize(5);
		
		HttpSession session = request.getSession();
		JSONObject json = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
	
			String sta=request.getParameter("sta");
		JSONObject jso=	usersBiz.doUserCoupon(json.getInt("userID"),sta,pageUtil);
		JSONArray arr=jso.getJSONArray("arr");
		request.setAttribute("jso", jso);
		
		request.setAttribute("arr", arr);
		request.setAttribute("sta", sta);
		int url = (Integer) request.getSession().getAttribute(
				Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url) + "/userCoupon";
	}
	/**
	 * 优惠券翻页
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("users/userCouponFlip.html")
	public void userCouponFlip(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		
	
		String sta=request.getParameter("sta");
		JSONObject jso=	usersBiz.doUserCoupon(json.getInt("userID"),sta,pageUtil);
		
		JSONArray arr=jso.getJSONArray("arr");
		 JSONObject msg=new JSONObject();
		 msg.element("arr", arr);
		msg.element("jso", jso);
		 msg.element("pageIndex", pageIndex);
		 msg.element("pageSize", pageSize);
		 
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("text/plain");
		 response.getWriter().println(msg.toString());
		
	}
	

}


