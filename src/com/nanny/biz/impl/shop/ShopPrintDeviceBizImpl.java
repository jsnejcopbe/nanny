package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.shop.ShopPrintDeviceBiz;
import com.nanny.model.ShopPrintDevice;
import com.zhuoan.ssh.dao.SSHUtilDao;


@Transactional
@Service("shopPrintDeviceBiz")
public class ShopPrintDeviceBizImpl implements ShopPrintDeviceBiz {
	
	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONArray getPrintMsg(Long shopID) {
		String sql=" select b.deKey,b.deNo,a.printerType FROM base_shop a " +
				" LEFT JOIN shop_print_device b ON b.shopID=a.id WHERE a.id=? " ;
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	@Override
	public String updatePrintMsg(int type,Long shopID,ShopPrintDevice spd) {
		String sql = "delete from shop_print_device  where shopID=? ";
		Object[] param={shopID.intValue()};
		dao.updateObjectBySQL(sql, param);
		dao.saveObject(spd);
		
		String sql2 = "update base_shop set printerType=? where id=?";
		Object[] param2={type,shopID.intValue()};
		dao.updateObjectBySQL(sql2, param2);
		
		return "修改成功";
		
	}

	@Override
	public String updateShopPrint(int type,Long shopID) {
		String sql = "update base_shop set printerType=?  where id=? ";
		Object[] param = {type,shopID.intValue()};
		dao.updateObjectBySQL(sql, param);
 		return "修改成功";
	}
	
	

	

}
