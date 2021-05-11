package com.nanny.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author syl
 *
 */
public class JsonUtil {

	/** 
	 * @param obj
	 * @return 将对象转换为json
	 */
	public static String getJson(Object obj){
		if(obj == null) throw new IllegalAccessError("不能为空");
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	/** 
	 * @param total 
	 * @param datalist
	 * @return 特定的json 格式  含有total,rows键值
	 */
	public static String getJson(String total,Object datalist){
		Map<String, Object> map = new HashMap<String, Object>();
		if(total != null && !total.trim().isEmpty())map.put("total", total);
		map.put("rows", datalist);
		return getJson(map);
	}
	
	/**
	 * @param json 
	 * @return List《String》
	 */
	public static List<String> getList(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
	}
	
	/**获取 
	 * @param json 
	 * @return Map 《String,String》
	 */
	public static Map<String,String> getMapStr(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<Map<String,String>>(){}.getType());
	}
	
	/** 获取
	 * @param json
	 * @return Map《String,Object》 
	 */
	public static Map<String,Object> getMapObj(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<Map<String,Object>>(){}.getType());
	}
	
	/** 可能顺序不一
	 * @param json 获取
	 * @return List《Map《String,Object》》 
	 */
	public static List<Map<String,Object>> getListMaps(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<Map<String,Object>>>(){}.getType());
	} 
	
	/** 
	 * @param json
	 * @param t1 指定的反射bean 
	 * @return 对象
	 */
	public static Object getBean(String json,Class<?> t1){
		Gson gson = new Gson();
		return gson.fromJson(json, t1);
	}
	
	/**
	 * 把json中的数据映射到 两个JavaBean中的一个
	 * 
	 * @param json json数据
	 * @param t1 类1
	 * @param t2 类2
	 * @return 返回一个不为空的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> Object getRightBean(String json,Class<?> t1,Class<?> t2){
		Gson gson = new Gson();
		T obj1 = null;
		T obj2 = null;
		try {
			obj1 = (T) gson.fromJson(json, t1);
		} catch (Exception e) {
			try {
				obj2 =  (T) gson.fromJson(json, t2);
			} catch (Exception e2) {
				throw new IllegalAccessError("传入的JSON格式有误");
			}
		}
		if(obj1 == null)return obj2;
		return obj1;
	}
 
}
