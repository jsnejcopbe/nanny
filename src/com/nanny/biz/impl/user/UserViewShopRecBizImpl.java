package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UserViewShopRecBiz;
import com.nanny.model.UserViewShopRec;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("userViewShopRecBiz")
public class UserViewShopRecBizImpl implements UserViewShopRecBiz {

	@Resource
	private SSHUtilDao dao;
	
	public void addUserViewShopRec(UserViewShopRec bean) {
		//0.查询是否存在地址信息
		JSONObject addMsg=getViewRecByOpenID(bean.getOpenId());
		if(addMsg!=null)
		{
			String sql=" update user_view_shop_rec set shopID=?,url=? where id=?";
			Object[] param={bean.getShopId().intValue(),bean.getUrl(),addMsg.getInt("id")};
			dao.updateObjectBySQL(sql, param);
		}else
			dao.saveObject(bean);
	}

	public JSONObject getViewRecByOpenID(String openID) {
		String sql=" select * from user_view_shop_rec where openID=?";
		Object[] param={openID};
		JSONArray data=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(data.size()==0)
			return null;
		else
			return data.getJSONObject(0);
	}

	public void addUserViewLocation(UserViewShopRec bean) {
		//0.查询是否存在地址信息
		JSONObject addMsg=getViewRecByOpenID(bean.getOpenId());
		if(addMsg!=null)
		{
			String sql=" update user_view_shop_rec set memo=? where id=?";
			Object[] param={bean.getMemo(),addMsg.getInt("id")};
			dao.updateObjectBySQL(sql, param);
		}else
			dao.saveObject(bean);
	}

}
