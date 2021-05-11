package com.nanny.controller.user;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.user.UserCollectBiz;
import com.nanny.dto.Dto;


/**
 * 用户我的收藏
 * 控制层
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author LPC
 * @version 0.1
 */
@Controller
public class usermyCollectionController {
    //载入资源
	@Resource(name="usercollectBiz")
	private UserCollectBiz userCollectBiz;
	
	
	@RequestMapping("users/myCollection")
	public ModelAndView getuserMyCollection(HttpServletRequest request,HttpServletResponse response)throws IOException{
		//获得参数
		HttpSession session=request.getSession();
		JSONObject object = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		Long userID =object.getLong("userID");
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//查询
		JSONArray mycollection = userCollectBiz.getUserCollectionByuserID(userID);
		 ModelAndView mav =new ModelAndView(Dto.getPagePath(type)+"/usermyCollection");  
		 mav.addObject("mycollectionlist", mycollection);
		 return mav;				
	}
	
	
	/**
	 * 删除我的收藏
	 * 控制层
	 * @Copyright Copyright (c) 2016
	 * @Company zhouan
	 * @author LPC
	 * @version 0.1
	 */
	@RequestMapping("users/delcollection")
	  public void delcollection(HttpServletRequest request,HttpServletResponse response)throws IOException{
		//得到参数
		String user = request.getParameter("userID");
		String shop = request.getParameter("shopID");
	    Long  userID= Long.valueOf(user);
		Long  shopID = Long.valueOf(shop);
		
		String usercolltion =userCollectBiz.delUserCollectByuserIDandshopID(userID, shopID);
		 JSONObject msg = new JSONObject();
			msg.element("msg","删除成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());	
		
	}	  	
}
