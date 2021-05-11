package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.SysGlaAreaBiz;
import com.nanny.model.SysGlobalArea;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("sysGlaAreaBiz")
public class SysGlaAreaBizImpl implements SysGlaAreaBiz {
	
	@Resource
	private SSHUtilDao dao;
	
	public JSONArray getSysProvince() {
		String sql=" select province from sys_global_area group by province";
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, null, null));
		return array;
	}

	public JSONArray getSysCity(String province) {
		String sql=" select city from sys_global_area where province=? group by city";
		Object[] param={province};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	public JSONObject getGlaArea(JSONObject condition, PageUtil pageUtil) {
		String queryList="id,province,city,area,detAdd,addName,lon,lat";
		Object[] param={"%"+condition.getString("addName")+"%"};
		//0.拼接sql
		StringBuffer sql=new StringBuffer();
		sql.append(" select $ from sys_global_area");
		sql.append(" where 1=1");
		if(condition.containsKey("province"))
			sql.append(" and province='"+condition.getString("province")+"'");
		if(condition.containsKey("city"))
			sql.append(" and city='"+condition.getString("city")+"'");
		if(!("").equals(condition.getString("addName")))
			sql.append(" and addName like ?");
		else
			param=null;
		//1.查询数据
		JSONArray data=JSONArray.fromObject(dao.getObjectListBySQL((sql.toString()).replace("$", queryList)+" order by id desc", param, pageUtil));
		JSONArray count=JSONArray.fromObject(dao.getObjectListBySQL((sql.toString()).replace("$", "count(id) as count"), param, null));
		
		//2.返回数据
		JSONObject obj=new JSONObject();
		obj.element("nowPage", pageUtil.getPageIndex());
		obj.element("data", data);
		obj.element("total", count.getJSONObject(0).getInt("count"));
		obj.element("size", pageUtil.getPageSize());
		return obj;
	}

	public Long addNewMap(SysGlobalArea bean) {
		dao.saveObject(bean);
		return bean.getId();
	}

	public void updateMapData(SysGlobalArea bean){
		dao.updateObject(bean);
	}

	public JSONArray getSysAreaByPoiData(JSONArray poiList,String city) {
		//0.组织sql
		String sql=" select id,province,city,area,detAdd,addName,lon,lat from sys_global_area where";
		
		if(city!=null)
			sql+=" city like '%"+city+"%' and (";
		else
			sql+=" 1=1 and (";
		
		for(int i=0;i<poiList.size();i++)
		{
			JSONObject tmpobj=poiList.getJSONObject(i);
			if((i+1)==poiList.size())
				sql+=" addName like '%"+tmpobj.getString("title")+"%')";
			else
				sql+=" addName like '%"+tmpobj.getString("title")+"%' or";
		}
		
		//2.查询数据
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, null, null));
		return  array;
	}

	public JSONArray getSysAreaByCityAndName(String city, String name) {
		String sql=" select id,detAdd,addName from sys_global_area where city like ? and (detAdd like ? or addName like ?)";
		Object[] param={"%"+city+"%","%"+name+"%","%"+name+"%"};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	public JSONObject getUsedAddressList(String name,PageUtil pageUtil) {
		String checkQuery=" sd.adressID,sga.*";
		String sql=" select $" +
				   " from shop_dispatching sd,sys_global_area sga" +
				   " where sga.id=sd.adressID";
		if(name!=null && !("").equals(name))
			sql+=" and (sga.addName like '%"+name+"%' or sga.detAdd like '%"+name+"%')";
		sql+=" group by sd.adressID";
		
		String countSql="select count(t.id) as total from ($) t";
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", checkQuery), null, pageUtil));
		JSONArray count=JSONArray.fromObject(dao.getObjectListBySQL(countSql.replace("$", sql.replace("$", "sga.id")), null, null));
		
		JSONObject rtnData=new JSONObject();
		rtnData.element("total", count.getJSONObject(0).getInt("total"));
		rtnData.element("data", array);
		rtnData.element("nowPage", pageUtil.getPageIndex());
		rtnData.element("size", pageUtil.getPageSize());
		return rtnData;
	}
}
