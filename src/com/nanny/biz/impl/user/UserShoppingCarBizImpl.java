package com.nanny.biz.impl.user;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UserShoppingCarBiz;
import com.nanny.model.UserShoppingCar;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service("userShoppingCarBiz")
public class UserShoppingCarBizImpl implements UserShoppingCarBiz {

	@Resource
	private SSHUtilDao dao;
	
	public void addProduct(JSONArray data,Long userID,Long shopID) {
		//2.删除旧有数据
		String sql=" delete from user_shopping_car where shopID=? and userID=?";
		Object[] param={shopID.intValue(),userID.intValue()};
		dao.updateObjectBySQL(sql, param);
		//1.添加新数据
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpobj=data.getJSONObject(i);
			UserShoppingCar bean=new UserShoppingCar();
			bean.setCount(tmpobj.getInt("count"));
			bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
			bean.setPrice(tmpobj.getDouble("price"));
			bean.setShopId(tmpobj.getLong("shopID"));
//			bean.setUserId(tmpobj.getLong("userID"));
			bean.setUserId(userID);
			bean.setProId(tmpobj.getLong("proID"));
			if(tmpobj.containsKey("ise"))
				bean.setMemo(tmpobj.getString("ise"));
			else
				bean.setMemo("0");
			dao.saveObject(bean);
		}
	}

	public JSONArray getCarProListByID(Long userID, Long shopID) {
		String sql=" select proID,price,count,memo from user_shopping_car where shopID=? and userID=?";
		Object[] param={shopID.intValue(),userID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		for(int i=0;i<array.size();i++)
		{
			if(array.getJSONObject(i).getInt("memo") == 1)
				array.getJSONObject(i).element("ise", array.getJSONObject(i).getInt("memo"));
		}
		return array;
	}

}
