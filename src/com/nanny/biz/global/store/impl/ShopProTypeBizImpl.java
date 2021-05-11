package com.nanny.biz.global.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.store.ShopProTypeBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopProTypeBiz")
public class ShopProTypeBizImpl implements ShopProTypeBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONArray getFirstClassList(Long shopID) {
		String sql=" select id,name,parID,shopID,type " +
				   " from shop_pro_type where parID=0 and shopID=?" +
				   " order by memo*1 desc,id desc";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	public JSONArray getChildClassByID(Long classID) {
		String sql=" select id,name,parID,shopID from shop_pro_type where parID=? or id=? order by memo*1 desc,id desc";
		Object[] param={classID.intValue(),classID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	public JSONArray getClassTreeData(Long shopID) {
//		String sql=" select id,name,parID,shopID from shop_pro_type where shopID=? order by id desc";
		String sql=" select id,name,parID,shopID from shop_pro_type " +
				   " where shopID=0 and " +
				   " (id in(select typeID from shop_product where shopID=? group by typeID) " +
				   " or id in(select spt.parID from shop_product sp,shop_pro_type spt where sp.shopID=? and sp.typeID=spt.id group by parID))" +
				   " order by id desc";
		Object[] param={shopID.intValue(),shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		array=createTreeData(array);
		return array;
	}

	
	/**
	 * 组织树状数据
	 */
	private JSONArray createTreeData(JSONArray data)
	{
		JSONArray treeData=new JSONArray();
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpObj=data.getJSONObject(i);
			if(tmpObj.getLong("parID")==0){
				tmpObj.element("childArray", findChild(data, tmpObj.getLong("id")));
				treeData.add(tmpObj);
			}
		}
		return treeData;
	}
	
	private JSONArray findChild(JSONArray data,Long parID){
		JSONArray treeData=new JSONArray();
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpObj=data.getJSONObject(i);
			if(tmpObj.getLong("parID")==parID){
				String name=tmpObj.getString("name");
				if(name.length()>4)
					tmpObj.element("splitName", name.substring(0, 4)+"...");
				else
					tmpObj.element("splitName", name);
				treeData.add(tmpObj);
			}
		}
		return treeData;
	}

	public void updateClassSort(JSONArray data) {
		//遍历拼接sql
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpObj=data.getJSONObject(i);
			String sql=" update shop_pro_type set memo=? where shopID=0 and id=?";
			Object[] param={tmpObj.getString("memo"),tmpObj.getInt("classID")};
			dao.updateObjectBySQL(sql, param);
		}
	}

	public JSONObject getClassDetById(Long classID) {
		String sql=" select * from shop_pro_type where id=?";
		Object[] param={classID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()>0)
			return array.getJSONObject(0);
		else
			return null;
	}
	
	/*
	 * 新添加的方法
	 * 
	 */
	public void hideClassSort(Long id) {
		// 拼接sql
		String sql=" update shop_pro_type set type=0 where shopID=0 and id=?";
		Object[] param={id.intValue()};
		dao.updateObjectBySQL(sql, param);
		
	}

	
	public void showClassSort(Long id) {
		// 拼接sql
		String sql=" update shop_pro_type set type=1 where shopID=0 and id=?";
		Object[] param={id.intValue()};
		dao.updateObjectBySQL(sql, param);
		
	}


	@Override
	public JSONArray getChildClassList(int parID) {
		String sql=" select id,name,parID,shopID " +
				   " from shop_pro_type where parID=? and shopID=0 " +
				   " order by memo*1 desc,id desc";
		Object[] param={parID};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
		
	}

}
