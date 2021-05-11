package com.nanny.controller.login;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.login.LoginBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseSaleman;
import com.nanny.model.BaseSupplier;
import com.zhuoan.util.CookiesUtil;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.VerifyCodeUtils;

/**
 *  登陆控制层
 * @author lpc
 *
 */
@Controller
public class LoginController{
	
	//加载资源
	@Resource(name="loginBiz")
	private  LoginBiz loginBiz;
   
	/** 业务员登录跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("salesman")
	public String jump_salesman(HttpServletRequest request){
		HttpSession session = request.getSession();
		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(plat_type)+"/salesman_login";
	}
	
	
	@RequestMapping("salesman/login")
	@ResponseBody
	public String salesman_login(BaseSaleman bs,String verifycode,HttpServletRequest request){
		System.out.println("into next step");
		Object truecode = request.getSession().getAttribute(Dto.VERTIFY_CODE);
		if(truecode == null)if(bs == null)return Dto.REDIRECT+"salesman.html";
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1000*30);
		
		String openId=(String) session.getAttribute(Dto.WEIXIN_USER_OPENID);
		
		System.out.println("into next step2\n");
		
		String backMsg=loginBiz.getSalesManMsg(bs,verifycode,truecode==null?null:truecode.toString(),session);
		
		if(openId!=null && (Dto.SUCCEED).equals(backMsg)){
			if(bs.getId()==null){
				System.out.println("into next step3 backMsg "+backMsg+"\n");
				BaseSaleman bas = (BaseSaleman) request.getSession().getAttribute(Dto.LOGIN_SALEMAN);
				System.out.println("into next step4 bas "+bas.toString()+"\n");
				loginBiz.updateSalesOpenId( openId,bas.getId());
				System.out.println("into next step5\n");
			}else{
				loginBiz.updateSalesOpenId( openId, bs.getId());
			}
		}
		return backMsg;
	}
	
	
	/**
	 * 页面跳转
	 * 跳转到login
	 * 
	 */
	@RequestMapping(value="login")
	public ModelAndView touserRegister(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		ModelAndView lo=new ModelAndView(Dto.getPagePath(type)+"/login");
	    return lo;
	}
	
	
	/**
	 * 登录操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("loginact")
	public void Control(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String username=request.getParameter("nickName");
		String password=request.getParameter("password");
		String isAutologin=request.getParameter("autologin");
		//得到session中的验证码和输入的验证码
		String verifyCode=(String) request.getSession().getAttribute("randVerifyCode");
		String inputCode=request.getParameter("verifycode");
		//1.查询用户
		JSONObject oUserObj=loginBiz.getLoginUserMsg(username,password);
		
		JSONObject msg=new JSONObject();
		if(oUserObj==null)
			msg.element("msg", "用户不存在或密码错误");
		else if(!(verifyCode).equals(inputCode.toLowerCase()))
			msg.element("msg", "验证码错误，请重新输入");
	    else{
	         String token=MathDelUtil.getRandomCode(10);
	    	
	    	//2.更新用户openId
	    	Object openId=request.getSession().getAttribute(Dto.WEIXIN_USER_OPENID);
	    	if(openId!=null)
	    		loginBiz.updateUserOpenId((String) openId, oUserObj.getLong("userID"));
	    	
	    	//更新用户token
	    	loginBiz.updateUserToken(token, oUserObj.getLong("userID"));
	    	
	    	//3.存入session
	    	request.getSession().setAttribute(Dto.LOGIN_USER, oUserObj); //在session中填入信息
	    	
	    	//4.存入cookies
	    	CookiesUtil.addCookie(Dto.USER_TOKEN, token, response);
	    
	    	if(isAutologin!=null)
	    		CookiesUtil.addCookie(Dto.REM_LOGIN_USERNAME, oUserObj.getString("tel"), response);
	    	
	    	msg.element("msg", "登录成功,正在跳转...");
	    	msg.element("success", "success");
	    	msg.element("userID", oUserObj.getLong("userID"));
	    	msg.element("shopID", oUserObj.getString("shopID"));
		}
	    	
		//5.返回消息
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	} 
	
	/**
	 * mall 远程登录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("loginmall")
	public void malllogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String username=request.getParameter("nickName");
		String password=request.getParameter("password");
		//1.查询用户
		JSONObject oUserObj=loginBiz.getLoginUserMsg(username,password);
		if(oUserObj!=null){
		
			String token=MathDelUtil.getRandomCode(10);
			//更新用户token
	    	loginBiz.updateUserToken(token, oUserObj.getLong("userID"));
	    	//存入cookies
	    	CookiesUtil.addCookie(Dto.USER_TOKEN, token, response);
	    	//存入session
	    	request.getSession().setAttribute(Dto.LOGIN_USER, oUserObj); 
		}
		Dto.printMsg(response, oUserObj.toString());
		
		
		
	}
	
	/**
	 * supplier登录页面
	 * @param request
	 * @return
	 */
	@RequestMapping("supplier/login")
	public String jump_supplier(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return "pc/supplierlogin";
	}
	
	/**
	 * 注册supplier界面
	 * @param request
	 * @return
	 */
	@RequestMapping("supplier/add")
	public String jump_addsupplier(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		int plat_type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return "pc/addsupplier";
	}
	/**
	 * supplier登录操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("supplier/loginact")
	public void ControlSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String username=request.getParameter("nickName");
		String password=request.getParameter("password");
		String isAutologin=request.getParameter("autologin");
		//得到session中的验证码和输入的验证码
		String verifyCode=(String) request.getSession().getAttribute("randVerifyCode");
		String inputCode=request.getParameter("verifycode");
		//1.查询用户
		JSONObject oSupplierUserObj=loginBiz.getLoginSupplierMsg(username, password);
		
		JSONObject msg=new JSONObject();
		if(oSupplierUserObj==null)
			msg.element("msg", "用户不存在或密码错误");
		else if(!(verifyCode).equals(inputCode.toLowerCase()))
			msg.element("msg", "验证码错误，请重新输入");
	    else{

	    	//3.存入session
	    	request.getSession().setAttribute(Dto.LOGIN_SUPPLIER, oSupplierUserObj); //在session中填入信息
	    	//4.存入cookies
	    	if(isAutologin!=null)
    		CookiesUtil.addCookie(Dto.SUPP_LOGIN_USERNAME, oSupplierUserObj.getString("tel"), response);
	    	CookiesUtil.addCookie(Dto.SUPP_LOGIN_PASSWORD, oSupplierUserObj.getString("password"), response);
	    	
	    	
	    	msg.element("msg", "登录成功,正在跳转...");
	    	msg.element("success", "success");
//	    	msg.element("userID", oSupplierUserObj.getLong("userID"));
//	    	msg.element("shopID", oUserObj.getString("shopID"));
		}
	    	
		//5.返回消息
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	} 
	
	/**
	 * supplier添加操作
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("add/supplierct")
	public void ControlAddSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String username=request.getParameter("username");
		String tel=request.getParameter("tel1");
		String pass=request.getParameter("pass1");
		
		
		BaseSupplier bs=new BaseSupplier();
		bs.setSupplierName(username);
		bs.setTel(tel);
		bs.setPassword(pass);
		bs.setStatus(1);
		
		JSONObject msg=new JSONObject();
		if(loginBiz.getLoginSupplierName(username)){
			msg.element("msg", "用户名已被注册");
		}
		else if(loginBiz.getLoginSupplierMsg(tel)){
			msg.element("msg", "电话号码已被注册");
		}
		else{
		loginBiz.addSupplier(bs);
		msg.element("success", "success");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
	/**
	 * supplier注销操作
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("supplier/loginout")
	public String supplierloginOut(HttpServletRequest request){
		HttpSession session=request.getSession();
		session.removeAttribute(Dto.LOGIN_SUPPLIER);
		return "pc/supplierlogin";
	}
	
	
	/**
	 * 注销操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("loginout")
	public ModelAndView loginOut(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//注销
		session.removeAttribute(Dto.LOGIN_USER);
		
		ModelAndView lo=new ModelAndView(Dto.getPagePath(type)+"/login");
	    return lo;
	}
	
	/**
	 * 业务员注销操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saleloginout")
	public ModelAndView saleloginOut(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//注销
		session.removeAttribute(Dto.LOGIN_SALEMAN);
		
		ModelAndView lo=new ModelAndView(Dto.getPagePath(type)+"/salesman_login");
	    return lo;
	}

	/**
	 * 生成随机验证码
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("verifycode")
	public void createVerifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        //存入会话session  
        HttpSession session = request.getSession();  
        session.setAttribute("randVerifyCode", verifyCode.toLowerCase());  
        //生成图片  
        int w = 200, h = 80;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
	}	
	
	/**
	 * 统一页面跳转
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("index")
	public void sendPathDefine(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获取用户信息
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//1.跳转判断
		if(session.getAttribute(Dto.LOGIN_USER)!=null)
		{
			JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
			if(!(userMsg.getString("isAdmin")).equals("null")&& userMsg.getInt("isAdmin")==(Dto.IS_ADMIN) && type==0){
				response.sendRedirect("/nanny/"+Dto.LOGIN_ADMIN_SEND);
				return;
			}else if(userMsg.containsKey("shopID") && !(userMsg.getString("shopID")).equals("null"))
			{
				response.sendRedirect("/nanny/"+Dto.LOGIN_SHOP_SEND);
				return;
			}
			else if(type!=0){	
				response.sendRedirect("/nanny/"+Dto.LOGIN_USER_SEND);
				return;
			}else{
				if(type==0)
					response.sendRedirect("/nanny/login.html");
				else
					response.sendRedirect("/nanny/register.html");
				return;
			}
		}else{
			if(type==0)
				response.sendRedirect("/nanny/login.html");
			else
				response.sendRedirect("/nanny/register.html");
			return;
		}
	}
	
	

}
