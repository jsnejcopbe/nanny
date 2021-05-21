package com.zhuoan.util.weixin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.zhuoan.util.HttpReqUtil;

import net.sf.json.JSONObject;

/**
 *  微信JS接口调用配置
 * @author home
 *
 */
public class WeiXinJsConfigUtil {
	
	private static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static String access_token = "";
	private static long token_expires = 0;
	
	private static final String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	private static String ticket = "";
//	private static long ticket_expires = 0;
	
	public static String getAccessToken(){
		
		if(System.currentTimeMillis()-token_expires > 0)
			//重新获取token
			return refresh();
		else
			return access_token;
		
	}
	
	private static String refresh(){
		
		String result = HttpReqUtil.doGet(access_token_url, "grant_type=client_credential&appid="+Configure.getKey()+"&secret="+Configure.getAppSecret(), "utf-8");
		
		JSONObject token_json = JSONObject.fromObject(result);
		if(token_json.has("errcode")){
			return "";
		}
		
		access_token = token_json.getString("access_token");
		token_expires = System.currentTimeMillis() + token_json.getLong("expires_in")*1000;
		
		result = HttpReqUtil.doGet(ticket_url, "access_token="+access_token+"&type=jsapi", "utf-8");
		JSONObject ticket_json = JSONObject.fromObject(result);
		if(ticket_json.get("errcode").equals("0")){
			ticket = ticket_json.getString("ticket");
		}
		
		
		return access_token;
	}
	
	/**
	 * 
	 * @param request
	 */
	public static void apply(HttpServletRequest request){
		
		String timestamp = String.valueOf(System.currentTimeMillis());
		String nonceStr = UUID.randomUUID().toString();
		
		request.setAttribute("appId", Configure.getAppid());
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("nonceStr", nonceStr);
		request.setAttribute("signature", sign(ticket,getRequestUrl(),timestamp,nonceStr));
		
	}
	
	private static String getRequestUrl(){
		return "";
	}
	
	
	private static String sign(String jsapi_ticket, String url,String timestamp,String nonceStr) {
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonceStr +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }


        return signature;
    }
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
