package com.nanny.biz.global.store.impl;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.global.store.ShopMsgBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopMsgBiz")
public class ShopMsgBizImpl implements ShopMsgBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONObject getShopMsg(Long shopID) {
		String sql=" select * from base_shop where id=?";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0);
	}
	
	public JSONObject getShopDetail(Long shopID){
		String sql=" select bs.*,sga.detAdd,bu.tel" +
				   " from base_shop bs" +
				   " left join sys_global_area sga on bs.adressID=sga.id" +
				   " left join base_users bu on bs.userID=bu.id" +
				   " where bs.id=?";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()>0)
			return array.getJSONObject(0);
		else
			return null;
	}

	public JSONArray getShopOfficeHours(Long shopID){
		String sql=" select startTime,endTime from shop_officehours where shopID=?";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}
	
	public JSONArray getShopIDListByUserAdd(Long areaID){
		String sql=" select bs.id,bs.shop_name,bs.memo,sga.addName from shop_dispatching sd" +
				   " inner join base_shop bs on bs.id=sd.shopID" +
				   " right join sys_global_area sga on sga.id=sd.adressID" +
				   " where sga.id=? and bs.situation=? order by sd.memo,bs.id desc";
		Object[] param={areaID.intValue(),Dto.SHOP_SITU};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}
	
	public JSONArray getShopDetListByUserAdd(Long areaID,PageUtil pageUtil){
		String sql=" select bs.id,bs.shop_name,bs.shop_icon,bu.tel,sga.detAdd" +
				   " from base_shop bs" +
				   " inner join shop_dispatching sd on bs.id=sd.shopID" +
				   " left join base_users bu on bs.userID=bu.id " +
				   " left join sys_global_area sga on sga.id=bs.adressID" +
				   " where sd.adressID=? and bs.situation=? " +
				   " order by sd.memo,bs.id desc";
		Object[] param={areaID.intValue(),Dto.SHOP_SITU};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param,pageUtil));
		return array;
	}

	public JSONObject getSysShopList(PageUtil pageUtil,String name) {
		String query=" bs.id as shopID,bs.shop_name ,bs.shop_icon ,bu.nickName";
		String sql=" select $ from base_shop bs,base_users bu where bs.situation=0 and bu.id=bs.userID";
		if(name !=null && !("").equals(name))
			sql+=" and (bs.shop_name like '%"+name+"%' or bu.nickName like '%"+name+"%' or bu.tel like '%"+name+"%')";
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", query), null, pageUtil));
		JSONArray count=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", "count(bs.id) as total"), null, null));
		
		JSONObject rtnData=new JSONObject();
		rtnData.element("total", count.getJSONObject(0).getInt("total"));
		rtnData.element("data", countShopOrders(array));
		rtnData.element("nowPage", pageUtil.getPageIndex());
		rtnData.element("size", pageUtil.getPageSize());
		return rtnData;
	} 
	
	/**
	 * 计算取出的店家的订单销量
	 * @param shopList
	 * @return
	 */
	private JSONArray countShopOrders(JSONArray shopList)
	{
		String sql=" select count(id) as total from base_orders where recShopID=? and `status`=?";
		for(int i=0;i<shopList.size();i++)
		{
			Object[] param={shopList.getJSONObject(i).getInt("shopID"),Dto.STATU_DONE};
			JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
			shopList.getJSONObject(i).element("orderCount", array.getJSONObject(0).getLong("total"));
		}
		return shopList;
	}
	
/**
 * 轮波
 */
	@Override
	public JSONArray getwheel(Long shopID) {
	 String sql="select * FROM user_wheel where shopID=? and isUser=0  OR isAllUser=0 ";
	 Object[] objects={shopID.intValue()};
	 JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, objects, null));
		return array;
	}
}
