package com.zhuoan.util.weixin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.zhuoan.util.HttpReqUtil;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.TimeUtil;

import net.sf.json.JSONObject;

/**
 * 微信红包工具类
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public class WeiXinRedBagUtil {

	/**
	 * 组织微信红包数据
	 * @param param 传入参数
	 * @return
	 */
	public static String createXmlData(JSONObject param){
		Map<String,String> map=new HashMap<String, String>();
		map.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		map.put("mch_billno", Configure.getMchid()+TimeUtil.getNowDate("yyyymmdd")+MathDelUtil.getRandomStr(10));
		map.put("mch_id", Configure.getMchid());
		map.put("wxappid", Configure.getAppid());
		map.put("send_name", Configure.SEND_SHOP_NAME);
		map.put("re_openid", param.getString("re_openid"));
		map.put("total_amount", getMoney(param.getString("total_amount")));
		map.put("total_num", param.getString("total_num"));
		map.put("wishing", param.getString("wishing"));
		map.put("act_name", param.getString("act_name"));
		map.put("remark", param.getString("remark"));
		if(param.containsKey("amt_type"))
			map.put("amt_type", param.getString("amt_type"));
		else
			map.put("client_ip", param.getString("client_ip"));
		map.put("sign",Signature.getSign(map));
		
		//1.组织XMl数据
		String xmlData=XMLParser.getXMLFromMap(map);
		return xmlData;
	}
	
	/**
	 * 请求红包接口并获得操作结果
	 * @param xmlData
	 * @param type 红包类型 0:普通 1:裂变
	 * @return
	 * @throws Exception 
	 */
	public static Map<String,Object> postDataToInterface(String xmlData,int type,HttpServletRequest req) throws Exception
	{
		String sRootPath=req.getSession().getServletContext().getRealPath("");
		String resultXML;
		if(type==0)
			resultXML=HttpReqUtil.doGet(Configure.API_URL_OF_NORMAL, xmlData, "utf-8", sRootPath+"/WEB-INF/cert/apiclient_cert.p12");
		else
			resultXML=HttpReqUtil.doGet(Configure.API_URL_OF_GROUP, xmlData, "utf-8", sRootPath+"/WEB-INF/cert/apiclient_cert.p12");
		
		return XMLParser.getMapFromXML(resultXML);
	}
	
	
	/**
	 * 元转换成分
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}
}
