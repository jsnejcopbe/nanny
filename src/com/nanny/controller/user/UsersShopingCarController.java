package com.nanny.controller.user;

import java.io.IOException;
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

import com.nanny.biz.user.UserBaseMsgBiz;
import com.nanny.biz.user.UserShopCarBiz;
import com.nanny.biz.user.UsersBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserReceiveAdd;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.TimeUtil;


/**
 * 买家购物车控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
@Controller
public class UsersShopingCarController {
 
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private UserShopCarBiz userShopCarBiz;
	
	@Resource
	private UserBaseMsgBiz ubm;
	
	@Resource
	private UsersBiz ub;
	
	
	/**
	 * 购物车列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/shopcar.html")
	public String ShopCar(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String receid=request.getParameter("id");
		if(receid!=null&&!"".equals(receid)){
			
			UserReceiveAdd rece=(UserReceiveAdd) sshUtilBiz.getObjectById(UserReceiveAdd.class, Long.valueOf(receid));
			request.setAttribute("rece",rece);
		
		}else if(session.getAttribute(Dto.RECEIVE_ID)!=null){
			Long id=(Long) session.getAttribute(Dto.RECEIVE_ID);
			UserReceiveAdd rece=(UserReceiveAdd) sshUtilBiz.getObjectById(UserReceiveAdd.class, id);
			request.setAttribute("rece",rece);
			
		}else{
			
			QueryParam queryParam1=new QueryParam();
			
			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			Map<String, String> typeMap1 = new HashMap<String, String>();
			Map<String, String> orderMap1 = new HashMap<String, String>();
			
			paramMap1.put("userId", json.getLong("userID"));
			typeMap1.put("userId", "eq");
		
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			queryParam1.setOrderMap(orderMap1);
			
			List<UserReceiveAdd>  rece=(List<UserReceiveAdd>) sshUtilBiz.getObjectList(UserReceiveAdd.class, queryParam1, null);
			
			if(rece.size()!=0){
				request.setAttribute("rece", rece.get(0));
			}else{
				try {
					response.sendRedirect("/nanny/users/addUseRess.html?shopcar=0");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		int point=ub.getUserPointByUserID(json.getLong("userID"));
		Long addID=(Long) session.getAttribute(Dto.SESSION_USER_AREA);
		double mony=ubm.getUserBalance(json.getLong("userID"));
		
		JSONArray shop=  userShopCarBiz.getshopList(json.getInt("userID"),addID.intValue());
		
		PageUtil pageUtil=new PageUtil();
			pageUtil.setPageIndex(1);
			pageUtil.setPageSize(100);
		
		JSONObject vc=ub.doUserCoupon(json.getInt("userID"),"0",pageUtil);
		request.setAttribute("shop", shop);
		request.setAttribute("point", point);
		request.setAttribute("mony", mony);
		request.setAttribute("vc", vc.getJSONArray("arr"));
		
		int url= (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		return Dto.getPagePath(url)+"/shopCar";
		
	}
	
	/**
	 * 提交购物
	 * @param request
	 * @param response
	 */
	@RequestMapping("users/updShopcar.html")
	public void updshopcar(HttpServletRequest request,HttpServletResponse response) {
		Dto.writeLog("start pay setp and now time is"+TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
		
		String shopId=request.getParameter("shopid");
		String recName=request.getParameter("recName");
		String tel=request.getParameter("tel");
		String address=request.getParameter("address");
		String community=request.getParameter("community");
		String doorplate= request.getParameter("doorplate");
		String total= request.getParameter("total");
		//String arryData= request.getParameter("arryData"); 
		String masg=request.getParameter("mesg");
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		JSONObject bean=new JSONObject();
		
		bean.element("shopId", shopId);
		bean.element("recName", recName);
		bean.element("tel", tel);
		bean.element("address", address);
		bean.element("community", community);
		bean.element("doorplate", doorplate);
		bean.element("total", total);
		//bean.element("arryData", arryData);
		bean.element("masg", masg);
		JSONArray mesg=userShopCarBiz.addShopOrder(bean, obj.getInt("userID"));
		
		request.getSession().setAttribute(Dto.ARRAY_SHOPID, mesg.toString());
		Dto.writeLog("order id add to session(JSONArray):"+mesg);
		JSONObject msg=new JSONObject();
		msg.element("msg", mesg);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		try {
			response.getWriter().println(msg.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	
	
	
	/**
	 * 删除购物记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/delShopcar.html")
	public void delshopcar(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String uId=request.getParameter("uid");
//		String mesg=userShopCarBiz.delShopOrder(Integer.valueOf(uId));
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "1");
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
	
	
	/**
	 * 购物车显示
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/pointgoods.html")
	public void shopcarpointpro(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String arryData=request.getParameter("arryData");
		
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		
		JSONObject bean=new JSONObject();
		bean.element("arryData", arryData);
		bean.element("userID", obj.get("userID"));
		
		JSONObject mesg=userShopCarBiz.doPointRedeem(bean);
		System.out.println(mesg);
		
		
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(mesg.toString());
	}
	
	/**
	 * 购物车抵用卷使用
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/shcarVoucher.html")
	public void shopvs(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject obj=  (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		String mag=userShopCarBiz.getshopcarvs(obj.getInt("userID"));
	
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(mag.toString());
	}
	
}
