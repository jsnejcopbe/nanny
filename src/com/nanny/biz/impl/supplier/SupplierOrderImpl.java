package com.nanny.biz.impl.supplier;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.supplier.SupplierOrderBiz;
import com.nanny.model.SupplierShopTouch;
import com.nanny.util.DateUtil;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class SupplierOrderImpl implements SupplierOrderBiz{
	
	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONObject supplierDetById(int suOrderId) {
		
		String sql= "select a.totalPrice,a.fee ,a.payShopID,a.status,b.memo,b.adressID,b.shop_icon,b.shop_name,b.createTime ,s.detAdd,s.addName,a.orderCode,a.shopmsg from supplier_order a LEFT  JOIN base_shop b ON a.payShopID = b.id LEFT JOIN sys_global_area s ON b.adressID=s.id where a.id=?";

		
		Object[] queryParam = {suOrderId};
		
		JSONArray arr = JSONArray.fromObject(dao.getObjectListBySQL(sql,queryParam, null));
		for(int i=0;i<arr.size();i++){
			JSONObject bean=arr.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			
		}
		
		
		sql=" select c.* ,d.* from supplier_order_detail c LEFT JOIN supplier_product d ON  c.proID= d.id where c.sup_orderID=?";
		Object[] queryParam1={suOrderId};
		JSONArray arr1=JSONArray.fromObject(dao.getObjectListBySQL(sql,queryParam1, null));
		for(int i=0;i<arr1.size();i++){
			JSONObject bean=arr1.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			
		}

		JSONObject jso1=new JSONObject();

		
		jso1.element("supplierDet", arr.getJSONObject(0));

		jso1.element("shopDet", arr1);
		
		return jso1;
		
	}



	@Override
	public void updateSupplier(JSONObject bean, int id) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("update supplier_order set");
		
		Object msg=bean.get("msg");
		
		if(msg!=null && !msg.toString().trim().isEmpty()){
			sql.append(" memo='"+bean.getString("msg")+"' ");

		}
		Object status=bean.get("status");
		if(status!=null && !status.toString().trim().isEmpty()){
			sql.append(" status="+bean.getLong("status"));
			
		}
		sql.append(" where id=?");
		Object[] query={id};
		dao.updateObjectBySQL(sql.toString(), query);
		
	}



	@Override
	public JSONObject searchOrder(JSONObject bean, PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select bu.*,bs.id as shopID, bs.shop_name,bs.shop_icon,bs.min_send_price,bs.delivery_price,bs.status,bs.memo as shopTel");
		sql.append(" from supplier_order bu ");
		sql.append(" LEFT  JOIN base_shop bs ON bu.payShopID = bs.id");
		sql.append(" where 1=1 ");
		Object orderCode=bean.get("orderCode");
		Object sta=bean.get("sta");
		if(orderCode!=null && !orderCode.toString().trim().isEmpty()){
		
			sql.append(" and ( bu.orderCode='"+bean.getString("orderCode")+"' or bs.shop_name='"+bean.getString("orderCode")+"' or bs.memo='"+bean.getString("orderCode")+"')");
		}
		
		
		if(sta!=null && !sta.toString().trim().isEmpty()){
	
			sql.append(" and  bu.status="+bean.getInt("sta"));
			
		}
		
	
		int count=Integer.parseInt(dao.getTotal(sql.toString().replace("bu.*,bs.id as shopID, bs.shop_name,bs.shop_icon,bs.min_send_price,bs.delivery_price,bs.status,bs.memo as shopTel ", "count(*) "), null));
		
		JSONArray arr = JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(),null, pageUtil));
	
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "createTime");
			
		}
	
		JSONObject obj=new JSONObject();
		
	
		obj.element("nowpage", pageUtil.getPageIndex());
		obj.element("value", arr);
		obj.element("total", count);
		obj.element("size", pageUtil.getPageSize());
		return obj;
	}



	@Override
	public void checkRelation(JSONObject bean) {
		long supplierId=bean.getLong("supplierId");
		long shopId=bean.getLong("payShopID");
		int supplierId1=bean.getInt("supplierId");
		int shopId1=bean.getInt("payShopID");
		String sql="select *from supplier_shop_touch where supplierID=? and shopID=?";
		Object[] query={supplierId1,shopId1};
		JSONArray arr = JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(),query, null));
		
		if(arr.size()>0){
			return;
		}
		else{
			SupplierShopTouch sst=new SupplierShopTouch();
			sst.setCreateTime(DateUtils.getTimestamp());
			sst.setSupplierId(supplierId);
			sst.setShopId(shopId);
			dao.saveObject(sst);
		}
		
	}

}
