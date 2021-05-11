package com.nanny.aop;

import java.sql.Timestamp;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.nanny.biz.global.aopdel.GlobalCashRecBiz;
import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.biz.user.UsersOrderBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseOrders;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserAccountsRec;
import com.nanny.model.UserCashApply;
import com.nanny.model.UserPointRedeem;
import com.nanny.model.UserSign;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

/**
 * 平台现金账目AOP类
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Aspect
@Component
public class GloCashRecAop extends PointcutCollection {
	
	//载入资源
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_ShopBaseMsgBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopMsgBiz;
	@Resource(name="globalCashRecBiz")
	private GlobalCashRecBiz g_CashRecBiz;
	@Resource
	private UsersOrderBiz userOrderBiz;	
	@Resource
	private SSHUtilBiz ssb;
	
	/**
	 * 商户退款记录
	 * @param point
	 */
	@After("shopOrderRefDel()")
	public void shopOrderDelAop(JoinPoint point){
		//0.获得传入参数
		JSONObject param=(JSONObject)point.getArgs()[0];
		Long shopID=param.getLong("shopID");//发起商户ID
		Long userID=param.getLong("userID");//接收用户ID
		Double cost=param.getDouble("cost");//扣款金额
		JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(shopID);//获得商户基础信息
		
		//1.组织账目数据
		UserAccountsRec bean=new UserAccountsRec();
		bean.setBalance(g_ShopBaseMsgBiz.getShopBalanceByShopID(shopID));
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setDes("商户退款操作");
//		bean.setMoney(Double.valueOf("-"+cost.toString()));
		bean.setMoney(cost);
		bean.setOrderCode(creSerial());
		bean.setOtherSide(userID);
		bean.setPayType(0);
		bean.setType(Dto.CASH_OF_REFUND);
		bean.setUserId(shopMsg.getLong("userID"));
		
		//2.添加账目
		g_CashRecBiz.addCashRec(bean);
	}
	
	/**
	 * 用户充值记录
	 * @param point
	 */
	@After("userChargeDel()")
	public void userChargeDelAop(JoinPoint point){
		//0.获得传入参数
		JSONObject param=(JSONObject)point.getArgs()[0];
		
		//1.组织账目数据
		UserAccountsRec bean=new UserAccountsRec();
		bean.setBalance(param.getDouble("nowBalance"));
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setDes("用户充值");
		bean.setMoney(param.getDouble("money"));
		bean.setOrderCode(creSerial());
		bean.setOtherSide(param.getLong("userID"));
		bean.setPayType(0);
		bean.setType(Dto.CASH_OF_CHARGE);
		bean.setUserId((long)0);
		
		//2.添加账目
		g_CashRecBiz.addCashRec(bean);
	}
	
	/**
	 * 用户订单支付记录
	 * @param point
	 */
	@After("updateUserorderDel()")
	public void updateUserorderDelAop(JoinPoint point){
		Dto.writeLog("into payRec method getJoinPoint is"+point.toString());
		
		//0.获得传入参数
		JSONArray orderIDList=(JSONArray)point.getArgs()[0];//订单ID合集
		Long userID=(Long)point.getArgs()[1];
		
		//1.组织账目数据
		for(int i=0;i<orderIDList.size();i++)
		{
			Long orderID=orderIDList.getLong(i);
			JSONObject payData=g_ShopBaseMsgBiz.getShopBizByOrderID(orderID);//获得订单下的账目数据
			
			Dto.writeLog("start insert rec into acc_rec get order msg is:"+payData.toString());
			
			UserAccountsRec bean=new UserAccountsRec();
			bean.setBalance(payData.getDouble("balance"));
			bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
			bean.setDes("用户订单支付，订单号："+payData.getString("orderCode"));
			bean.setMoney(payData.getDouble("totalPrice"));
			bean.setOrderCode(creSerial());
			bean.setOtherSide(payData.getLong("userID"));
			bean.setPayType(0);
			bean.setType(Dto.CASH_OF_PAYMENT);
			bean.setUserId(userID);
			
			//2.添加账目
			g_CashRecBiz.addCashRec(bean);
		}
		
		Dto.writeLog("end payRec method");
	}
	
	/**
	 * 用户提现支付记录
	 * @param point
	 */
	@After("userTransferDel()")
	public void userTransferDelAop(JoinPoint point){
		//0.获得传入参数
		UserCashApply cashBean=(UserCashApply)point.getArgs()[0];//传入参数
		Object shopID=point.getArgs()[1];
		
		//1.组织用户账目数据
		UserAccountsRec bean=new UserAccountsRec();
		bean.setBalance(Double.valueOf(cashBean.getMemo()));
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setDes("用户提现");
		bean.setMoney(cashBean.getMoney());
		bean.setOrderCode(creSerial());
		bean.setOtherSide((long)0);
		bean.setPayType(0);
		bean.setType(Dto.CASH_OF_WITHDRAW);
		if(shopID==null)
			bean.setUserId(cashBean.getUserId());
		else{
			JSONObject shopMsg=g_ShopMsgBiz.getShopMsg((Long) shopID);
			bean.setUserId(shopMsg.getLong("userID"));
		}
		
		//2.添加账目
		g_CashRecBiz.addCashRec(bean);
	}
	
	/**
	 * 用户提现退款支付记录
	 * @param point
	 */
	@After("userTransferBackDel()")
	public void userTransferBackDelAop(JoinPoint point){
		//0.获得传入参数
		JSONObject param=(JSONObject)point.getArgs()[0];//传入参数
		
		//1.组织用户账目数据
		UserAccountsRec bean=new UserAccountsRec();
		bean.setBalance(param.getDouble("balance"));
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setDes("管理员拒绝用户提现申请");
		bean.setMoney(param.getDouble("money"));
		bean.setOrderCode(creSerial());
		bean.setUserId((long)0);
		bean.setPayType(0);
		bean.setType(Dto.CASH_OF_TRANSFERREFUND);
		if(param.containsKey("userID"))
			bean.setOtherSide(param.getLong("userID"));
		else{
			JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(param.getLong("shopID"));
			bean.setOtherSide(shopMsg.getLong("userID"));
		}
		
		//2.添加账目
		g_CashRecBiz.addCashRec(bean);
	}
	
	/**
	 * 商户订单返现记录
	 * @param point
	 */
	@After("shopOrderRepayDel()")
	public void shopOrderRepayDelAop(JoinPoint point){
		//0.获得传入参数
		JSONArray param=(JSONArray)point.getArgs()[0];//传入参数
		
		//1.组织用户账目数据
		for(int i=0;i<param.size();i++)
		{
			JSONObject tmpObj=param.getJSONObject(i);
			UserAccountsRec bean=new UserAccountsRec();
			bean.setBalance(tmpObj.getDouble("nowBalance"));
			bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
			bean.setDes("店铺消费每日首单返现");
			bean.setMoney(tmpObj.getDouble("addMoney"));
			bean.setOrderCode(creSerial());
			bean.setOtherSide(tmpObj.getLong("userID"));
			bean.setPayType(0);
			bean.setType(Dto.CASH_OF_BACKPAY);
			bean.setUserId((long)0);
			
			//2.添加账目
			g_CashRecBiz.addCashRec(bean);
		}
	}
	
	
	/**
	 * 用户订单支付积分记录
	 * @param point
	 */
	@After("orderPointDel()")
	public void orderPointDelAop(JoinPoint point){
//		//0.获得传入参数
//		JSONArray orderIDList=(JSONArray)point.getArgs()[0];//订单ID合集
//		int zpoint=(Integer) point.getArgs()[1];
//		double TotalPrice=(Double) point.getArgs()[2];
//		long userID=(Long) point.getArgs()[1];
//		
//		//1.组织账目数据
//		for(int i=0;i<orderIDList.size();i++)
//		{
//			Long orderID=orderIDList.getLong(i);
//			JSONObject payData=g_ShopBaseMsgBiz.getShopBizByOrderID(orderID);//获得订单下的账目数据
//			
//			//循环判断是否有积分商品
//			JSONObject obj=userOrderBiz.PointGoods(orderID);
//				
//			JSONArray data=obj.getJSONArray("expro");
//			
//			//订单中的积分商品ID
//			for(int j=0;j<data.size();j++){
//				//不是积分商品不存
//				if(obj.getInt("gpoint")!=0){
//					UserPointRedeem upr=new UserPointRedeem();
//					upr.setCreateTime(DateUtils.getTimestamp());
//					upr.setUserId(userID);
//					upr.setShopId(payData.getLong("shopID"));
//					upr.setProId(data.getJSONObject(j).getLong("proID"));
//					upr.setOrderId(orderID);
//					upr.setStatus(Dto.USER_DEDUCT);
//					upr.setPoint(data.getJSONObject(j).getInt("point"));
//					upr.setDes("用户积分兑换商品,订单编号:"+payData.getLong("orderCode"));
//					g_CashRecBiz.addPointRec(upr);
//				}
//			}
//			
//		}
		UserPointRedeem bean= (UserPointRedeem) point.getArgs()[0];
		g_CashRecBiz.addPointRec(bean);
	}
	
	/**
	 * 用户积分获取记录
	 * @param point
	 */
	@After("userPointDel()")
	public void userPointDelAop(JoinPoint point){
		//0.获得传入参数
		Object  type=  point.getArgs()[0];
		long userID=(Long) point.getArgs()[1];
		
		String type1=type.getClass().toString();
		int type2 =type1.indexOf("Long");
		
		if(type2==-1){
			UserSign us=(UserSign) type;
			
			//1.组织账目数据				
			UserPointRedeem upr=new UserPointRedeem();
			upr.setCreateTime(DateUtils.getTimestamp());
			upr.setUserId(userID);
			//upr.setShopId(payData.getLong("shopID"));
			//upr.setProId(proId);
			//upr.setOrderId(orderID);
			upr.setStatus(Dto.USER_INCREASE);
			upr.setPoint(us.getScore());
			upr.setDes("用户签到积分");
			g_CashRecBiz.addPointRec(upr);
			
			
		}else{
			long orid=(Long) type;
			BaseOrders bo=(BaseOrders) ssb.getObjectById(BaseOrders.class, orid);
			
			 int poi= userOrderBiz.opoint(bo.getTotalPrice());
			 if(poi>0){
				//1.组织账目数据				
				UserPointRedeem upr=new UserPointRedeem();
				upr.setCreateTime(DateUtils.getTimestamp());
				upr.setUserId(userID);
				//upr.setShopId(payData.getLong("shopID"));
				//upr.setProId(proId);
				upr.setOrderId(orid);
				upr.setStatus(Dto.USER_INCREASE);
				upr.setPoint(poi);
				upr.setDes("用户订单返分");
				g_CashRecBiz.addPointRec(upr);
			}
		}
			
}
	
	
	/**
	 * 用户积分退还and 删除记录
	 * @param point
	 */
	@After("userPointBackDel()")
	public void userPointBackDel(JoinPoint point){
		
		JSONObject upbd=(JSONObject) point.getArgs()[0];
		g_CashRecBiz.delPointRec(upbd);
		
		
	}
	
	
	/**
	 * 用户抵用卷
	 * @param point
	 */
	@After("userVoucherAddDel()")
	public void userVoucherAddDel(JoinPoint point){
		BaseUsers bu=(BaseUsers) point.getArgs()[0];
		g_CashRecBiz.addUserVoucher(bu);
		
		
	}
	
	
/*******************************************************************************************************************************/	
	
	/**
	 * 创建流水号
	 * @return
	 */
	private String creSerial(){
		return "CA"+TimeUtil.getNowDate("yyyyMMddHHmmssSS");
	}
}
