package com.nanny.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.conn.Wire;
import org.apache.log4j.Logger;

import com.zhuoan.util.TimeUtil;

import net.sf.json.JSONObject;


/**
 * 系统常量bean
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @version 0.1
 */
public class Dto {
	// basis start
	public static final String REDIRECT = "redirect:/";

	public static final String PC_REDIRECT = "redirect:/work/pc/";

	public static final String MOBILE_REDIRECT = "redirect:/work/mobile/";

	public static final String NULL_ERROR = "不能为空";

	public static final String EXIST_ERROR = "exist";//存在错误
	
	public static final String ONE_ERROR = "请至少选择一行";

	public static final String STATE_ERROR = "没有权限";
	
	public static final String SUCCEED = "";
	// basis end
	
	
	//平台基础数据 start：
	public static String PC_PAGE_PATH="pc";
	
	public static String MOBILE_PAGE_PATH="mobile";
	
	public static String PLAT_TYPE_NAME="playType"; //平台类型sessionKey
	
	public static String LOGIN_USER="loginUser";//登录用户sessionKey
	
	public static String LOGIN_ADMIN_TMP="tmpadminobj";//登录管理员临时存放sessionKey
	
	public static String LOGIN_SALEMAN="login_saleman";//登录业务员sessionKey
	
	public static String LOGIN_SUPPLIER="login_supplier";//登录供应商sessionKey
	
	public static String ERROR_CODE = "-1";
	
	public static String REGISTER_USER="registerUser";//注册用户的sessionKey
	
	public static String VERTIFY_CODE="randVerifyCode";//验证码名称
	
	public static String SHOPID_OF_RECOMMEND="sessionrecshopID";
	
	public static String PLAT_ADDRESS="addressType";//购物车地址请求
	
	//平台基础数据 end
	
	//商户申请 状态 start
	public static final String SHOP_ADD_TYPE = "add_type";
	
	public static final int WAIT_OK = 0;
	public static final int OK = 1;
	public static final int NO_OK = 2;
	
	public static String getShopState(String i){
		switch (Integer.valueOf(i)) {
			case Dto.NO_OK:
				return "未通过";
			case Dto.WAIT_OK:
				return "待审核";
			case Dto.OK:
				return "通过";
		}
		return "";
	}
	//商户申请 状态 end
	
	//商户 状态 start
		
	public static final int SHOP_SITU = 0;//启用
	
	public static final int SHOP_STOPPING=1;//歇业
		
	//商户 状态 end
	
	
    //登陆跳转  start 
	
	public static final String LOGIN_USER_SEND = "users/userIndex.html";
	public static final String LOGIN_SHOP_SEND = "shop/shopIndex.html";

	
	public static final String LOGIN_SUPP_SEND = "supplier/supplierIndex.html";//供应商跳转
		
	public static final String LOGIN_ADMIN_SEND = "admin/adminIndex.html";

	public static final int IS_ADMIN=1;
	
	//end 跳转结束
	
	//自动登录信息 start
	public static String  REM_LOGIN_USERNAME="remloginusername";
	public static String  REM_LOGIN_PASSWORD="remloginpassword";
	
	public static String USER_TOKEN="usertoken";
	//自动登录信息 end 
	
	//业务员自动登录信息 start
		public static String  SALES_LOGIN_USERNAME="salesloginusername";
		public static String  SALES_LOGIN_PASSWORD="salesloginpassword";
	//业务员自动登录信息 end 
		
	//供应商自动登录信息 start
		public static String  SUPP_LOGIN_USERNAME="supploginusername";
		public static String  SUPP_LOGIN_PASSWORD="supploginpassword";
	//供应商自动登录信息 end 
	
	//商品状态  start
	public static final int IS_SALE =0;//上架
	
	public static final int IS_RECOMMOND=0;//是否推荐
	
	public static final String getIsUseState(String isUse) {
		return "0".equals(isUse)?"上架":"下架";
	}
	
	public static final String getIsRecommond(int isRec) {
		return isRec==IS_RECOMMOND?"非推荐商品":"推荐商品";
	}
	
	public static final int IS_COM =0;//计算佣金
	
	//商品状态  end
	
	//地区选择 start
	public static final String USER_AREA ="user_city";//地区

	public static final String SESSION_USER_AREA="area_id";
	
	public static final String RECEIVE_ID="receive_id";//收货地址ID
	
	public static final String ACCESS_ADDRESS="access_address";//当前访问地址
	//地区选择 end
	
	//订单状态 start
	public static final int STATU_WAITPAY=0;//待付款
	public static final int STATU_WAITSEND=1;//待发货\已付款
	public static final int STATU_WAITREV=2;//已发货\待收货
	public static final int STATU_DONE=3;//交易完成
	public static final int STATU_REFUND=4;//退款
	public static final String ORDER_COUNT_WAIT_DEL="waitSendOrderCount";//待处理订单数
	public static final String APPLY_COUNT_WAIT_DEL="waitSendApplyCount";//待处理申请数
	public static final String PRO_APPLY_COUNT_WAIT_DEL="waitSendProApplyCount";//待处理申请数
	
	public static String getOrderState(String i,String shopID){
		boolean b = "null".equals(shopID);
		switch (Integer.valueOf(i)) {
			case Dto.STATU_WAITPAY:
				return "待付款";
			case Dto.STATU_WAITSEND:
				return b ? "待发货" : "已付款/货到付款";
			case Dto.STATU_WAITREV:
				return b ? "已发货" : "待收货";
			case Dto.STATU_DONE:
				return "交易完成";
			case Dto.STATU_REFUND:
				return "订单取消";
		}
		return "";
	}
	
	//订单状态 end
	
	//账目类型 start
	public static int CASH_OF_REFUND=0;//退款
	public static int CASH_OF_CHARGE=1;//充值
	public static int CASH_OF_PAYMENT=2;//付款
	public static int CASH_OF_WITHDRAW =3;//提现
	public static int CASH_OF_BACKPAY =4;//返现
	public static int CASH_OF_TRANSFERREFUND =5;//提现退款
	//账目类型 end
	
	//站内信是否已读 start
	public static int IS_MSG_READ=0;//未读
	//站内信是否已读 end
	
	//微信支付 start
	public static String MONEY_OF_CHARGE="charge_money";//支付金额
	public static String CALL_BACK_URL="call_back_url";//回调地址
	
	public static String ARRAY_SHOPID="array_shopid";//存付款订单ID
	
	public static int CHARGE_DONE=0;//充值成功
	public static int CHARGE_UNPAY=1;//未支付
	
	public static String getCharge_stata(String type) {
		return (CHARGE_DONE+"").equals(type) ? "充值成功" : "未支付";
	}
	
	//微信支付 end
	
	//微信登录 start
	public static String WEIXIN_USER_OPENID="user_openid";//存储当前用户的微信openid
	public static String USER_WEIXIN_INFO="user_weixin_info";//用户微信信息
	//微信登录 end
	
	//用户申请提现 start
	public static int TRANSFER_WAIT_CHECK=0;
	public static int TRANSFER_PASS_CHECK=1;
	public static int TRANSFER_FAIL_CHECK=2;
	//用户申请提现 end
	
	//提现银行状态 start
	public static int BANK_ISUSER=0;//0开启 1关闭
	//提现银行状态 end
	

	//添加商品申请状态 start
	public static int SHOP_PRO_PENDING=0;//待处理
	public static int SHOP_PRO_AVAILABLE=1;//可使用
	public static int SHOP_PRO_UNAVAILABLE=2;//不可用
	//添加商品申请状态 end
	
	//商品查询条件 start
	public static String PRO_QUERY_ITEMS="pro_query_items";//待处理
	
	//商品查询条件 end
	
	//积分流动类型 start
	public static int USER_DEDUCT=0;//积分扣除
	public static int USER_INCREASE=1;//积分增加
	//积分流动类型 end
	
	//商户小票打印机类型 start
		public static int PRINTER_TYPE_WIFI=0;//无线接口
		public static int PRINTER_TYPE_NONE=1;//无
		public static int PRINTER_TYPE_USB=2;//USB接口
	//商户小票打印机类型  end
		
		
	//商户小票打印机类型 start
		public static int VOUCHER_STATUS_0=0;//未使用
		public static int VOUCHER_STATUS_1=1;//已使用
		public static int VOUCHER_STATUS_2=2;//已过期
	//商户小票打印机类型  end	
		
	//日志私有对象
		private static Logger log;
		
		
	/**
	 * 根据传入平台类型返回页面目录
	 * @param type 0：pc 1：移动
	 * @return
	 */
	public static String getPagePath(int type){
		return type==0?PC_PAGE_PATH:MOBILE_PAGE_PATH;
	}
	
	public static String getShopID(HttpServletRequest request){
		JSONObject obj = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		return  obj.get("shopID").toString();
	}
	
	public static String getUserID(HttpServletRequest request){
		JSONObject obj = (JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
		return  obj.get("userID").toString();
	}
	
	public static void printMsg(HttpServletResponse response, String msg) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().write(msg);
		
		writeLog("print msg to response stream msg is"+msg+" and now time is"+TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 获得本机IP
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	public static String getLocalIp(HttpServletRequest request) throws UnknownHostException{
		return request.getRemoteAddr();
	}
	
	/**
	 * 接收远程传递的post消息
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String receivePostMsg(ServletInputStream inputStream) throws IOException{
		BufferedReader input=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String line=null;
		String result = "";
		try {
			while((line=input.readLine())!=null)
				result+=line+"\r\n";
		}catch(Exception e){
			e.printStackTrace();
		}finally{input.close();}
		
		return result;
	}
	
	/**
	 * 输出日志
	 * @param msg
	 */
	public static void writeLog(String msg){
		log=Logger.getLogger(Dto.class);
		log.info(msg);
	}
}
