package com.nanny.task;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.zhuoan.util.TimeUtil;

/**
 * 系统定时任务
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component
public class GlobalTimerTask {

	//载入资源
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderBiz;
	@Resource
	private ShopAccountBiz g_AccBiz;

 /** 
  * 定时更新系统订单状态
  * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下） 
  * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT) 
  */ 
	
	@Scheduled(cron="0 * */2 * * *")
//	@Scheduled(cron="0 46 10 * * ? ")
	public void updateAllOrderSta(){
		//0.获得本次检查的下限时间
		String nowTime=TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss");//当前日期
		String timeBeforeAweek=TimeUtil.addHoursBaseOnNowTime(nowTime, -2, "yyyy-MM-dd HH:mm:ss");
		
		//1.更新全部超时订单
		g_OrderBiz.updateOrderStaForOverTime(timeBeforeAweek);
	}
	
	/**
	 * 当天订单检查(12点)
	 */
	@Scheduled(cron="0 0 0 * * *")
	public void dayOrderCheck(){
		//0.取消全部未付款订单
		g_OrderBiz.updateOrderStaForNoPay();
		
		//1.每日订单返现计算
		String end=TimeUtil.getNowDate("yyyy-MM-dd")+" 00:00:00";
		String star=TimeUtil.addDaysBaseOnNowTime(end, -1, "yyyy-MM-dd")+" 00:00:00";
		
		//2.更新商户余额
		JSONArray data=g_OrderBiz.getTodayOrderedShopList(star, end);
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpObj=data.getJSONObject(i);
			int count=g_OrderBiz.getTodyFirstOrderCount(tmpObj.getLong("shopID"), star, end);
			tmpObj.element("backMoney", count);
		}
		g_AccBiz.updateUserBalForRepay(data);
	}
}
