package com.nanny.biz.impl.supplier;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.supplier.SupplierIndexBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class SuplierIndexBizImpl implements SupplierIndexBiz {

	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONArray inorder(int sid) {
		
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.createTime,a.orderCode,a.totalPrice,a.status,c.shop_icon,c.shop_name,c.memo,d.detAdd,d.addName,a.id FROM supplier_order a");
		sql.append(" LEFT JOIN base_supplier b ON b.id=a.recSupplierID");
		sql.append(" LEFT JOIN base_shop c ON c.id=a.payShopID ");
		sql.append(" LEFT JOIN sys_global_area d ON d.id=c.adressID ");
		sql.append(" WHERE b.id=? ");
		sql.append(" ORDER BY a.id desc  limit 5 ");
		Object[] queryParam={sid};
		
		JSONArray product=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), queryParam, null));
		
		for(int i=0;i<product.size();i++){
			JSONObject objt=product.getJSONObject(i);
			objt=TimeUtil.transTimeStamp(objt, "yyyy-MM-dd HH:mm:ss", "createTime");
			
		}
		
		
		return product;
	}

}
