package com.nanny.biz.global.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;



import com.nanny.biz.global.store.ProductDetailsBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service("productdetails")
@Transactional
public class ProductDetailsBizImpl implements ProductDetailsBiz {
	//载入资源
	@Resource
	private SSHUtilDao g_BaseDao;
	
	public JSONArray getProductDedailsByshopID(String id) {
		//书写sql

	String sql="select sp.inventory,sp.shopID,sp.name,sp.price,sp.disPrice,sp.cover,sp.proDes,sp.typeID," +
		            "spb.name as spbname," +// 品牌
		            "spt.name as sptname," +//日用 学习 保鲜袋垃圾袋  
		            "spa.attrName as spaname,spa.attrDes as spad" +//如 容量 200ml 
		            " from shop_product sp " +
		            "LEFT  JOIN shop_pro_brand spb ON sp.id=spb.id" +
		            " LEFT JOIN shop_pro_type spt ON sp.id=spt.id  " +
		            "LEFT JOIN shop_pro_attribute spa ON sp.id=spa.id " +
		            "where sp.id=? ";
		
		// 封装参数
	   Object[] param={id};
		
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, param, null));
		
			return aUserArray;
	
	}

	
	
}
