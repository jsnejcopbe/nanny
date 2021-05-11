package com.nanny.intercepter;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * 拦截器处理方法工厂
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("delFunFactory")
public class DelFunFactory {
	//加载资源
	@Resource(name="userLoginStaCeckDelFun")
	private CommonDelFun g_UserStaCheckDelFun;
	@Resource(name="shopOfficeHoursDelFun")
	private CommonDelFun g_ShopOfficeHoursDelFun;
	@Resource(name="userLoginCookDelFun")
	private CommonDelFun g_UserLoginCookeDelFun;
	@Resource(name="shopRefundDelFun")
	private CommonDelFun g_ShopRefundDelFun;
	@Resource(name="weiXinLoginDelFun")
	private CommonDelFun g_WeiXinLoginDelFun;
	@Resource(name="userTransferAppDelFun")
	private CommonDelFun g_UserTransferAppDelFun;
	@Resource(name="shopOrderCountDelFun")
	private CommonDelFun g_ShopOrderCountDelFun;
	@Resource(name="userPointCheckDelFun")
	private CommonDelFun g_UserPointCheckDelFun;
	@Resource(name="preventRepActDelFun")
	private CommonDelFun preventRepActDelFun;
	
	
	public CommonDelFun getFunction(String name){
			
		if(("common").equals(name))
			return g_UserStaCheckDelFun;
		else if(("storepage").equals(name))
			return g_ShopOfficeHoursDelFun;
		else if(name!=null&&name.indexOf("store")!=-1)
			return g_UserLoginCookeDelFun;
		else if("supermarket".equals(name))
			return g_UserLoginCookeDelFun;
		else if("useraddress".equals(name))
			return g_UserLoginCookeDelFun;
		else if("shopcar".equals(name))
			return g_UserLoginCookeDelFun;
		else if("orderrefund".equals(name))
			return g_ShopRefundDelFun;
		else if("weixinlogin".equals(name))
			return g_WeiXinLoginDelFun;
		else if("addtransferapp".equals(name))
			return g_UserTransferAppDelFun;
		else if("orderCount".equals(name))
			return g_ShopOrderCountDelFun;
		else if("shoplist".equals(name))
			return g_UserLoginCookeDelFun;
		else if("pointexchange".equals(name))
			return g_UserPointCheckDelFun;
		else if("addUseRess".equals(name))
			return g_UserLoginCookeDelFun;
		else if("userIndex".equals(name))
			return g_UserLoginCookeDelFun;
		else if(("checkRep").equals(name))
			return preventRepActDelFun;
		else	
			return null;
		
	}
	
	
	
}
