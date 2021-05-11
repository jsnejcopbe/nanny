package com.nanny.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author syl
 *
 */
public class BasisUtil {
	/**
	 * MD5加密的工具类
	 * @param str
	 * @return
	 */
	public static final String toMD5(String str) {
		char digits[] = {'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f' };

		char strChar[] = new char[32];   
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] b = md.digest();

			int k = 0;

			for (int i = 0; i < 16; i++) {

				strChar[k++] = digits[b[i] >>> 4 & 0xf];
				strChar[k++] = digits[b[i] & 0xf];
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(strChar).toLowerCase();
	}

	/** 获取0-9范围的随机字符串
	 * @param size 要获得的长度个数
	 * @return
	 */
	public static final String getRandomStr(int size){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<size;i++){
			int nextInt = new Random().nextInt(10);
			sb.append(nextInt);
		}
		return sb.toString();
	}
	
	/** 把Timestamp的时间变正常 
	 * @param obj
	 * @return
	 */
	public static final String Time_Format(Object obj){
		if(obj == null)return "";
		String time = obj.toString();
		return time.substring(0, time.length()-2);
	}
	
	/** 把Timestamp的时间变正常 
	 * @param obj
	 * @return
	 */
	public static final String Time_Format(Map<String, String> map){
		Object o = map.get("createTime");
		if(o == null)return "";
		String time = o.toString();
		return time.substring(0, time.length()-2);
	}
	
	/** sql 合并
	 * @param sql 普通sql 除了 where 的语句
	 * @param where 
	 * @param type 0 and 1 or 
	 * @return
	 */
	public static final StringBuffer SqlMerge(String sql,Map<String, String> where,int type[]) {
		StringBuffer sb = where == null ? new StringBuffer(sql) : new StringBuffer(sql+" where 1=1 ");
		if(where == null)return sb;
		Set<String> keySet = where.keySet();
		Iterator<String> iterator = keySet.iterator();
		if(type == null || type.length == 0)return sb;
		int i=0;
		while (iterator.hasNext()) {
			String key = iterator.next();
			boolean b = key.indexOf("like") <= 0 && key.indexOf("LIKE") <= 0;
			if(key.indexOf("=") <= 0 && b)throw new IllegalAccessError("键值对中请包含关键字 = 或 like");
			String temp = where.get(key);
				   temp = !b ?"%"+temp+"%":temp;
				   temp = !BasisUtil.isNumber1(temp)?"'"+temp+"'":temp;
			sb.append(type[i] == 0 ? " and " :" or ");
			sb.append(key+" ");
			sb.append(temp+" ");
			i = i + 1;
		}
		return sb;
	}
	
	/** sql 拼接
	 * @param column 选择的列，默认*
	 * @param from 
	 * @param where 
	 * @param type 0 or  1 and
	 * @return 
	 */
	public static final StringBuffer SqlSplice(String column[],String from,Map<String, String> where,int type) {
		StringBuffer sb = new StringBuffer("select ");
		if(column == null)sb.append(" * ");
		else {
			for(int i=0;i<column.length;i++){
				if(i != column.length-1)sb.append(column[i]+",");
				else sb.append(column[i]+" ");
			}
		}
		sb.append("from "+from);
		if(where == null)return sb;
		sb.append(" where 1=1 ");
		Set<String> keySet = where.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if(key.indexOf("=") <= 0 && key.indexOf("like") <= 0)throw new IllegalAccessError("键值对中请包含关键字 = 或 like");
			String temp = where.get(key);
			temp = !BasisUtil.isNumber1(temp)?"'"+temp+"'":temp;
			sb.append(key+" ");
			sb.append(temp+" ");
			if(iterator.hasNext()){
				sb.append(type == 0 ? "or " : "and ");
			}
		}
		return sb;
	}

	/** 配合表格控件 生成带有规定键值对的map
	 * @param total 总数
	 * @param datalist 数据
	 * @return
	 */
	public static final Map<String, Object> myTableHelp(String total,Object datalist){
		Map<String, Object> map = new HashMap<String, Object>();
		if(total != null && !total.trim().isEmpty())map.put("total", total);
		map.put("rows", datalist);
		return map;
	}
	
	/** 配合sql拼接后进行 分组,排列,分页
	 * @param sb 由SqlSplice返回的 StringBuffer
	 * @param group 分组的列
	 * @param order 排列的列
	 * @param page 当前第几页
	 * @param row 每页显示几行
	 * @return 最终的sql
	 */
	public static final String getSql(StringBuffer sb,String group[],String order,String orderType,String page,String row){
		if(group != null){
			int size = group.length;
			for(int i=0;i<size;i++){
				if(i != size-1)sb.append(" group by "+group[i]+",");
				sb.append(" group by "+group[i]);
			}
		}
		if(order !=null && !order.trim().isEmpty()) sb.append(" order by "+order+" "+orderType);
		if(page ==null || row ==null)return sb.toString();
		long pg = Long.valueOf(page);
		long rw = Long.valueOf(row);
		if(pg >0 && rw !=0){
			long temp = (pg-1)*rw;
			long total = pg*rw;
			sb.append(" LIMIT "+temp+","+rw);
		}
		return sb.toString();
	}
	
	/**
	 * 文件保存的工具类
	 * @param request
	 * @param name name[0] 文件夹 可空  name[1] 文件名
	 * @param i 0 关闭防止重复  1 开启防止重复
	 * @return 文件
	 */
	public static final File filesave(HttpServletRequest request,String folder_name,String name[],int i){
		StringBuffer sb = new StringBuffer();
		sb.append(request.getServletContext().getRealPath("/upload"));
		sb.append("/");
		sb.append(folder_name);
		String path = "".equals(name[0]) ? sb.toString(): sb.append("\\").append(name[0]).toString();
		/**
		 * 防止重复可改
		 */
		String fileName = new Date().getTime()+"_"+name[1].substring(1, name[1].length());
		fileName = BasisUtil.chinaToUnicode(fileName);
		File targetFile = new File(path, fileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//保存
		try {
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return targetFile;
	}

	/** 
	 * 把中文转成Unicode码 
	 * @param str 
	 * @return 
	 */  
	public static final String chinaToUnicode(String str){
		String result="";  
		for (int i = 0; i < str.length(); i++){  
			int chr1 = (char) str.charAt(i);
			if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
				result+="-" + Integer.toHexString(chr1); 
			}else{  
				result+=str.charAt(i);  
			}  
		}  
		return result;  
	}

	/**
	 * 分割List （分页实现）
	 * @param list 要分割的list  
	 * @param pageSize 分割后每个list几条记录
	 * @return List[]
	 */
	public static final List[] splitList(List list, int pageSize) {
		int total = list.size();  
		//总页数  
		if(list.size() == 0)return null;
		int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;  
		List[] result = new List[pageCount];  
		for(int i = 0; i < pageCount; i++) {  
			int start = i * pageSize;  
			//最后一条可能超出总数  
			int end = start + pageSize > total ? total : start + pageSize;  
			List subList = list.subList(start, end);  
			result[i] = subList;  
		}  
		return result;  
	}

	/** 验证保存时是否为空
	 * @param str 要验证的数组
	 * @return 通过验证返回true
	 */
	public static final boolean save_empty_verify(String str) {
		if (str == null || str.trim().isEmpty()) return true;
		return false;
	}

	/** 验证保存时是否为空
	 * @param str 要验证的数组
	 * @return 通过验证返回true
	 */
	public static final boolean save_empty_verify(String str[]) {
		for (String s : str) {
			if (s == null || s.trim().isEmpty()) return true;
		}
		return false;
	}

	/** 验证是否是数字
	 * @param str
	 * @return
	 */
	public static final boolean save_number_verify(String str[]){
		for (String s:str){
			if(!isNumber(s))return true;
		}
		return false;
	}

	/** 验证保存时是否为空
	 * @param str 要验证的数组
	 * @return 通过没有通过验证的元素
	 */


	/** 是否为手机号
	 * @param str
	 * @return
	 */
	public static final boolean isPhone(String str){
		return str.matches("^(1)[0-9]{10}$");
	}


	/** 是否为0和非0打头的自然数
	 * @param str
	 * @return
	 */
	public static final boolean isNumber(String str){
		return str.matches("^([1-9][0-9]*|0)$");
	}
	
	/** 是否为小数
	 * @param str
	 * @return
	 */
	public static final boolean isDouble(String str){
		return str.matches("^([1-9][0-9]*|0)(\\.[0-9]+)?$");
	}

	/** 是否为数字 可以为正负数
	 * @param str 
	 * @return 
	 */
	public static final boolean isNumber1(String str){
		return str.matches("^[+-]?([1-9][0-9]*|0)(\\.[0-9]+)?$");
	}


	/** 是否为数字 可以为正负数和百分数
	 * @param str
	 * @return
	 */
	public static final boolean isNumber2(String str){
		return str.matches("^[+-]?([1-9][0-9]*|0)(\\.[0-9]+)?%?$");
	}
}
