package com.nanny.biz.impl.login;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.nanny.biz.login.RegisterBiz;
import com.nanny.model.BaseUsers;
import com.zhuoan.ssh.dao.SSHUtilDao;


@Service("registerBiz")
@Transactional
public class RegisterBizImpl implements RegisterBiz{
	//载入资源
		@Resource
		private SSHUtilDao g_BaseDao;	
	
	@Override
	public JSONObject addNewUser(BaseUsers bean) {
		if(bean.getOriginId()!=null){
			//0.去除openID
			String sql=" update base_users set originID=null where originID=?";
			Object[] param={bean.getOriginId()};
			g_BaseDao.updateObjectBySQL(sql, param);
		}
		
    	//添加用户
		g_BaseDao.saveObject(bean);
	    JSONObject oRtnObj=new JSONObject();
	    oRtnObj.element("userID", bean.getId());
		oRtnObj.element("nickName", bean.getNickName());
		oRtnObj.element("tel", bean.getTel()==null?"null":bean.getTel());
		oRtnObj.element("mail", bean.getMail()==null?"null":bean.getMail());
		oRtnObj.element("birthdate", bean.getBirthdate());
		oRtnObj.element("qq", bean.getQq()==null?"null":bean.getQq());
		oRtnObj.element("password", bean.getPassword());
		oRtnObj.element("balance", bean.getBalance());
		oRtnObj.element("point", bean.getPoint());
		oRtnObj.element("headImg", bean.getHeadImg());
		oRtnObj.element("sex", bean.getSex());
		oRtnObj.element("isAdmin", bean.getIsAdmin());
		oRtnObj.element("recShopID", bean.getRecShopId());
		oRtnObj.element("origin", bean.getOrigin());
		oRtnObj.element("originID", bean.getOriginId()==null?"null":bean.getOriginId());
		oRtnObj.element("createTime", bean.getCreateTime());
		return oRtnObj;
	  }

}
