package com.nanny.controller.admin;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.admin.AdminReturnPointsBiz;
import com.nanny.biz.global.statistics.StatisticsBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.ShopProduct;
import com.nanny.model.SysReturnPoints;
import com.nanny.model.UserAccountsRec;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.QueryParam;

@Controller
public class AdminIndexController {
		@Resource
		private SSHUtilBiz sshUtilBiz;
		
		@Resource
		private StatisticsBiz sbl;
		
		@Resource
		private AdminReturnPointsBiz arp;
		/**
		 * 总后台首页统计
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("admin/adminIndex.html")
		public String indexStatistics(HttpServletRequest request,HttpServletResponse response) {
			
			QueryParam queryParam = new QueryParam();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			Map<String, String> orderMap = new HashMap<String, String>();
			
			
			paramMap.put("shopId", 0L);
			typeMap.put("shopId", "eq");
				
			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			queryParam.setOrderMap(orderMap);
			
			
			String colName="id";
			//平台商品数量
			int product= sshUtilBiz.getObjectCount(ShopProduct.class, colName, queryParam);
			
			
			QueryParam queryParam1 = new QueryParam();
			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			Map<String, String> typeMap1 = new HashMap<String, String>();
			Map<String, String> orderMap1 = new HashMap<String, String>();
			
			
			paramMap1.put("situation", 0);
			typeMap1.put("situation", "eq");
				
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			queryParam1.setOrderMap(orderMap1);
			//平台商户
			int shop=sshUtilBiz.getObjectCount(BaseShop.class, colName, queryParam1);
			
			QueryParam queryParam2 = new QueryParam();
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			Map<String, String> typeMap2 = new HashMap<String, String>();
			
			paramMap2.put("type", 1);
			typeMap2.put("type", "eq");
				
			queryParam2.setParamMap(paramMap2);
			queryParam2.setTypeMap(typeMap2);
			
			String colName1="money";
			double cz=sshUtilBiz.getSum(UserAccountsRec.class, colName1, queryParam2);
			
			paramMap2.clear();
			typeMap2.clear();
			paramMap2.put("type", 3);
			typeMap2.put("type", "eq");
				
			queryParam2.setParamMap(paramMap2);
			queryParam2.setTypeMap(typeMap2);
			double tx=sshUtilBiz.getSum(UserAccountsRec.class, colName1, queryParam2);
			
			paramMap2.clear();
			typeMap2.clear();
			paramMap2.put("type", 5);
			typeMap2.put("type", "eq");
				
			queryParam2.setParamMap(paramMap2);
			queryParam2.setTypeMap(typeMap2);
			double txtk=sshUtilBiz.getSum(UserAccountsRec.class, colName1, queryParam2);
			
			  double ye=cz-tx+txtk;
			  
			  DecimalFormat df = new DecimalFormat("#######.00"); 
			  String ys=  df.format(ye);
			//业务员列表
			 StringBuffer sql=new StringBuffer();
			 sql.append(" SELECT  a.name,a.tel,COUNT(b.id) as sum FROM base_saleman a ");
			 sql.append(" LEFT JOIN base_shop b on b.SalmanID=a.id ");
			 sql.append(" GROUP BY b.SalmanID ");
			 sql.append(" ORDER BY sum DESC LIMIT 5 ");
			 Object[] Param={};
			 JSONArray salman=JSONArray.fromObject( sshUtilBiz.getObjectListBySQL(sql.toString(), Param, null));
			 
			//平台每日下单数
				JSONArray orsum=  sbl.dayordersum(0);
				
			JSONObject bean=arp.sysReturn();
			JSONArray arr= bean.getJSONArray("retp");
			
			JSONObject arr1=arp.setReturnCash();
			request.setAttribute("arr1",arr1);
			request.setAttribute("arr", arr);
	
			request.setAttribute("product",product);
			request.setAttribute("shop",shop);
			request.setAttribute("salman", salman);
			request.setAttribute("ye", ys);
			request.setAttribute("orsum",orsum);
			
			int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/index-admin";
		}
		
		/**
		 * 设置兑换比例
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("admin/upcash")
		public void AdminUpSysIntegralSetting(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
				double  cash=Double.parseDouble(request.getParameter("cashset"));

				arp.updateSysCash(cash);
				
				JSONObject msg=new JSONObject();
				msg.element("msg","操作成功" );
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/plain");
				response.getWriter().println(msg.toString());
				
		}
		
		
		@RequestMapping("admin/setform")
		public void AdminUpIntegralSet(HttpServletRequest request,HttpServletResponse response) throws IOException {
			
			JSONArray arr=JSONArray.fromObject(request.getParameter("arr"));
			arp.delAll();
			for(int i=0;i<arr.size();i++){
			 	double cash=arr.getJSONObject(i).getDouble("cash11");
			 	int  integral=arr.getJSONObject(i).getInt(("integral11"));
			 	SysReturnPoints srp=new SysReturnPoints();
			 	srp.setCash(cash);
			 	srp.setIntegral(integral);
			 	arp.addintegralset(srp);
			}
			
			JSONObject msg=new JSONObject();
			msg.element("success","success");
			msg.element("msg", "操作成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());

		}
		
		/**
		 * 删除
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("admin/delSet")
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


