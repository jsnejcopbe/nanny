package com.nanny.intercepter.store;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.global.cus.CusReceiveAddBiz;
import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.biz.global.store.ShopSettingBiz;
import com.nanny.biz.shop.ShopDispatchingBiz;
import com.nanny.biz.user.UserCollectBiz;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;
import com.zhuoan.util.TimeUtil;

@Component("shopOfficeHoursDelFun")
public class ShopOfficeHoursDelFunImpl implements CommonDelFun {

	//载入资源
	@Resource(name="shopSettingBiz")
	private ShopSettingBiz g_SettingBiz;
	@Resource(name="cusReceiveAddBiz")
	private CusReceiveAddBiz g_CusAddBiz;
	@Resource(name="shopMsgBiz")
	private ShopMsgBiz g_ShopMsgBiz;
	@Resource(name="shopDispatchingBiz")
	private ShopDispatchingBiz g_DispatchBiz;
	@Resource(name="usercollectBiz")
	private UserCollectBiz g_UserCollBiz;
	/**
	 * 跳转前判断店铺是否在营业时间
	 */
	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
		Long shopID=null;
		if(mav.getModel().get("shopID")!=null)
		{
			shopID=(Long) mav.getModel().get("shopID");
			//0.获得营业时间列表
			JSONArray hoursList=g_SettingBiz.getShopOfficeHours(shopID);
			
			JSONObject shopMsg=g_ShopMsgBiz.getShopMsg(shopID);
			
			//1.判断当前时间是否在营业时间范围内
			String nowTime=TimeUtil.getNowDate("yyyy-MM-dd HH:mm");
			boolean isClosed=false;
			
			for(int i=0;i<hoursList.size();i++)
			{
				JSONObject tmp=hoursList.getJSONObject(i);
				isClosed= isClosed || isInTime(nowTime, tmp);
			}
			
			if(!isClosed || shopMsg.getInt("status")==Dto.SHOP_STOPPING)
				mav.addObject("isClosed", "true");
			
		}
		
		//2.查询用户地址，且配送价格为多少
		if(request.getSession().getAttribute(Dto.SESSION_USER_AREA)!=null)
		{
			Long addID=(Long) request.getSession().getAttribute(Dto.SESSION_USER_AREA);
			JSONObject areaMsg=g_CusAddBiz.getAddDetByAddID(addID);
			mav.addObject("areaMsg", areaMsg);
			
			if(shopID!=null)
			{
				JSONObject dispatchMsg=g_DispatchBiz.getShopDispatchByAddID(shopID, addID);
				if(dispatchMsg!=null)
					mav.addObject("dispatchMsg", dispatchMsg);
				else
					mav.addObject("isOutArea", "true");
			}
		}else
			mav.addObject("isOutArea", "true");
		//g_UserCollBiz
		//3.查询用户收藏信息
		if(request.getSession().getAttribute(Dto.LOGIN_USER)!=null && shopID!=null)
		{
			JSONObject userMsg=(JSONObject) request.getSession().getAttribute(Dto.LOGIN_USER);
			JSONObject collectMsg=g_UserCollBiz.cheUserCollectByuserID(userMsg.getLong("userID"), shopID);
			if(collectMsg!=null)
				mav.addObject("isCollected", "true");
		}
	}

	public boolean blReturnDelFu(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		return false;
	}

	/**
	 * 判断时间是否在范围内
	 * @param nowTime
	 * @param timeObj
	 * @return
	 */
	private boolean isInTime(String nowTime,JSONObject timeObj)
	{
		String nowMonth=TimeUtil.getNowDate("yyyy-MM-dd");
		String startTime=nowMonth+" "+timeObj.getString("startTime").replaceAll(" ", "");
		String endTime=nowMonth+" "+timeObj.getString("endTime").replaceAll(" ", "");
		
		boolean boolean1=TimeUtil.isLatter(nowTime+":00", startTime+":00");
		boolean boolean2=TimeUtil.isLatter(nowTime+":00", endTime+":00");
		
		if(boolean1==true && boolean2==false)
			return true;
		else
			return false;
	}
}
