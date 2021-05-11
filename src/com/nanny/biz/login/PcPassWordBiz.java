package com.nanny.biz.login;

import net.sf.json.JSONArray;

public interface PcPassWordBiz {
	/**
	 * 更改密码
	 * @param tel
	 * @param pass
	 * @return
	 */
	public void updatepawork(String tel,String pass);
	
	
	/**
	 * 
	 * @param tel
	 * @return
	 */
	public JSONArray  dopawork(String tel);
}
