package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UserCollectBiz;
import com.nanny.model.UserCollection;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service("usercollectBiz")
@Transactional
public class UserCollectBizImpl implements UserCollectBiz {

	// 载入资源
	@Resource
	private SSHUtilDao g_BaseDao;

	@Override
	public String addUserCollect(UserCollection bean) {
		// 添加新的收藏
		g_BaseDao.saveObject(bean);
		return "添加成功";
	}

	@Override
	public JSONObject cheUserCollectByuserID(Long userID,Long shopID) {
		// 拼接sql
		String sql = " select * from user_collection where userID=? and shopID=?";
		// 封装参数
		Object[] queryParam ={userID.intValue(),shopID.intValue()};
		//调用dao层
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql,queryParam, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;

	}

	@Override
	public String delUserCollectByuserIDandshopID(Long userID,Long shopID) {
		// 拼接sql
		String sql = "delete  from user_collection where userID=? and shopID=? ";
		// 封装参数
		Object[] queryParam= { userID.intValue(), shopID.intValue()};
		//调用DAO层
		g_BaseDao.updateObjectBySQL(sql, queryParam);
	
		return "取消收藏";
	}

	@Override
	public JSONArray getUserCollectionByuserID(Long userID) {
		//拼接sql
		String sql ="select a.userID,a.shopID,b.shop_name,b.shop_icon,c.address from user_collection a " +
				"LEFT JOIN base_shop b ON a.shopID=b.id " +
				"LEFT JOIN shop_position c ON a.shopID=c.shopID " +
				"where a.userID=? ";
		//封装参数
		Object[] queryParam ={userID.intValue()};
		//调用DAO层
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql,queryParam, null));
		return aUserArray; 
	}
}