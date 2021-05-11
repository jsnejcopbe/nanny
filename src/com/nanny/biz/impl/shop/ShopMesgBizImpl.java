package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.ShopMesgBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Service
@Transactional
public class ShopMesgBizImpl implements ShopMesgBiz {
	
	@Resource
	private SSHUtilDao sshUtilDao;
	
	@Override
	public JSONArray getShopMsg(int toUser,String state,PageUtil pageUtil ) {
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  a.*,(select nickName from base_users where id = a.otherSide) AS oname,(select nickName from base_users where id = a.userID) AS uname FROM user_site_message a ");
		sql.append("WHERE (a.otherSide=? or a.userID=?)");
		if(!"".equals(state) && state !=null&&!"-1".equals(state)){
			sql.append(" and a.isRead="+state);
		}
		
		sql.append(" ORDER BY a.id desc ");
		Object[] queryParam={toUser,toUser};
		JSONArray mesg=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, pageUtil));
		for(int i=0;i<mesg.size();i++){
			JSONObject bean=mesg.getJSONObject(i);
			
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			
			if(bean.getInt("isRead")==Dto.IS_MSG_READ)
				bean.element("stats", "未读");
			else 
				bean.element("stats", "已读");
			
		}
		
		
		return mesg;
	}

	
	
}
