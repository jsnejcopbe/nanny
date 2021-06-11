package com.nanny.biz.global.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.store.ShopProDataBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SystemSetup;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopProDataBiz")
public class ShopProDataBizImpl implements ShopProDataBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONArray getProListByClass(JSONArray classList,Long firstClassID,Long shopID,PageUtil pageUtil) {
		//0.拼接sql
		StringBuffer sql=new StringBuffer();
		sql.append(" select sp.id,sp.name,sp.price,sp.disPrice,sp.cover,sp.buyCount,sp.viewCount,sp.typeID,st.name as className");
		sql.append(" from shop_product sp ,shop_pro_type st");
		sql.append(" where sp.isUse=? and sp.shopID=? and st.id=sp.typeID and ( sp.typeID="+firstClassID);
		for(int i=0;i<classList.size();i++)
		{
			JSONObject tmpObj=classList.getJSONObject(i);
			if(i+1==classList.size())
				sql.append(" or sp.typeID="+tmpObj.getLong("id")+")");
			else
				sql.append(" or sp.typeID="+tmpObj.getLong("id"));
		}
		sql.append(" order by sp.typeID desc");
		
		Object[] param={Dto.IS_SALE,shopID.intValue()};
		
		//1.查询数据
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), param, pageUtil));
		return createProJsonData(classList, array);
	}

	public JSONArray getProDataByCondition(JSONObject condition,PageUtil pageUtil){
		JSONArray data=new JSONArray();
		StringBuffer sql=new StringBuffer();
		sql.append(" select sp.id,sp.name,sp.price,sp.disPrice,sp.cover,sp.buyCount,sp.viewCount,sp.typeID,st.name as className");
		sql.append(" from shop_product sp ,shop_pro_type st");
		sql.append(" where st.id=sp.typeID and sp.isUse=? and sp.shopID=?");
		if(condition.containsKey("classID"))
			sql.append(" and sp.typeID="+condition.getLong("classID"));
		if(condition.containsKey("name")){
			sql.append(" and (sp.name like ? or st.name like ?)");
			Object[] param={Dto.IS_SALE,condition.getInt("shopID"),"%"+condition.getString("name")+"%","%"+condition.getString("name")+"%"};
			data=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), param, pageUtil));
		}else{
			Object[] param={Dto.IS_SALE,condition.getInt("shopID")};
			data=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), param, pageUtil));
		}
		return data;
	}
	/************************************************************************************************************************************************/	
	/*
	 * 16.03.22 hxq  
	 * 新增shopid获取兑换商品
	 */
	public JSONArray getProlistIsex(Long shopID,PageUtil pageUtil) {
		JSONArray isex=new JSONArray();
		JSONObject prois=new JSONObject();
		
		//0.拼接sql
		StringBuffer sql=new StringBuffer();
		sql.append(" select sp.id,sp.name,sp.price,sp.disPrice,sp.cover,sp.buyCount,sp.viewCount,sp.typeID,sp.isExchange");
		sql.append(" from shop_product sp,base_shop bs ");
		sql.append(" where sp.isUse=? and sp.shopID=?  and sp.isExchange=1 and bs.id=sp.shopID and bs.isTransfer=1 ");
	
		//sql.append(" order by sp.typeID desc");
		
		Object[] param={Dto.IS_SALE,shopID.intValue()};
		
		//1.查询数据
		
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), param, pageUtil));
		//获取系统设置
		 SystemSetup sts=(SystemSetup) dao.getObjectById(SystemSetup.class, 1L);
				
		 	double cash=sts.getCash();
		 	//兑换商品所需积分
			for(int j=0;j<array.size();j++){
				int point=0;
				if(!array.getJSONObject(j).get("isExchange").toString().equals("null") && !array.getJSONObject(j).get("isExchange").toString().equals("0")){
					double productprice=array.getJSONObject(j).getDouble("price");
					point= (int) (productprice/cash);
				}
				array.getJSONObject(j).element("point", point);
			}
			prois.element("className", "");
			prois.element("data", array);
			
			isex.add(prois);
		
		 return isex;
		
		
		
	}
	
	
	
/************************************************************************************************************************************************/	
	
	/**
	 * 组织商品数据
	 * @param classList
	 * @param dataArray
	 * @return
	 */
	private JSONArray createProJsonData(JSONArray classList,JSONArray dataArray)
	{
		JSONArray result=new JSONArray();
		//0.遍历所有小类
		int roop=0;
		for(int i=0;i<classList.size();i++)
		{
			JSONObject tmp=classList.getJSONObject(i);
			
			JSONObject item=new JSONObject();
			JSONArray data=new JSONArray();
			
			//1.遍历取出的商品数据
			for(int k=roop;k<dataArray.size();k++)
			{
				JSONObject tmp2=dataArray.getJSONObject(k);
//				if(tmp2.getLong("typeID")==tmp.getLong("id"))  前面检查过的不再检查
//					data.element(tmp2);
//				else{
//					roop=k;
//					break;
//				}
				if(tmp2.getLong("typeID")==tmp.getLong("id"))
					data.element(tmp2);
			}
			if(data.size() > 0){
				item.element("className", tmp.getString("name"));
				item.element("data", data);
				result.element(item);
			}
		}
		return result;
	}
}
