package com.nanny.biz.impl.admin;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.AdminSupplierBiz;
import com.nanny.model.BaseSupplier;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;


@Transactional
@Service
public class AdminSupplierBizImpl implements AdminSupplierBiz{
	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONObject getBaseSupplierInfo(JSONObject bean,PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * from base_supplier where 1=1");
		
		Object supplierInfo=bean.get("supplierInfo");
		if(supplierInfo!=null && !supplierInfo.toString().trim().isEmpty()){
			sql.append(" and supplier_name='"+bean.getString("supplierInfo")+"' or tel='"+bean.getString("supplierInfo")+"'");
		}
		Object status=bean.get("status");
		if(status!=null && !status.toString().trim().isEmpty()){
			sql.append(" and status="+bean.getInt("status"));
		}
		
		JSONArray arr = JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(),null, pageUtil));
		
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "createTime");
			String sql1="select count(*) from supplier_shop_touch sst INNER JOIN base_supplier bs ON bs.id=sst.supplierID INNER JOIN supplier_order so ON so.payShopID=sst.shopID  INNER JOIN supplier_order_detail sod ON sod.sup_orderID=so.id  INNER JOIN supplier_product sp ON sp.id=sod.proID  where so.status=1 and bs.id="+arr.getJSONObject(i).getInt("id");
			int count=Integer.parseInt(dao.getTotal(sql1,null));
//			bean.element("", value);
			bean1.element("count", count);
		}
		
//		String sql1="select sst.*,bs.*,so.id as suOrderId,so.*,sod.*,sp.createTime as spTime,sp.* from supplier_shop_touch sst INNER JOIN base_supplier bs ON bs.id= sst.supplierID INNER JOIN supplier_order so ON so.payShopID=sst.shopID  INNER JOIN supplier_order_detail sod ON sod.sup_orderID=so.id  INNER JOIN supplier_product sp ON sp.id=sod.proID  where bs.status=1";
//		int count1=Integer.parseInt(dao.getTotal(sql1, null));
		
		int count=Integer.parseInt(dao.getTotal(sql.toString().replace(" *", " count(id) "), null));
		
		
		JSONObject bsInfo=new JSONObject();
		
//		bsInfo.element("count1",count1 );
		bsInfo.element("bsInfo", arr);
		bsInfo.element("nowpage", pageUtil.getPageIndex());
		bsInfo.element("size", pageUtil.getPageSize());
		bsInfo.element("total", count);
		
		return bsInfo;
	}
	
	@Override
	public void addSupplier(BaseSupplier supplier) {
		
		dao.saveObject(supplier);
	}

	@Override
	public void changeSupplierStatus(int supplierId, int status) {

		StringBuffer sql=new StringBuffer();
		sql.append("update base_supplier set");
		
		sql.append(" status=?");
		
		sql.append(" where id=?");
		Object[] queryParam={status,supplierId};
		dao.updateObjectBySQL(sql.toString(), queryParam);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "操作成功");
		
	}
	
	@Override
	public boolean checkSupplierExi(JSONObject bean) {
		boolean b=false;
		StringBuffer sql=new StringBuffer();
		sql.append("select *from base_supplier where supplier_name='"+bean.getString("username")+"' or tel='"+bean.getString("tel")+"'");
	
		JSONArray arr = JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), null, null));
		if (arr.size() > 0){
			b=true;
		}
		
			return b;
	}

	@Override
	public JSONObject getOrder(JSONObject bean, PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		
		sql.append("select sst.*,so.id as suOrderId,so.*,sod.*,sp.createTime as spTime,sp.* from supplier_shop_touch sst");
		sql.append(" INNER JOIN supplier_order so ON so.payShopID=sst.shopID");
		sql.append(" INNER JOIN supplier_order_detail sod ON sod.sup_orderID=so.id");
		sql.append(" INNER JOIN supplier_product sp ON sp.id=sod.proID");
		sql.append(" where sst.supplierID="+bean.getInt("supplierId"));
		
		Object query=bean.get("query");
		if(query!=null && !query.toString().trim().isEmpty()){
			sql.append(" and (sp.name='"+bean.getString("query")+"' or so.orderCode='"+bean.getString("query")+"')");
		}
		Object sta=bean.get("sta");
		if(sta!=null && !sta.toString().trim().isEmpty()){
			sql.append(" and so.status="+bean.getInt("sta"));
		}
		
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), null, pageUtil));
		
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "spTime");
			
		}
		
		int count=Integer.parseInt(dao.getTotal(sql.toString().replace("sst.*,so.id as suOrderId,so.*,sod.*,sp.createTime as spTime,sp.*", " count(*) "), null));
		
		
		
		JSONObject orderList=new JSONObject();
		
		orderList.element("orderList",arr );
		orderList.element("nowpage", pageUtil.getPageIndex());
		orderList.element("size", pageUtil.getPageSize());
		orderList.element("total", count);

		return orderList;
	}

	@Override
	public void UpdateSupplierInfo(int id,String memo) {
		String sql="update  base_supplier set memo=? where id=?";
		
		Object[] queryParam={memo,id};
		
		dao.updateObjectBySQL(sql, queryParam);
		
	}

	


}
