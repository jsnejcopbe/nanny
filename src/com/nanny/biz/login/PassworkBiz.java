package com.nanny.biz.login;

import net.sf.json.JSONArray;

public interface PassworkBiz {
	
	/**
	 * 更改密码
	 * @param tel
	 * @param pass
	 * @return
	 */
	public void updatepawork(String tel,String pass);
	/**
	 * 更换手机
	 * @param id
	 * @param tel
	 */
	public void updatePhonework(int id,String tel);
	
	
	/**
	 * 
	 * @param tel
	 * @return
	 */
	public JSONArray  dopawork(String tel);
	
	public boolean checkPhone(String tel);
}
