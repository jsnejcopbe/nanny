package com.nanny.controller.global;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.nanny.biz.admin.SysGlaAreaBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysGlobalArea;
import com.nanny.model.UserReceiveAdd;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.CookiesUtil;
import com.zhuoan.util.HttpReqUtil;
import com.zhuoan.util.weixin.WeiXinJsApiConfigUtil;



/**
 * 城市、区域
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

@Controller
public class GlobalAreaController {
	
	@Resource
	private SSHUtilBiz sshUtilBiz;
	@Resource(name="sysGlaAreaBiz")
	private SysGlaAreaBiz g_AreaBiz;
	
	/**
	 * 城市列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/global_city.html")
	public String globacity(HttpServletRequest request,HttpServletResponse response) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT memo,city FROM sys_global_area ");
		sql.append("GROUP BY city ");
		sql.append("ORDER BY memo  ");
		Object[] queryParam={};
		/*List<SysGlobalArea> area=(List<SysGlobalArea>) sshUtilBiz.getObjectListBySQL(sql.toString(), queryParam, null);*/
		JSONArray area=JSONArray.fromObject(sshUtilBiz.getObjectListBySQL(sql.toString(), queryParam, null));
		
	/*	StringBuffer sql1=new StringBuffer();
		sql.append("SELECT memo,city FROM sys_global_area ");
		sql.append("GROUP BY memo ");
		sql.append("ORDER BY memo  ");
		Object[] queryParam1={};
		List<SysGlobalArea> area=(List<SysGlobalArea>) sshUtilBiz.getObjectListBySQL(sql.toString(), queryParam, null);
		JSONArray area1=JSONArray.fromObject(sshUtilBiz.getObjectListBySQL(sql1.toString(), queryParam1, null));
		
		//首字母
		ArrayList<String> smemo=new ArrayList<String>();
		
		
		for(int i=0;i<area1.size();i++){
			
			JSONObject bean=area1.getJSONObject(i);
				
				smemo.add(bean.getString("memo"));
			
		}
		
		System.out.println(smemo);*/
		
		String[] aa={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		JSONArray areaList=new JSONArray();
		int nowPos=0;//记录跳出之后，所在位置
		for(String text:aa){
			JSONObject tmpObj=new JSONObject();//
			JSONArray cityList=new JSONArray();//存城市
			
			for(int i=nowPos;i<area.size();i++){
				JSONObject tmpObj2=area.getJSONObject(i);
				if(text.equals(tmpObj2.getString("memo"))){//判断首字母相等
					String	ct= tmpObj2.getString("city");
					
					cityList.add(ct.substring(0, ct.length()-1));//存入数组	
				}else{
					nowPos=i;//跳出位置
					break;
				}
			}
			if(cityList.size()>0)
			{
				tmpObj.element("firNum", text.toUpperCase());
				tmpObj.element("data", cityList);
				
				areaList.element(tmpObj);
			}
			

				}
		
			String ip;
		   if (request.getHeader("x-forwarded-for") == null) { 
		        ip= request.getRemoteAddr();  
		    }else{
		    	ip= request.getHeader("x-forwarded-for");  
		    }
		    
		    String ip1="117.24.57.197";
		    String path="http://ip.taobao.com/service/getIpInfo.php"; 
		    String city= HttpReqUtil.doGet(path, "ip="+ip, "utf-8");
		    
		    Dto.writeLog("city:"+city+"\n");
		    
		    JSONObject msg=JSONObject.fromObject(city);
		    int code=msg.getInt("code");//0：成功  1：失败
		    //String name= msg.getJSONObject("data").getString("city");
		    if(code==1){
		    	
		    	request.setAttribute("code", code);
		    }else{
		    	
		    	String name= msg.getJSONObject("data").getString("city");
		    	
		    	String ctname=name.substring(0,name.length()<1?0:(name.length()-1));
		    	request.setAttribute("ctname", ctname);
		    	request.setAttribute("code", code);
		    }
		    
		    request.setAttribute("areaList", areaList);
		    
		    request.setAttribute("jump", request.getParameter("jump"));
		    
		    int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		    
			request.setAttribute("weiXinJsApiConfig",WeiXinJsApiConfigUtil.get(request));
		    
		    return Dto.getPagePath(url)+"/Globalarea";
		
	}
	
	/**
	 * 选择城市区域
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/global_addName.html")
	public String queryaddname(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		try{
			QueryParam queryParam=new QueryParam();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			Map<String, String> orderMap = new HashMap<String, String>();
			
			HttpSession session =request.getSession();
			JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
			
			//request.setCharacterEncoding("utf-8");
			
			String city=request.getParameter("city");
			if(city!=null){
				city=new String(city.getBytes("ISO-8859-1"), "UTF-8");
			}
			System.out.println(city);
			String name=request.getParameter("addname");
			if(name!=null){
				name=new String(name.getBytes("ISO-8859-1"), "UTF-8");
			}
			
			System.out.println(name);
			paramMap.put("city", "%"+city+"%");
			typeMap.put("city", "like");
			
			paramMap.put("addName", "%"+name+"%");
			typeMap.put("addName", "like");
			
			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			queryParam.setOrderMap(orderMap);
			
			
			List<SysGlobalArea>  arealist=(List<SysGlobalArea>) sshUtilBiz.getObjectList(SysGlobalArea.class, queryParam, null);
			request.setAttribute("arealist", arealist);
			//用户是否登录 显示用户收货地址
			if(session.getAttribute(Dto.LOGIN_USER)!=null){
				QueryParam queryParam1=new QueryParam();
				
				Map<String, Object> paramMap1 = new HashMap<String, Object>();
				Map<String, String> typeMap1 = new HashMap<String, String>();
				Map<String, String> orderMap1 = new HashMap<String, String>();
				
				paramMap1.put("userId", json.getLong("userID"));
				typeMap1.put("userId", "eq");
				
				queryParam1.setParamMap(paramMap1);
				queryParam1.setTypeMap(typeMap1);
				queryParam1.setOrderMap(orderMap1);
				
				List<UserReceiveAdd>  ura=(List<UserReceiveAdd>) sshUtilBiz.getObjectList(UserReceiveAdd.class, queryParam1, null);
				request.setAttribute("ura", ura);
			}
			
			String city1=request.getParameter("city");
			if(city1!=null){
				city1=new String(city1.getBytes("ISO-8859-1"), "UTF-8");
			}
			request.setAttribute("city",city1);
			System.out.println(city1);
			
			String name1=request.getParameter("addname");
			if(name1!=null){
				name1=new String(name1.getBytes("ISO-8859-1"), "UTF-8");
			}
			request.setAttribute("name",name1);
			
			int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			
			request.setAttribute("weiXinJsApiConfig",WeiXinJsApiConfigUtil.get(request));
			
			return Dto.getPagePath(url)+"/Globalname";
		}catch (Exception e) {
			
			Dto.writeLog("the add search occur an error,detail msg is:"+e.getMessage());
			return "";
		}
	}
	
	/**
	 * 选择地区跳转
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/areaJump.html")
	public void areaJump(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		  String  receid= request.getParameter("receid");
		  
		  String  areaid= request.getParameter("areaid");
		  String url=(String) request.getSession().getAttribute(Dto.ACCESS_ADDRESS);
		  
		  
		if(receid!=null&&!"".equals(receid)){
			UserReceiveAdd rece= (UserReceiveAdd) sshUtilBiz.getObjectById(UserReceiveAdd.class, Long.valueOf(receid));
			
			request.getSession().setAttribute(Dto.RECEIVE_ID,Long.valueOf(receid));//存入收货地址ID
			
			CookiesUtil.addCookie(Dto.USER_AREA, rece.getMemo(), response);
			response.sendRedirect(createUrl((url==null || url.indexOf("store")!=-1)?"http://www.zsbaomu.com/nanny/supermarket.html":url,rece.getMemo()));
			//response.sendRedirect("/nanny/supermarket.html?addid="+rece.getMemo());
			
		}else{
			CookiesUtil.addCookie(Dto.USER_AREA, areaid, response);
			response.sendRedirect(createUrl((url==null || url.indexOf("store")!=-1)?"http://www.zsbaomu.com/nanny/supermarket.html":url,areaid));
			//response.sendRedirect("/nanny/supermarket.html?addid="+areaid);
		}
		
		
	

		/*return "redirect:/login.html";*/
		
	}
	
	private String createUrl(String url,String areaid){
		
		if(url.indexOf("?")!=-1){
			url+="&addid="+areaid;
			
		}else{
			
			url+="?addid="+areaid;
		}
		
		return url;
		
	}
	/**
	 * 用户新增地址选择城市区域
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/global_Name.html")
	public void queryname(HttpServletRequest request,HttpServletResponse response) throws IOException {
		QueryParam queryParam=new QueryParam();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		
		
		long city= (Long) request.getSession().getAttribute(Dto.SESSION_USER_AREA);
		//String city= CookiesUtil.getCookie(Dto.USER_AREA, request);//取出COOK 中 用户选择的ID
		//通过ID 查询 城市等信息
		SysGlobalArea yga=  (SysGlobalArea) sshUtilBiz.getObjectById(SysGlobalArea.class, city);
		
		
		String name=request.getParameter("addname");
//		if(name!=null){
//			name=new String(name.getBytes("ISO-8859-1"), "UTF-8");
//			}
		System.out.println("新增"+name);
		paramMap.put("city", "%"+yga.getCity()+"%");
		typeMap.put("city", "like");
			
		paramMap.put("addName", "%"+name+"%");
		typeMap.put("addName", "like");
	
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);
		
		List<SysGlobalArea>  area=(List<SysGlobalArea>) sshUtilBiz.getObjectList(SysGlobalArea.class, queryParam, null);
		/*request.setAttribute("area", area);*/
		JSONObject msg=new JSONObject();
		msg.element("area", area);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
		/*int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/addUseRess";*/
	}
	
	/**
	 * 使用ajax查询地址库地址名称
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/ajaxqueryarea")
	public void ajaxQueryAreaName(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
		String city=request.getParameter("city");
		String name=request.getParameter("name");
		
		//1.查询相符地址
		JSONArray data=g_AreaBiz.getSysAreaByCityAndName(city, name);
		
		//2.返回数据
		Dto.printMsg(response, data.toString());
	}
	
	/**
	 * 使用session中 areaID查询地址库
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/sessAreaID")
	public void globalsession(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		HttpSession session =request.getSession();
		JSONObject msg=new JSONObject();
		
		Long json=  (Long) session.getAttribute(Dto.SESSION_USER_AREA);
		if(json!=null){
			SysGlobalArea sga=(SysGlobalArea) sshUtilBiz.getObjectById(SysGlobalArea.class, json);
			msg.element("addname", sga.getAddName());
			msg.element("betadd", sga.getCity());
			msg.element("areaID",sga.getId());
		}else{
			msg.element("addname", "");
			msg.element("betadd", "");
			msg.element("areaID","");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
 }

