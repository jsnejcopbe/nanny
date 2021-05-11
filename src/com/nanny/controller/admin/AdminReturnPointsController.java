package com.nanny.controller.admin;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.nanny.biz.admin.AdminReturnPointsBiz;
import com.nanny.biz.admin.AdminSupplierBiz;
import com.nanny.biz.supplier.SupplierOrderBiz;
import com.nanny.model.BaseSupplier;
import com.nanny.model.SysReturnPoints;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;



@Controller
public class AdminReturnPointsController {
	
		@Resource
		private AdminReturnPointsBiz arp;
		
	
		/**
		 * 积分与分先金额表
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("admin/returnPoint")
		public String AdminSupplier(HttpServletRequest request,HttpServletResponse response) {
			
			
			JSONObject bean=arp.sysReturn();
			JSONArray arr= bean.getJSONArray("retp");
			request.setAttribute("arr", arr);
			return "pc/adminReturnPoints";
		}
		/**
		 * 新增积分换算比例(修改)
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 */
		@RequestMapping("admin/integralSetting")
		public void AdminInteralSet(HttpServletRequest request,HttpServletResponse response) throws IOException {
			
			double cash=Double.parseDouble(request.getParameter("cash"));
			int  integral=Integer.parseInt(request.getParameter("integral"));
			String id=request.getParameter("sys_id");
			System.out.println(id);
			if(id==null || id.equals("")){
			
			SysReturnPoints srp=new SysReturnPoints();
			srp.setCash(cash);
			srp.setIntegral(integral);
			
			Timestamp time = new Timestamp(System.currentTimeMillis());
			srp.setCreateTime(time);
			
			arp.addintegralset(srp);
			}
			else{
				int id1=Integer.parseInt(id);
				arp.updateIntegralSet(id1, cash, integral);
				
			}
			
			
			JSONObject msg=new JSONObject();
			msg.element("success","success");
			msg.element("msg", "操作成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
		}
		

		
		/**
		 * 积分兑换现金比例设定
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("admin/sysIntegralSetting")
		public String AdminSysIntegralSetting(HttpServletRequest request,HttpServletResponse response) throws IOException {
			
			
			JSONObject arr=arp.setReturnCash();
			request.setAttribute("arr",arr);
			return "pc/adminReturnPointsSetting";
		}
		
		@RequestMapping("admin/upSysIntegralSetting")
		public void AdminUpSysIntegralSetting(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
				double  cash=Double.parseDouble(request.getParameter("cashset"));
				
				arp.updateSysCash(cash);
				
				JSONObject msg=new JSONObject();
				
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/plain");
				response.getWriter().println(msg.toString());
				
		}
		
		
		@RequestMapping("admin/integralDel")
		public void AdminUpSysIntegralDel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
				long id =Long.parseLong(request.getParameter("id"));
				SysReturnPoints srp=new SysReturnPoints();
				srp.setId(id);
				arp.delSysReturn(srp);
				
				JSONObject msg=new JSONObject();
				msg.element("success","success");
				msg.element("msg", "操作成功");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/plain");
				response.getWriter().println(msg.toString());
				
		}
		
		
		
}


