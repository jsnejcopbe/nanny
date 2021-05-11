package com.nanny.biz.user;

import net.sf.json.JSONArray;

import com.zhuoan.ssh.bean.PageUtil;

public interface AdminadveritsBiz {

	public JSONArray getweel(PageUtil pageUtil,String bname);
	public JSONArray getweelcon(String id);
	public JSONArray getbaseshop();
	public void inertweel(Long bank,String imgStc,String jumpSrc,String isall,String isUse,String time);
	public void operation(String Disid,String Enid,String Delid);
	public void updatewheel( String imgStc, String jumpSr, String isAllUser, String isUser,String time,Long wheelid,String shopID);
}
