package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.ShopSupplierBiz;
import com.nanny.model.SupplierOrder;
import com.nanny.model.SupplierOrderDetail;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;


@Transactional
@Service
public class ShopSupplierBizImpl implements ShopSupplierBiz {

	@Resource
	private SSHUtilDao dao;
	
	
	@Override
	public JSONObject getshopsup(JSONObject obj, PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT $ FROM supplier_global a ");
		sql.append(" LEFT JOIN base_supplier b ON b.id=a.supplierID  ");
		sql.append(" WHERE a.city = (SELECT city FROM sys_global_area  WHERE id=?) ");
		sql.append(" AND a.area= (SELECT area FROM sys_global_area  WHERE id=?) ");
		
		
		Object name = obj.get("sname");
		if( name !=null&&!name.toString().trim().isEmpty()){
			sql.append("and b.supplier_name like '%"+ obj.getString("sname")+"%' ");
		}
		
		
		Object[] queryParam={obj.getInt("adressID"),obj.getInt("adressID")};
		
		JSONArray suplist=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", "b.id,b.supplier_name,b.supplier_icon "), queryParam, pageUtil));

		JSONArray total=JSONArray.fromObject( dao.getObjectListBySQL(sql.toString().replace("$", " COUNT(a.id) as total  "), queryParam, pageUtil));
		
		JSONObject bean=new JSONObject();
		bean.element("suplist", suplist);
		bean.element("nowPage", pageUtil.getPageIndex());
		bean.element("total", total.getJSONObject(0).getInt("total"));
		bean.element("size", pageUtil.getPageSize());
		
		return bean;
	}


	@Override
	public String doshnextro(JSONObject bean) {
		
		SupplierOrder so=new SupplierOrder();
		
		so.setStatus(0);
		so.setRecSupplierId(bean.getLong("supid"));
		so.setPayShopId(bean.getLong("shopid"));
		
		String code = TimeUtil.getNowDate("yyyyMMddHHmmssSS");
		
		so.setOrderCode(code);
		so.setCreateTime(DateUtils.getTimestamp());
		so.setFee(0D);
		so.setTotalPrice(bean.getDouble("total"));
		long orid=  (Long) dao.saveObject(so);
		
		JSONArray data=bean.getJSONArray("arrydate");
		
		for (int i = 0; i < data.size(); i++) {
			JSONObject arr = data.getJSONObject(i);
			
			if(arr.getInt("count")!=0){
			SupplierOrderDetail sod=new SupplierOrderDetail();
			
			sod.setSupOrderId(orid);
			sod.setProId(arr.getLong("pid"));
			sod.setCount(arr.getInt("count"));
			sod.setPrice(arr.getDouble("price"));
			sod.setCreateTiem(DateUtils.getTimestamp());
			
			dao.saveObject(sod);
			}
		}
		
		
		
		return "下单成功";
	}

}
