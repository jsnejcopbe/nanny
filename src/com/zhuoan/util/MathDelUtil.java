package com.zhuoan.util;

import java.math.BigDecimal;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数字处理工具类
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @change wph
 * @version 0.1
 */
public class MathDelUtil {

	/**
	 * double类型四舍五入运算
	 * @param num
	 * @return
	 */
	public static double halfUp(Double num){
		BigDecimal b =new BigDecimal(num); 
		return b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 获得任意位数随机数
	 */
	public static String getRandomStr(int count)
	{
		String str="";
		for(int i=0;i<count;i++)
		{
			str+=(int)(10*(Math.random()));
		}
		return str;
	}
	
	/**
	 * 获得指定位数的随机码
	 */
	public static String getRandomCode(int count) {
		
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < count; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();
		
	}
	
	/**
	 * 获得两个经纬度坐标间的距离
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double getDistanceOfTwoPos(Double lat1,Double lon1,Double lat2,Double lon2)
	{
		double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lon1) - Math.toRadians(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
	}
	
	/**
	 * JSONArray根据键名进行二分排序
	 * @param arrayData
	 * @param key
	 * @return
	 */
	public static JSONArray arraySort(JSONArray arrayData,String key){
		int d = arrayData.size();
		if(d>1){
			while(true){
				d = d / 2;
				for(int x=0;x<d;x++){
					for(int i=x+d;i<arrayData.size();i=i+d){
						JSONObject temp=arrayData.getJSONObject(i);
						
						
						int j;
						for(j=i-d;j>=0&&arrayData.getJSONObject(j).getLong(key)>temp.getLong(key);j=j-d)
							arrayData.set(j+d, arrayData.getJSONObject(j));
						arrayData.set(j+d,temp);
					}
				}
				if(d == 1)
					break;
			}
		}
		return arrayData;
	}
}
