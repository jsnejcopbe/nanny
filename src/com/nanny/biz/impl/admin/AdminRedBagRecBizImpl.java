package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.admin.AdminRedBagRecBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysRedbagRec;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service("adminRedBagRecBiz")
public class AdminRedBagRecBizImpl implements AdminRedBagRecBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Long addRec(SysRedbagRec bean) {
		dao.saveObject(bean);
		return bean.getId();
	}

	@Override
	public JSONArray doredbag(String logmin, String logmax, PageUtil pageUtil) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.id,a.actName,a.createTime,a.money,a.num,a.result,a.type,b.nickName,b.headImg  FROM sys_redbag_rec a ");
		sql.append(" INNER JOIN base_users b on b.id=a.userID ");
		sql.append("  where 1=1 ");
		if(!"".equals(logmin) && logmin != null){
			sql.append(" and a.createTime>='"+logmin+"'");
		}
		
		if(!"".equals(logmax) && logmax != null){
			sql.append(" and a.createTime<='"+logmax+"'");
		}
		sql.append(" order by a.id desc");

		Object[] queryParam={};
		JSONArray redp=JSONArray.fromObject( dao.getObjectListBySQL(sql.toString(), queryParam, pageUtil));
		 for(int i=0;i<redp.size();i++){
				JSONObject bean=redp.getJSONObject(i);		
				bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");

			}
		return redp;
	}
	
}
