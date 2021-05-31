package com.zhuoan.util.weixin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.zhuoan.util.HttpReqUtil;

import net.sf.json.JSONObject;

/**
 *  微信JS接口调用配置
 * @author home
 *
 */
public class WeiXinJsApiConfigUtil {
	
	private static class Token{
		private final String url = "https://api.weixin.qq.com/cgi-bin/token";
		private static String value = "";
		private static long expires = 0;
		
		public String get(){
			
			if(System.currentTimeMillis()-expires > 0)
				//重新获取token
				return refresh();
			else
				return value;
		}
		
		private String refresh(){
			
			String result = HttpReqUtil.doGet(url, "grant_type=client_credential&appid="+Configure.getAppid()+"&secret="+Configure.appKey, "utf-8");
			
			JSONObject token_json = JSONObject.fromObject(result);
			if(token_json.has("errcode")){
				return "";
			}
			
			value = token_json.getString("access_token");
			expires = System.currentTimeMillis() + token_json.getLong("expires_in")*1000;
			
			return value;
		}
	}
	
	private static class Ticket{
		private final String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		private static String value = "";
		private static long expires = 0;
		
		public String get(Token token){
			if(System.currentTimeMillis()-expires > 0)
				//重新获取token
				return refresh(token);
			else
				return value;
		}
		
		
		private String refresh(Token token){
			
			String result = HttpReqUtil.doGet(url.replace("ACCESS_TOKEN", token.get()), "", "utf-8");
			
			JSONObject ticket_json = JSONObject.fromObject(result);
			if(ticket_json.has("errcode") && !ticket_json.get("errcode").equals(0)){
				return "";
			}
			
			value = ticket_json.getString("ticket");
			expires = System.currentTimeMillis() + ticket_json.getLong("expires_in")*1000;
			
			return value;
		}
	}
	
	public static String get(HttpServletRequest request){
		
		Token token = new Token();
		Ticket ticket = new Ticket();
		String jsapi_ticket = ticket.get(token);
		String nonceStr = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);;
		String url = getRequestURL(request);
		String signature = sign(jsapi_ticket,url,timestamp,nonceStr);
		
//		System.out.println("=========================================================");
//		System.out.println("jsapi_ticket = "+jsapi_ticket);
//		System.out.println("nonceStr = "+nonceStr);
//		System.out.println("timestamp = "+timestamp);
//		System.out.println("url = "+url);
//		System.out.println("signature = "+signature);
//		System.out.println("=========================================================");
		
		Map<String,String> map = new HashMap<String, String>();
		
//		map.put("debug", "true");
		map.put("appId", Configure.getAppid());
		map.put("timestamp", timestamp);
		map.put("nonceStr", nonceStr);
		map.put("signature", signature);
		map.put("jsApiList", 
				"["
				+ "'checkJsApi',"
				+ "'onMenuShareTimeline',"
				+ "'onMenuShareAppMessage',"
				+ "'onMenuShareQQ',"
				+ "'onMenuShareWeibo',"
				+ "'onMenuShareQZone',"
				+ "'hideMenuItems',"
				+ "'showMenuItems',"
				+ "'hideAllNonBaseMenuItem',"
				+ "'showAllNonBaseMenuItem',"
				+ "'translateVoice',"
				+ "'startRecord',"
				+ "'stopRecord',"
				+ "'onVoiceRecordEnd',"
				+ "'playVoice',"
				+ "'onVoicePlayEnd',"
				+ "'pauseVoice',"
				+ "'stopVoice',"
				+ "'uploadVoice',"
				+ "'downloadVoice',"
				+ "'chooseImage',"
				+ "'previewImage',"
				+ "'uploadImage',"
				+ "'downloadImage',"
				+ "'getNetworkType',"
				+ "'openLocation',"
				+ "'getLocation',"
				+ "'hideOptionMenu',"
				+ "'showOptionMenu',"
				+ "'closeWindow',"
				+ "'scanQRCode',"
				+ "'chooseWXPay',"
				+ "'openProductSpecificView',"
				+ "'addCard',"
				+ "'chooseCard',"
				+ "'openCard'"
				+ "]");
		
		JSONObject json = JSONObject.fromObject(map);
		
		System.out.println(json);
		
		return json.toString();
	}
	private static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
	private static String getRequestURL(HttpServletRequest request){
		String url = "http://" + request.getServerName() // 服务器地址
				+ request.getContextPath() // 项目名称
				+ request.getServletPath() // 请求页面或其他地址
				+ (isEmpty(request.getQueryString())?"":("?" + (request.getQueryString())))
				; // 参数

		return url;
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
