package com.nanny.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author syl
 */
public class SqlUtil {
	private String base_sql;

	private Map<String, String> where;
	private String groupSql;
	private String order;
	private String orderType;
	
	private String now_page;
	private String row;
	
	private String total_sql;
	private String full_sql;
	private	Object object[];

	/** 拼接sql的工具类 
	 * @param base_sql 基础的sql 例: select * from xxx 不包含where及之后的语句 
	 *  分页参数
	 * @param now_page 现在第几页
	 * @param row 一页显示几行
	 */
	public SqlUtil(String base_sql,String now_page,String row){
		this.base_sql = base_sql;
		this.now_page = now_page;
		this.row = row;
	}
	
	/**
	 * @param base_sql 基础的sql 例: select * from xxx 不包含where后的语句 
	 */
	public SqlUtil(String base_sql){
		this.base_sql = base_sql;
	}

	 
	/** 开始拼接
	 * @param type 0 存在sql注入的sql 1 用?占位的sql;
	 * @return
	 */
	public SqlUtil start(){
		StringBuffer sb = where == null ? new StringBuffer(base_sql) : new StringBuffer(base_sql+" ");
		if(where == null){
			this.total_sql = count(sb.toString());
			this.full_sql = limit(sb);
			return this;
		}
		Set<String> keySet = where.keySet();
		Iterator<String> iterator = keySet.iterator();
		int i=0;
		List<Object> list = new ArrayList<Object>();
		while (iterator.hasNext()) {
			String key = iterator.next();
			boolean b1 = key.indexOf("and") <0 && key.indexOf("AND") <0;
			boolean b2 = key.indexOf("or") <0 && key.indexOf("OR") <0;
			
			if(b1 && b2)throw new IllegalAccessError("where的第"+i+"个键值对中请包含关键字 and 或 or");
			boolean b3 = key.indexOf("like") <= 0 && key.indexOf("LIKE") <= 0;
			if(key.indexOf("=") <= 0 && b3)throw new IllegalAccessError("where的第"+i+"个键值对中请包含关键字 = 或 like");
			list.add(where.get(key));
			String temp = where.get(key);
				   temp = !b3 ?"%"+temp+"%":temp;
				   temp = !BasisUtil.isNumber1(temp)?"'"+temp+"'":temp;
		    boolean kuohao = temp.indexOf(")")>=0;
				   temp = kuohao ? temp.replace(")",""):temp;
			sb.append(key+" ");
			sb.append(temp+" ");
			if(kuohao)sb.append(" )");
			i = i + 1;
		}
		this.total_sql = count(sb.toString());
		if(this.groupSql !=null && !this.groupSql.trim().isEmpty())sb.append(" "+this.groupSql+" ");
		
		this.full_sql = limit(sb);
		this.object = list.toArray();
		return this;
	}
	
	//计算总数 
	private String count(String sql){
		String regEx = "(?<=select).*?(?=from)";
		Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		StringBuffer buffer = new StringBuffer();
		while(matcher.find()){
		    buffer.append(matcher.group());
		    break;
		}
		return sql.replace(buffer.toString(), " count(*) ");
	}
	
	//分页
	private String limit(StringBuffer sb){
		
		if(this.order !=null && !this.order.trim().isEmpty()){
			String temp = this.orderType == null?"desc":this.orderType;
			sb.append(" order by "+order+" "+temp);
		}
		
		if(this.now_page == null || this.row == null)return sb.toString();
		
		long pg = Long.valueOf(this.now_page);
		long rw = Long.valueOf(this.row);
		//排序
		if(pg >0 && rw !=0){
			long temp = (pg-1)*rw;
			sb.append(" LIMIT "+temp+","+rw);
		}
		return sb.toString();
	}
	
	/**
	 * @return 获取总数的sql
	 */
	public String getTotal_sql() {
		return total_sql;
	}

	/**
	 * @return 拼接后的所有sql
	 */
	public String getFull_sql() {
		return full_sql;
	}
	
	public Object getObject() {
		return object;
	}

	/**
	 * @param where 对应where的语句
	 */
	public void setWhere(Map<String, String> where) {
		this.where = where;
	}
	
	/** 获取指定的Map
	 * @return 
	 */
	public Map<String, String> getWhere(){
		return new LinkedHashMap<String, String>();
	}

	/**
	 * @param groupSql 对应 group by 的sql
	 * @param order 要order by 的参数
	 */
	public void setG_Oby(String groupSql,String order,String orderType) {
		this.groupSql = groupSql;
		this.order = order;
		this.orderType = orderType;
	}
	
}
