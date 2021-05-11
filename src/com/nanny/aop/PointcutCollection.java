package com.nanny.aop;

import org.aspectj.lang.annotation.Pointcut;


/**
 * AOP类切入点合集
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public class PointcutCollection {

	/**
	 * 订单状态更改(发货)切入点
	 * act:账目/站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.shop.orders.ShopOrdersBizImpl.updateOrderSta(..))")
	protected void shopOrderDel(){}
	
	/**
	 * 订单状态更改(退款)切入点
	 * act:账目/站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.shop.orders.ShopOrdersBizImpl.updateOrderStaForRef(..))")
	protected void shopOrderRefDel(){}
	
	/**
	 * 订单状态更改(货到付款退款)切入点
	 * act:站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.shop.orders.ShopOrdersBizImpl.updateOrderStaForRefOfRecPay(..))")
	protected void shopOrderRefRecDel(){}
	
	
	/**
	 * 用户充值切入点
	 * act:账目/站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.cashrec.UserCashRecBizImpl.updateStaAfterCharge(..))")
	protected void userChargeDel(){}
	
	/**
	 * 用户提现切入点
	 * act:账目/站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.cashrec.UserTransferBizImpl.addNewTransferApp(..))")
	protected void userTransferDel(){}
	
	/**
	 * 用户提现退款切入点
	 * act:账目/站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.admin.AdminTransBizImpl.updateTablesForRefuse(..))")
	protected void userTransferBackDel(){}

	/**
	 * 商户每日订单返现切入点
	 */
	@Pointcut(" execution(* com.nanny.biz.impl.shop.ShopAccountBizImpl.updateUserBalForRepay(..))")
	protected void shopOrderRepayDel(){}

/******************************************************hxq**********************************************************/	
	
	/**
	 * 用户支付操作
	 * act:账目/站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.user.UsersOrderBizImpl.updateUserorder(..))")
	protected void updateUserorderDel(){}
	
	/**
	 * 用户退款申请
	 * act:站内信
	 */
	@Pointcut("execution(* com.nanny.biz.impl.user.UsersOrderBizImpl.ordeRefund(..))")
	protected void ordeRefundDel(){}
	
	/**
	 * 用户确认订单(在线付款/货到付款)
	 * act:站内信(微信消息)
	 */
	@Pointcut("(execution(* com.nanny.biz.impl.user.UsersOrderBizImpl.updateUserorder(..)))  || (execution(* com.nanny.biz.impl.user.UsersOrderBizImpl.cod(..)))")
	protected void orderConfirmDel(){}
	
	
	/**
	 * 用户积分兑换(在线付款/货到付款)
	 * act:站内信(微信消息)
	 */
	@Pointcut("execution(* com.nanny.biz.impl.user.UsersOrderBizImpl.updateUserPointForExc(..))")
	protected void orderPointDel(){}
	
	/**
	 * 用户积分来源(订单完成返分/签到)
	 * act:站内信(微信消息)
	 */
	@Pointcut("(execution(* com.nanny.biz.impl.user.UsersOrderBizImpl.updateShoporder(..)))  || (execution(* com.nanny.biz.impl.user.UsersBizImpl.updateusersign(..)))")
	protected void userPointDel(){}
	
	/**
	 * 用户积分退还，删除积分记录(订单退款还分/货到付款)
	 * act:站内信(微信消息)
	 */
	@Pointcut("(execution(* com.nanny.biz.impl.shop.orders.ShopOrdersBizImpl.updateOrderStaForRef(..)))  || (execution(* com.nanny.biz.impl.shop.orders.ShopOrdersBizImpl.updateOrderStaForRefOfRecPay(..)))")
	protected void userPointBackDel(){}
	
	
	/**
	 * 赠送用户抵用卷
	 * act:站内信(微信消息)
	 */
	@Pointcut("(execution(* com.nanny.biz.impl.login.RegisterBizImpl.addNewUser(..)))")
	protected void userVoucherAddDel(){}
	
	
}
