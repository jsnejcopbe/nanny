package com.nanny.controller.login;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.login.LoginBiz;
import com.nanny.biz.login.RegisterBiz;
import com.nanny.biz.user.UserViewShopRecBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseUsers;
import com.zhuoan.util.CookiesUtil;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.MathDelUtil;

import net.sf.json.JSONObject;



		
		
@Controller
public class RegisterController {
 
	// 载入资源
	@Resource(name="registerBiz")
	private RegisterBiz g_RegisterBiz;
	@Resource(name="loginBiz")
	private  LoginBiz loginBiz;
	@Resource(name="userViewShopRecBiz")
	private UserViewShopRecBiz g_ViewRecBiz;
	
	/**
	 * 页面跳转
	 * 跳转到userRegister
	 * 
	 */
	@RequestMapping(value="register")
	public ModelAndView touserRegister(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		String url;
		if(session.getAttribute(Dto.USER_WEIXIN_INFO)!=null)
			url="/register-wx";
		else
			url="/register";
		
		ModelAndView re=new ModelAndView(Dto.getPagePath(type)+url);
		re.addObject("recShopID", 0);
		if(session.getAttribute(Dto.USER_WEIXIN_INFO)!=null)
			re.addObject("userInfo", session.getAttribute(Dto.USER_WEIXIN_INFO));
	    return re;
	}
	
	/**
	 * 前往注册(商家推广专属链接)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="register/{shopID:\\d*}")
	public ModelAndView toUserRegByShop(@PathVariable Long shopID,HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		String url;
		if(session.getAttribute(Dto.USER_WEIXIN_INFO)!=null)
			url="/register-wx";
		else
			url="/register";
		
		ModelAndView re=new ModelAndView(Dto.getPagePath(type)+url);
		re.addObject("recShopID", shopID);
		if(session.getAttribute(Dto.USER_WEIXIN_INFO)!=null)
			re.addObject("userInfo", session.getAttribute(Dto.USER_WEIXIN_INFO));
	    return re;
	}
	
	/**
	 * 新用户注册
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	@RequestMapping("registeract")
	public void userRegister(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		HttpSession session=request.getSession();
		
		// 获得参数（输入值）
		String tel = request.getParameter("tel");
		String password = request.getParameter("password");
		String nickName= request.getParameter("nickname");
		Long recShopID=Long.valueOf(request.getParameter("recShopID"));
//		if(session.getAttribute(Dto.SHOPID_OF_RECOMMEND)!=null && recShopID==0)
//			recShopID=(Long) session.getAttribute(Dto.SHOPID_OF_RECOMMEND);
		  String token=MathDelUtil.getRandomCode(10);
		// 设置参数（填入表格  基础信息）
		BaseUsers bean=new BaseUsers();
		
		bean.setBalance(0D);
		bean.setBirthdate(null);
		bean.setCreateTime(DateUtils.getTimestamp());
		bean.setHeadImg(request.getParameter("hedImg"));
		bean.setId(null);
		bean.setIsAdmin(0);
		bean.setMail(null);
		if(nickName==null || ("").equals(nickName))
			bean.setNickName(tel);
		else
			bean.setNickName(nickName);
		bean.setOrigin(null);
		if(session.getAttribute(Dto.WEIXIN_USER_OPENID)!=null)
			bean.setOriginId((String) session.getAttribute(Dto.WEIXIN_USER_OPENID));
		bean.setPassword(password);
		bean.setPoint(0);
		bean.setQq(null);
		bean.setRecShopId((recShopID==null||recShopID==0)?getRecShopID(session, bean.getOriginId()):recShopID);
		bean.setSex(null);
		bean.setTel(tel);
		bean.setToken(token);
		
		    //查询用户
		JSONObject oUserObj=loginBiz.getLoginUserMsg(tel);
		if(oUserObj==null) {
			// 添加新用户
			JSONObject oUserMsg = g_RegisterBiz.addNewUser(bean);
			oUserMsg.element("shopID", "null");
			
			//存入cookies
	    	CookiesUtil.addCookie(Dto.USER_TOKEN, token, response);
			
			request.getSession().setAttribute(Dto.REGISTER_USER, oUserMsg);
			request.getSession().setAttribute(Dto.LOGIN_USER, oUserMsg);
			// 返回
			JSONObject msg = new JSONObject();
			msg.element("msg", "注册成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
			
	    }else {
	    	
	    	JSONObject msg = new JSONObject();
	    	msg.element("msg", "注册失败，该账号已被注册");
	    	response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
			
			}
		 
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
			else
				rtnID=null;
		}
		else if(session.getAttribute(Dto.SHOPID_OF_RECOMMEND)!=null)
			rtnID=(Long) session.getAttribute(Dto.SHOPID_OF_RECOMMEND);
		return rtnID;
	}
}
