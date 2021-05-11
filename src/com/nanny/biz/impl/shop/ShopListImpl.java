package com.nanny.biz.impl.shop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nanny.biz.admin.ShopList;
import com.nanny.dto.Dto;
import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.nanny.util.SqlUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

/**商品列表
 * @author syl
 *
 */
@Service
@Transactional
public class ShopListImpl implements ShopList{
	@Resource
	private SSHUtilDao dao;

	@Override
	public String commodity_init(Map<String, String> options,String shop_id,String right_shop_id,String isAdmin,String showType) {
		if(options == null)return "";	
		//System.out.println(options+""+isAdmin);
		String order = options.get("order");
		String orderType = options.get("orderType");
		String now_page = options.get("now_page");
		String row = options.get("row");
		String more = options.get("more");
		String type1 = options.get("typeID1");
		String type2 = options.get("typeID2");
		String brand = options.get("brandID");
		String use = options.get("isUse");
		
		String base_sql = "SELECT  sp.*,spt.`name` AS typeName, spb.`name` AS brandName,spb.icon FROM shop_product AS sp LEFT JOIN `shop_pro_type` AS spt ON sp.`typeID` = spt.`id` LEFT JOIN `shop_pro_brand` AS spb ON spb.`id` = sp.`brandID` where 1=1 ";
		if(right_shop_id !=null){
			base_sql = base_sql+" AND sp.proCode NOT IN (SELECT proCode FROM shop_product WHERE shopID = "+right_shop_id+")";
		}
		SqlUtil util = new SqlUtil(base_sql, now_page, row);
		Map<String, String> where = new LinkedHashMap<String, String>();
		if(more!=null && !more.trim().isEmpty()){
			where.put("and (sp.name like", more);
			where.put("or sp.proCode like", more+")");
		}
		where.put("and sp.shopID =", shop_id);
		//if(!"1".equals(isAdmin))where.put(" and sp.isUse =", "0");
		
		if(use!=null && !use.trim().isEmpty())where.put(" and sp.isUse =", use);
		
		if(type2!=null && !Dto.ERROR_CODE.equals(type2)){
			where.put("and spt.id =", type2);
		}else if(type1!=null && !Dto.ERROR_CODE.equals(type1)){
			where.put("and spt.parID =", type1);
		}
		if(brand!=null && !Dto.ERROR_CODE.equals(brand))where.put("and sp.brandID =", brand);
		
		where.put("and sp.isUse=", showType);
		
		
		util.setWhere(where);
		
		if(order!=null)
			util.setG_Oby("group by sp.id", order,orderType);
		else
			util.setG_Oby("group by sp.id", "isRecommond","desc");
		
		util.start();
		String fullSql = util.getFull_sql();
		String totalSql = util.getTotal_sql();
		//System.out.println(fullSql);
		//System.out.println(totalSql);
		
		List<Map<String, String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(fullSql, null, null);
		for(Map<String, String> map:datalist){
			Object temp = map.get("isUse");
			Object temp2= map.get("isRecommond");
			map.put("isUse", Dto.getIsUseState(temp.toString()));
			map.put("isRecInt", temp2.toString());
			map.put("isRecommond", Dto.getIsRecommond((Integer) temp2));
			map.put("createTime", BasisUtil.Time_Format(map.get("createTime")));
			Object temp3=map.get("isExchange");
			//System.out.println("isExchange:"+temp3);
			if( temp3==null || temp3.equals(0) ){
				map.put("isExchange","不可兑换");
			}
			else if(temp3.equals(1)){
				map.put("isExchange", "可兑换");
			}
			map.put("isExInt", temp3.toString());
		}
		Map<String, Object> map = BasisUtil.myTableHelp(dao.getTotal(totalSql, null), datalist);
		return JsonUtil.getJson(map);
	}

	@Override
	public String getType(String type) {
		String sql = "select id as s_value,name as s_text from shop_pro_type where parID = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{type}, null));
	}
	 
	/*
	 * 商户可能也有自己的品牌
	 */
	@Override
	public String getBrand(String shop_id) {
		//System.out.println(shop_id);
		String sql = "select * from shop_pro_brand where shopID = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{"0"}, null));
	}

	@Override
	public void upIsexchange(int productId) {
		
		String sql="select isExchange from shop_product where id=?";
		Object[] queryParam={productId};
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql, queryParam, null));
		Object ischange=arr.getJSONObject(0).get("isExchange");
		
		if(ischange.equals("null") || ischange.equals(0) ){
			sql="update shop_product set isExchange=1 where id=?";
			Object[] queryParam2={productId};
			dao.updateObjectBySQL(sql, queryParam2);
		}
		else{
		
		 sql="update shop_product set isExchange=0 where id=?";
		Object[] queryParam1={productId};
		dao.updateObjectBySQL(sql, queryParam1);
		}
	}
}
