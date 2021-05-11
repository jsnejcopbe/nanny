package com.nanny.aop;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.nanny.biz.global.aopdel.GlobalSiteMsgBiz;
import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.biz.user.UserBaseMsgBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserSiteMessage;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.WeixinUserMsgPush;

/**
 * 平台系统消息AOP类
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Aspect
@Component
public class GloSysMsgAop extends PointcutCollection {

	//载入资源
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderMsgBiz;
	@Resource(name="globalSiteMsgBiz")
	private GlobalSiteMsgBiz g_SiteMsgBiz;
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_ShopBaseMsgBiz;
	@Resource(name="userBaseMsgBiz")
	private UserBaseMsgBiz g_UserMsgBiz;
	
	/**
	 * 商户发货信息发送
	 * @param point
	 */
	@After("shopOrderDel()")
	public void shopOrderDelMsgAop(JoinPoint point){
		//0.获得传入参数
		Object[] args=point.getArgs();
		Long orderID=(Long) args[0];//订单ID
		JSONObject orderBase=g_OrderMsgBiz.getOrderBaseByOrderID(orderID);
		
		//1.组织站内信内容参数
		JSONObject tmpParam=new JSONObject();
		tmpParam.element("titel", "订单状态变更通知");
		tmpParam.element("orderCode", orderBase.getString("orderCode"));
		tmpParam.element("msg", "已开始进行配送，请注意查收");
		tmpParam.element("href", "javascript:void(0)");
		
		//2.组织站内信数据
		UserSiteMessage bean=new UserSiteMessage();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setIsRead(Dto.IS_MSG_READ);
		bean.setMailCon(createMsg(tmpParam));
		bean.setOtherSide(orderBase.getLong("payUserID"));
		bean.setUserId((long)0);
		bean.setMemo("系统通知，请勿回复");
		
		g_SiteMsgBiz.addNewSysMsg(bean);
	}
	
	/**
	 * 商户退款信息发送
	 * @param point
	 * @throws UnsupportedEncodingException 
	 */
	@After("shopOrderRefDel()")
	public void shopOrderMsgAop(JoinPoint point) throws UnsupportedEncodingException{
		//0.获得传入参数
		JSONObject param=(JSONObject)point.getArgs()[0];
		Long userID=param.getLong("userID");//接收用户ID
		Double cost=param.getDouble("cost");//扣款金额
		
		//1.组织站内信内容参数
		JSONObject tmpParam=new JSONObject();
		tmpParam.element("titel", "订单状态变更通知");
		tmpParam.element("orderCode", param.getString("orderCode"));
		tmpParam.element("msg", "已成功退款，退款金额"+cost+"元，请注意您的账户余额情况");
		tmpParam.element("href", "javascript:void(0)");
		
		//2.组织站内信数据
		UserSiteMessage bean=new UserSiteMessage();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setIsRead(Dto.IS_MSG_READ);
		bean.setMailCon(createMsg(tmpParam));
		bean.setOtherSide(userID);
		bean.setUserId((long)0);
		bean.setMemo("系统通知，请勿回复");
		
		g_SiteMsgBiz.addNewSysMsg(bean);
		
		WeixinUserMsgPush.pushMsgToUser(createWeixinMsg(userID, g_OrderMsgBiz.getOrderMsgForWXMail(param.getString("orderCode"))));
	}
	
	/**
	 * 商户取消订单信息发送
	 * @param point
	 * @throws UnsupportedEncodingException 
	 */
	@After("shopOrderRefRecDel()")
	public void shopOrderRefMsgAop(JoinPoint point) throws UnsupportedEncodingException{
		//0.获得传入参数
		JSONObject param=(JSONObject)point.getArgs()[0];
		Long userID=param.getLong("userID");//接收用户ID
		
		//1.组织站内信内容参数
		JSONObject tmpParam=new JSONObject();
		tmpParam.element("titel", "订单状态变更通知");
		tmpParam.element("orderCode", param.getString("orderCode"));
		tmpParam.element("msg", "已被取消");
		tmpParam.element("href", "javascript:void(0)");
		
		//2.组织站内信数据
		UserSiteMessage bean=new UserSiteMessage();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setIsRead(Dto.IS_MSG_READ);
		bean.setMailCon(createMsg(tmpParam));
		bean.setOtherSide(userID);
		bean.setUserId((long)0);
		bean.setMemo("系统通知，请勿回复");
		
		g_SiteMsgBiz.addNewSysMsg(bean);
		
		WeixinUserMsgPush.pushMsgToUser(createWeixinMsg(userID, g_OrderMsgBiz.getOrderMsgForWXMail(param.getString("orderCode"))));
	}
	
	/**
	 * 用户支付消息发送
	 * @param point
	 */
	@After("updateUserorderDel()")
	public void updateUserorderMsgAop(JoinPoint point){
		//0.获得传入参数
		JSONArray orderIDList=(JSONArray)point.getArgs()[0];//订单ID合集
		
		//1.组织站内信内容参数
		for(int i=0;i<orderIDList.size();i++)
		{
			Long orderID=orderIDList.getLong(i);
			JSONObject payData=g_ShopBaseMsgBiz.getShopBizByOrderID(orderID);
			
			JSONObject tmpParam=new JSONObject();
			tmpParam.element("titel", "订单状态变更通知");
			tmpParam.element("orderCode", payData.getString("orderCode"));
			tmpParam.element("msg", "已付款，请注意及时发货");
			tmpParam.element("href", "javascript:void(0)");
			
			//2.组织站内信数据
			UserSiteMessage bean=new UserSiteMessage();
			bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
			bean.setIsRead(Dto.IS_MSG_READ);
			bean.setMailCon(createMsg(tmpParam));
			bean.setOtherSide(payData.getLong("userID"));
			bean.setUserId((long)0);
			bean.setMemo("系统通知，请勿回复");
			
			g_SiteMsgBiz.addNewSysMsg(bean);
		}
	}
	
	/**
	 * 用户退款申请消息发送
	 * @param point
	 */
	@After("ordeRefundDel()")
	public void ordeRefundMsgAop(JoinPoint point){
		//0.获得传入参数
		Long orderID=(Long) point.getArgs()[1];//订单ID
		
		//1.获得订单基础信息
		JSONObject orderMsg=g_OrderMsgBiz.getOrderBaseByOrderID(orderID);
		
		//2.组织站内信内容参数
		JSONObject tmpParam=new JSONObject();
		tmpParam.element("titel", "用户退款通知");
		tmpParam.element("orderCode", orderMsg.getString("orderCode"));
		tmpParam.element("msg", "用户申请退款，请及时进行处理");
		tmpParam.element("href", "javascript:void(0)");
		
		//3.组织站内信数据
		UserSiteMessage bean=new UserSiteMessage();
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setIsRead(Dto.IS_MSG_READ);
		bean.setMailCon(createMsg(tmpParam));
		bean.setOtherSide(orderMsg.getLong("recShopID"));
		bean.setUserId((long)0);
		bean.setMemo("系统通知，请勿回复");
		
		g_SiteMsgBiz.addNewSysMsg(bean);
	}
	
	/**
	 * 用户确认订单消息发送
	 * @param point
	 * @throws UnsupportedEncodingException 
	 */
	@After("orderConfirmDel()")
	public void orderConfirmMsgAop(JoinPoint point) throws UnsupportedEncodingException{
		//0.获得传入参数
		JSONArray IDList=(JSONArray) point.getArgs()[0];
		
		//1.遍历发送消息
		for(int i=0;i<IDList.size();i++)
		{
			Long orderID=IDList.getLong(i);
			JSONObject orderMsg=g_OrderMsgBiz.getOrderBaseByOrderID(orderID);
			WeixinUserMsgPush.pushMsgToUser(createWeixinMsgForShop(orderMsg.getLong("shopUserID"), g_OrderMsgBiz.getOrderMsgForWXMail(orderMsg.getString("orderCode"))));
		}
	}
/****************************************************************************************************************************************/	
	
	/**
	 * 创建消息内容
	 * @param tmplateParam
	 * @return
	 */
	private String createMsg(JSONObject tmplateParam)
	{
		String html="<h3>"+tmplateParam.getString("titel")+"</h3>" +
				    "<p>您的订单 <br>"+tmplateParam.getString("orderCode")+"</br></p>" +
				    "<p>"+tmplateParam.getString("msg")+"</p>" +
				    "<p>请<a href='("+tmplateParam.getString("href")+")'>点击这里</a>前往查看</p>";
		return html;
	}
	
	/**
	 * 创建订单取消微信推送消息模板
	 * @return
	 */
	private JSONObject createWeixinMsg(Long userID,JSONObject orderMsg){
		//0.获得用户信息
		JSONObject userMsg=g_UserMsgBiz.getUserMsgByUserID(userID);
		
		//1.拼接模板
		if(!("null").equals(userMsg.getString("originID")) && orderMsg!=null)
		{
			//基础信息
			JSONObject templat=new JSONObject();
			templat.element("touser", userMsg.getString("originID"));
			templat.element("template_id", "LRVdhmxYxkvjrwWhq68LKGGc3mB9nhxUh2gqbxdFRzk");
			templat.element("url", "http://www.zsbaomu.com/nanny/users/orDetails.html?orid="+orderMsg.getLong("id"));
			//文字内容
			JSONObject data=new JSONObject();
			data.element("first", createKeyNote("您的订单已被取消"+(("null").equals(orderMsg.getString("memo"))?",请注意查看您的账户余额":""), "#173177"));//模板第一行
			
			data.element("keyword1", createKeyNote(orderMsg.getString("name")+"等", "#173177"));//第二行
			
			data.element("keyword2", createKeyNote(orderMsg.getString("orderCode"), "#173177"));//第三行
			
			data.element("keyword3", createKeyNote(orderMsg.getDouble("totalPrice")+"元", "#173177"));//第四行
			
			data.element("keyword4", createKeyNote(("null").equals(orderMsg.getString("memo"))?"在线付款":"货到付款", "#173177"));//第五行
			
			data.element("remark", createKeyNote("原因："+orderMsg.getString("shopmsg")+",如有任何疑问，请及时与商家联系", "#173177"));
			
			templat.element("data", data);
			
			//2.返回结果
			return templat;
		}else
			return null;
	}
	
	private JSONObject createWeixinMsgForShop(Long userID,JSONObject orderMsg){
		//0.获得用户信息
		JSONObject userMsg=g_UserMsgBiz.getUserMsgByUserID(userID);
		if(!("null").equals(userMsg.getString("originID")) && orderMsg!=null)
		{
			//基础信息
			JSONObject templat=new JSONObject();
			templat.element("touser", userMsg.getString("originID"));
			templat.element("template_id", "U_8zwqyrESPXwA8ThVzGa7-XYDzaj_5GRmK5ib7q_WI");
			templat.element("url", "http://www.zsbaomu.com/nanny/shop/order-det-"+orderMsg.getString("orderCode")+".html");
			//文字内容
			JSONObject data=new JSONObject();
			data.element("first", createKeyNote("您有一笔新的订单,请及时处理", "#173177"));//模板第一行
			data.element("keyword1", createKeyNote(orderMsg.getString("totalPrice")+"元", "#173177"));//第二行
			data.element("keyword2", createKeyNote(orderMsg.getString("name")+"等", "#173177"));//第三行
			data.element("keyword3", createKeyNote(orderMsg.getString("recName")+","+orderMsg.getString("recTel")+","+orderMsg.getString("address"), "#173177"));//第四行
			data.element("keyword4", createKeyNote(orderMsg.getString("createTime"), "#173177"));//第五行
			data.element("keyword5", createKeyNote(orderMsg.getString("orderCode"), "#173177"));//第五行
			
			templat.element("data", data);
			return templat;
		}else
			return null;
	}
	
	/**
	 * 创建模板字段
	 * @param value
	 * @param color
	 * @return
	 */
	private JSONObject createKeyNote(String value,String color){
		JSONObject keynote=new JSONObject();
		keynote.element("value", value);
		keynote.element("color", color);
		return keynote;
	}
}
