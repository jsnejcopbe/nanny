package com.nanny.biz.impl.supplier;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.supplier.SupplierShopBiz;
import com.nanny.model.SupplierOrder;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;


@Transactional
@Service
public class SupplierShopBizImpl implements SupplierShopBiz {

	@Resource
	private SSHUtilDao dao;
	
	
	@Override
	public JSONObject getsupshop(JSONObject obj,PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		sql.append(" select $  FROM supplier_shop_touch a ");
		sql.append(" LEFT JOIN base_supplier b ON b.id=a.supplierID ");
		sql.append(" LEFT JOIN base_shop c ON c.id=a.shopID ");
		sql.append(" WHERE a.supplierID=?");
		Object name=obj.get("shname");
		if( name !=null&&!name.toString().isEmpty()&&!"".equals(name)){
			sql.append(" and c.shop_name  like '%"+obj.getString("shname")+"%'");
		}
		
		Object[] queryParam={obj.getInt("sid")};
		
		JSONArray shlist=JSONArray.fromObject( dao.getObjectListBySQL(sql.toString().replace("$", " a.shopID,c.shop_name,c.shop_icon,c.memo "), queryParam, pageUtil));
		
		
		JSONArray total=JSONArray.fromObject( dao.getObjectListBySQL(sql.toString().replace("$", " COUNT(a.id) as total  "), queryParam, pageUtil));
		
		for(int i=0;i<shlist.size();i++){
			JSONObject bean=shlist.getJSONObject(i);
			//bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd hh:mm:ss", "createTime");
			QueryParam queryParam1 = new QueryParam();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
		
			paramMap.put("recSupplierId", obj.getLong("sid"));
			typeMap.put("recSupplierId", "eq");
			
			
			paramMap.put("payShopId", bean.getLong("shopID"));
			typeMap.put("payShopId", "eq");
			
			
			paramMap.put("status", 1);
			typeMap.put("status", "eq");
			
			
			queryParam1.setParamMap(paramMap);
			queryParam1.setTypeMap(typeMap);
			
			String colName="id";
			//订单数
			int orcount=  dao.getObjectCount(SupplierOrder.class, colName, queryParam1);
			bean.element("orcount", orcount);
		}
		
		
		
		JSONObject bean=new JSONObject();
		bean.element("nowPage", pageUtil.getPageIndex());
		bean.element("shlist", shlist);
		bean.element("total", total.getJSONObject(0).getInt("total"));
		bean.element("size", pageUtil.getPageSize());
		
		
		return bean;
	}

}
