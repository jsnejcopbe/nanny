package com.nanny.biz.impl.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.product.ProBrandBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;


/**
 * 商品品牌管理
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */


@Transactional
@Service
public class ProBrandBizImpl implements ProBrandBiz{
	@Resource
	private SSHUtilDao sshUtilDao;

	
	/**
	 * 商品品牌列表
	 */
	@Override
	public JSONArray BrandList(JSONObject brandlist,String name, PageUtil pageUtil) {
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.id,a.createTime,a.icon,a.memo,a.name,c.shop_name   ");
		sql.append("FROM shop_pro_brand a ");
		
		sql.append("LEFT JOIN  base_shop c on c.id=a.shopID ");
		
		
		if(name!=null&&!"".equals(name)){
			sql.append("WHERE a.name LIKE '%"+name+"%'");
		 	
		}
		Object[] queryParam={};
		JSONArray brand=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, pageUtil));
		
		return brand;
	}


	/**
	 * 商品品牌更新
	 */
	@Override
	public JSONObject updateProbrand(JSONObject bean) {
		
		String sql="UPDATE shop_pro_brand SET name=?,icon=? WHERE id=?";
		Object[] queryParam={bean.get("name"),bean.get("url"),bean.get("id")};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		
		return null;
	}


}
