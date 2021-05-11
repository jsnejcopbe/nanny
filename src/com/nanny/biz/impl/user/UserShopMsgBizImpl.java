package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UserShopMsgBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service("UserShopMsgbizimpl")
@Transactional
public class UserShopMsgBizImpl implements UserShopMsgBiz{
   
	//载入资源
	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONArray getMsgByid(String id) {
		//三步
		// 拼接sql
	String sql ="select usm.userID,usm.mailCon,usm.createTime,bu.nickName from user_site_message usm LEFT JOIN base_users bu ON usm.userID=bu.id where usm.id=?";
	 // 参数的封装到对象集
	Object[] queryParam={id};
	 //调用DAO实现查询
	JSONArray aUserArray = JSONArray.fromObject(dao.getObjectListBySQL(sql, queryParam, null));
	if (aUserArray.size() > 0)
		return aUserArray;
	else
		return null;


	}

}
