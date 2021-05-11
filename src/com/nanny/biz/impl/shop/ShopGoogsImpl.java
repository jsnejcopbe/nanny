package com.nanny.biz.impl.shop;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.shop.ShopGoods;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProduct;
import com.nanny.util.DateUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service
@Transactional
public class ShopGoogsImpl implements ShopGoods{
	@Resource
	private SSHUtilDao dao;

	@Override
	public String start_goods(JSONArray list,String shop_id) {
		for(int i=0;i<list.size();i++){
			Long proID=list.getJSONObject(i).getLong("id");
			
			String sql = "select * from shop_product where id=? ";
			List<ShopProduct> shops = (List<ShopProduct>) dao.getBeanListBySQL(sql, new Object[]{proID.intValue()}, ShopProduct.class);
			ShopProduct sp = shops.get(0);
			ShopProduct shop = this.setShopProduct(sp,shop_id,list.getJSONObject(i).getDouble("price"));
			if(isExist(sp.getProCode(),shop_id))continue;
			else dao.saveObject(shop);
		}
		return Dto.SUCCEED;
	}
	
	private boolean isExist(String name,String id){
		String sql = "SELECT id FROM shop_product WHERE proCode = ? and shopID  = ?";
		List<String> oneList = dao.getOneList(sql, new Object[]{name,id});
		if(oneList.size()>0)return true;
		return false;
	}
	
	private ShopProduct setShopProduct(ShopProduct shop,String shop_id,Double price){
		ShopProduct s = new ShopProduct();
		s.setId(null);
		s.setName(shop.getName());
		s.setProCode(shop.getProCode());
		s.setPrice(price);
		s.setDisPrice(shop.getDisPrice());
		s.setInventory(0);//shop.getInventory()
		s.setCover(shop.getCover());
		s.setViewCount(0);
		s.setBuyCount(0);
		s.setProDes(shop.getProDes());
		s.setShopId(Long.valueOf(shop_id));
		s.setBrandId(shop.getBrandId());
		s.setTypeId(shop.getTypeId());
		s.setProTag(shop.getProTag());
		s.setIsUse(0);
		s.setIsCommission(shop.getIsCommission());
		s.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		s.setMemo(shop.getMemo());
		s.setCostPrice(shop.getCostPrice());
		s.setIsRecommond(shop.getIsRecommond());
		s.setIsExchange(0);
		return s;
	}

	@Override
	public JSONArray pro_sup(int proid) {
		
		
		String sql1="SELECT proCode FROM shop_product WHERE id=?";
		
		Object[] queryParam1={proid};
		
		JSONArray pro=JSONArray.fromObject(dao.getObjectListBySQL(sql1.toString(), queryParam1, null));
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.supplier_name,a.tel from base_supplier a ");
		sql.append("LEFT JOIN supplier_product b on b.supplierID=a.id ");
		sql.append("WHERE b.proCode=? ");
		
		Object[] queryParam={pro.getJSONObject(0).getString("proCode")};
		
		 JSONArray suplist=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), queryParam, null));
		return suplist;
	}
	
}
