package com.nanny.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
	/*	List<Object> obj = new ArrayList<Object>();
		obj.add("123");
		Object[] array = obj.toArray();*/
//		String sql = "SELECT  sp.*,spt.name AS typeName FROM shop_product AS sp JOIN (SELECT proCode FROM shop_product )";
//		//SqlUtil util = new SqlUtil(sql,"1","8");
///*		Map<String, String> where = new HashMap<String, String>();
//		where.put("and sp.shopID =", "15");
//		where.put("and sp.typeID =", "1");
//		//util.setG_Oby(" GROUP BY sp.`id`", "sp.`createTime`");
//		util.setWhere(where);
//		util.start();*/
//		String regEx = "(?<=select).*?(?=from)";
//		Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(sql);
//		StringBuffer buffer = new StringBuffer();
//		while(matcher.find()){ 
//		    buffer.append(matcher.group()); 
//		    return;
//		}
//		
//		System.out.println(buffer.toString());
		
		System.out.println(BasisUtil.isDouble("sdads"));
	}
}
