package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.shop.GroupMessage;
import com.nanny.model.ShopSendmsgRec;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Service
@Transactional
public class GroupMessageImpl implements GroupMessage {
	@Resource
	private SSHUtilDao dao;
	/**
	 *	查询 商户  粉丝
	 */
	@Override
	public JSONObject getSelUserShow(String shopID,Integer aderssId,String key,PageUtil pageUtil) {
		//查询 商户 地址
		String sqlString="SELECT * FROM  shop_dispatching sd,sys_global_area sga "
					+ " WHERE sd.adressID=sga.id and sd.shopID=?";
		Object[] queryParam={shopID};
		JSONArray jaDZ= JSONArray.fromObject(dao.getObjectListBySQL(sqlString, queryParam, null));
		//拼接字符串
		StringBuffer buffer=new StringBuffer();
//		String sql = "SELECT tt FROM base_users bu,user_receive_add ura "
//				+ " WHERE ura.userID=bu.id AND bu.recShopID="+shopID;
		String sql = "SELECT tt FROM base_users bu "
				+ "LEFT JOIN user_receive_add ura ON ura.userID=bu.id  "
				+ "WHERE bu.recShopID=? ";
		buffer.append(sql);
		//判断key -name tel  不为null 空
		if(!"".equals(key) && key!=null){
			key=key.trim();//去除空格
			//模糊 查询
			buffer.append(" AND (bu.nickName like '%"+key+"%' or bu.tel like '%"+key+"%' ) ");
		}
		//判断地址 不为空
		if(!"".equals(aderssId) && aderssId!=null){
			//查询地址
			buffer.append(" AND ura.memo="+aderssId);}
		
		buffer.append(" GROUP BY bu.id ");
		//查询所有  显示
		JSONArray jsonArray= JSONArray.fromObject(dao.getObjectListBySQL(buffer.toString().replace("tt", " * "),queryParam, pageUtil));
		//查询数量  用于 分页 获取 pageCount
		JSONArray count= JSONArray.fromObject(dao.getObjectListBySQL(buffer.toString().replace("tt", " count(bu.id) as count "),queryParam, null));
		JSONObject bean = new JSONObject();
		//循环
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject bean1 = jsonArray.getJSONObject(i);
			//JSONObject 的时间 转化为yyyy-MM-dd HH:mm:ss
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd hh:mm:ss", "createTime");
			//判断不为 null
			if(("null").equals(bean1.getString("nickName"))){
				bean1.element("nickName","");}
			if(("null").equals(bean1.getString("tel"))){
				bean1.element("tel","");}
			if(("null").equals(bean1.getString("sex"))){
				bean1.element("sex","");}
			if(("null").equals(bean1.getString("community"))){
				bean1.element("community","");}
		}
		//地址下拉
		bean.element("jaDZ", jaDZ);
		//展示内容
		bean.element("jsonArray", jsonArray);
		//page 的总数数量
		bean.element("count", count.size());
//		bean.element("pageIndex", pageUtil.getPageIndex());
//		bean.element("pageSize", pageUtil.getPageSize());
		return bean;
}
	@Override
	public String getSel(String shopID, String nickName, String tel, String qq,
			PageUtil pageUtil) {
		return null;
	}
	
	
	public void addNewMsgRec(ShopSendmsgRec bean) {
		dao.saveObject(bean);
	}
	
	
	public JSONObject getMsgRec(JSONObject condition,PageUtil pageUtil) {
		String keyList=" ssr.msgCon,ssr.shopID,bs.shop_icon,bs.shop_name,bu.nickName,bu.tel";
		String count=" count(ssr.id) as count";
		
		StringBuffer sql=new StringBuffer(" select $ from shop_sendmsg_rec ssr,base_shop bs,base_users bu");
		sql.append(" where ssr.shopID=bs.id and bs.userID=bu.id");
		if(condition.containsKey("shopID"))
			sql.append(" and ssr.shopID="+condition.getLong("shopID"));
		if(condition.containsKey("starTime"))
			sql.append(" and ssr.createTime>='"+condition.getString("starTime")+"' and ssr.createTime<'"+condition.getString("endTime")+"'");
		sql.append(" order by ssr.id desc");
		
		JSONArray data=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", keyList), null, pageUtil));
		JSONArray totalCount=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", count), null, pageUtil));
		
		JSONObject backObj=new JSONObject();
		backObj.element("taotal", totalCount.getJSONObject(0).getInt("count"));
		backObj.element("data", data);
		backObj.element("nowPage", pageUtil.getPageIndex());
		return backObj;
	}
}
