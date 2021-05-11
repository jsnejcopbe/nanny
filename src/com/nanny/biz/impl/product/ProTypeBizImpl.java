package com.nanny.biz.impl.product;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nanny.biz.product.ProTypeBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商品分类管理
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ	
 * @version 0.1
 */
@Transactional
@Service
public class ProTypeBizImpl implements ProTypeBiz {
	
	@Resource
	private SSHUtilDao sshUtilDao;


	/**
	 * 商品类别更新
	 */
	@Override
	public JSONObject updProType(JSONObject bean) {
		
		String sql="UPDATE shop_pro_type SET name=? WHERE id=?";
		Object[] queryParam={bean.get("name"),bean.get("id")};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		
		return null;
	}


	public JSONArray getFirstProCla(Long shopID, Long parID) {
		String sql=" select id,name,parID from shop_pro_type where shopID=? and parID=?";
		Object[] param={shopID.intValue(),parID.intValue()};
		JSONArray array=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		return array;
	}
	
	
	
	
}
