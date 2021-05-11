package com.nanny.biz.impl.admin;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.BusinessBiz;
import com.nanny.biz.admin.ClienteleBiz;
import com.nanny.dto.Dto;
//import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;


@Transactional
@Service
public class ClienteleBizImpl implements ClienteleBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
		
	@Override
	public JSONObject doClienteleList(String name,String logmin,String logmax,PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		
		//bu.*
		//0.拼接sql
		sql.append("select $ from base_users bu ");
		sql.append(" LEFT JOIN base_shop a ON  a.id=bu.recShopID ");
		sql.append(" LEFT JOIN sys_global_area b ON  b.id=a.adressID ");
		sql.append("where bu.isAdmin=0  ");
		if(!"".equals(name) && name !=null){
			sql.append("and (bu.tel like '%"+name+"%'or bu.nickName like '%"+name+"%') ");
		}
		if(!"".equals(logmin) && logmin != null && !"null".equals(logmin)&&!"".equals(logmax) && logmax != null&&!"null".equals(logmax)){
			sql.append(" and bu.id IN(SELECT f.payUserID FROM base_orders f  WHERE f.status=3 and f.createTime>='"+logmin+"' and f.createTime<='"+logmax+"')");
		}else if(!"".equals(logmax) && logmax != null&&!"null".equals(logmax)){
			sql.append(" and bu.id in(SELECT f.payUserID FROM base_orders f  WHERE f.status=3 and f.createTime<='"+logmax+"')");
		}else  if(!"".equals(logmin) && logmin != null && !"null".equals(logmin)){
			sql.append(" and bu.id in(SELECT f.payUserID FROM base_orders f  WHERE f.status=3 and f.createTime>='"+logmin+"')");
		}
		
		
		
		
		sql.append(" and bu.id not in (select bs.userID from base_shop bs where bs.situation=0) ");
		sql.append(" ORDER BY  bu.id  desc ");
		Object[] queryParam={};
		
		//1.查询数据
		JSONArray client=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "bu.*,a.shop_name,b.city,b.detAdd,b.addName"), queryParam, pageUtil));
		//2.查询总条数
		JSONArray count=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "count(bu.id) as total"), queryParam, null));
		
		
		
		for(int i=0;i<client.size();i++){
			JSONObject bean=client.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			StringBuffer sql1=new StringBuffer();

			sql1.append("SELECT COUNT(b.id) FROM base_users a ");
			sql1.append(" LEFT JOIN base_orders b ON b.payUserID=a.id ");
			sql1.append(" WHERE b.status=3 ");
			if(!"".equals(logmin) && logmin != null&&!"null".equals(logmax)){
				sql1.append(" and b.createTime>='"+logmin+"'");
			}
			if(!"".equals(logmax) && logmax != null&&!"null".equals(logmax)){
				sql1.append(" and b.createTime<='"+logmax+"'");
			}
			if(logmin==null && "null".equals(logmin) && logmax==null && "null".equals(logmax)){
				sql1.append(" and to_days(b.createTime)=to_days(now()) ");
			}
			
			sql1.append(" AND a.id=? ");
			Object[] queryParam1={bean.getInt("id")};
			String orcount=sshUtilDao.getTotal(sql1.toString(), queryParam1);
			bean.element("orcount", orcount);
		}
		
		JSONObject obj=new JSONObject();
		obj.element("nowPage", pageUtil.getPageIndex());
		obj.element("client", client);
		obj.element("total", count.getJSONObject(0).getInt("total"));
		obj.element("size", pageUtil.getPageSize());
		return obj;
	
	}

}
