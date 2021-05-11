package com.zhuoan.util.weixin;

import java.util.HashMap;
import java.util.Map;

import com.zhuoan.util.weixin.Configure;
import com.zhuoan.util.weixin.RandomStringGenerator;
import com.zhuoan.util.weixin.Signature;


public class JsApiPay {

	public static String getJsApiParameters(String prepay_id) {
		
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonceStr = RandomStringGenerator.getRandomStringByLength(32);
		Map<String, String> map = new HashMap<String, String>();
		String packages = "prepay_id="+prepay_id;
		map.put("appId", Configure.getAppid());  
		map.put("timeStamp", timestamp);  
		map.put("nonceStr", nonceStr);  
		map.put("package", packages);  
		map.put("signType", "MD5");
		
		String sign = Signature.getSign(map);
		
		String jsonStr = "{\"appId\":\"" + Configure.getAppid() + "\",\"timeStamp\":\"" + timestamp
				+ "\",\"nonceStr\":\"" + nonceStr + "\",\"package\":\""
				+ packages + "\",\"signType\":\"MD5" + "\",\"paySign\":\""
				+ sign + "\"}";
		
		return jsonStr;
	}
}
