package com.nanny.biz.impl.supplier;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.supplier.SupplierProductBiz;
import com.nanny.dto.Dto;
import com.nanny.model.AttributeBean;
import com.nanny.model.AttributesBean;
import com.nanny.model.ShopProAttribute;
import com.nanny.model.ShopProduct;
import com.nanny.model.SupplierProduct;
import com.nanny.util.DateUtil;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class  SupplierProductBizImpl implements SupplierProductBiz {
	
	@Resource
	private SSHUtilDao dao;
	

	@Override
	public JSONObject dosupro(JSONObject bean,PageUtil pageUtil) {
		
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  $");
		sql.append(" FROM supplier_product a ");
		sql.append(" LEFT JOIN  shop_pro_type b ON b.id=a.typeID ");
		sql.append(" LEFT JOIN  shop_pro_brand c ON c.id=a.brandID ");
		sql.append(" WHERE 1=1 ");
		sql.append(" and a.supplierID=?");
		Object name = bean.get("name");
		if( name !=null&&!name.toString().trim().isEmpty()){
			sql.append("and a.name like '%"+ bean.getString("name")+"%' ");
		}
		
		Object fatyId = bean.get("typeid1");
		Object typeId = bean.get("typeid2");
		if(typeId != null && !typeId.toString().trim().isEmpty()&&!"-1".equals(typeId)){
			sql.append(" and b.id ="+bean.getLong("typeid2"));//类别
		}else if(fatyId != null && !fatyId.toString().trim().isEmpty()&&!"-1".equals(fatyId)){
			sql.append(" and b.parID ="+bean.getLong("typeid1"));//类别
		}
		
		Object brid = bean.get("brid");
		if(brid != null && !brid.toString().trim().isEmpty()&&!"-1".equals(brid)){
			sql.append(" and a.brandID ="+bean.getLong("brid"));
		}
		sql.append(" ORDER BY a.id ");
		Object[] queryParam={bean.getInt("suID")};
		JSONArray product=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", "a.*,b.name AS tname,c.name AS bName"), queryParam, pageUtil));
		
		//2.查询总条数
		JSONArray count=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", "count(a.id) as total"), queryParam, null));
		
		for(int i=0;i<product.size();i++){
			JSONObject objt=product.getJSONObject(i);
			objt=TimeUtil.transTimeStamp(objt, "yyyy-MM-dd HH:mm:ss", "createTime");
			if(objt.getInt("isUse")==Dto.IS_SALE)
				objt.element("stats", "上架");
			else 
				objt.element("stats", "下架");
			
			
		}
		
		JSONObject obj=new JSONObject();
		obj.element("nowPage", pageUtil.getPageIndex());
		obj.element("product", product);
		obj.element("total", count.getJSONObject(0).getInt("total"));
		obj.element("size", pageUtil.getPageSize());
		
		
		return obj;
	}


	@Override
	public JSONObject getshoplist(JSONObject bean, PageUtil pageUtil) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  $");
		sql.append(" FROM shop_product a ");
		sql.append(" LEFT JOIN  shop_pro_type b ON b.id=a.typeID ");
		sql.append(" LEFT JOIN  shop_pro_brand c ON c.id=a.brandID ");
		sql.append(" WHERE 1=1 ");
		sql.append(" and a.shopID=0 and a.proCode NOT IN (SELECT proCode FROM supplier_product WHERE supplierID =?) ");
		Object name = bean.get("name");
		if( name !=null&&!name.toString().trim().isEmpty()){
			sql.append("and a.name like '%"+ bean.getString("name")+"%' ");
		}
		
		Object fatyId = bean.get("typeid1");
		Object typeId = bean.get("typeid2");
		if(typeId != null && !typeId.toString().trim().isEmpty()&&!"-1".equals(typeId)){
			sql.append(" and b.id ="+bean.getLong("typeid2"));//类别
		}else if(fatyId != null && !fatyId.toString().trim().isEmpty()&&!"-1".equals(fatyId)){
			sql.append(" and b.parID ="+bean.getLong("typeid1"));//类别
		}
		
		Object brid = bean.get("brid");
		if(brid != null && !brid.toString().trim().isEmpty()&&!"-1".equals(brid)){
			sql.append(" and a.brandID ="+bean.getLong("brid"));
		}
		sql.append(" ORDER BY a.id ");
		Object[] queryParam={bean.getInt("suID")};
		JSONArray product=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", "a.*,b.name AS tname,c.name AS bName"), queryParam, pageUtil));
		
		//2.查询总条数
		JSONArray count=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString().replace("$", "count(a.id) as total"), queryParam, null));
		
		for(int i=0;i<product.size();i++){
			JSONObject objt=product.getJSONObject(i);
			objt=TimeUtil.transTimeStamp(objt, "yyyy-MM-dd HH:mm:ss", "createTime");
			if(objt.getInt("isUse")==Dto.IS_SALE)
				objt.element("stats", "上架");
			else 
				objt.element("stats", "下架");
			
			
		}
		
		JSONObject obj=new JSONObject();
		obj.element("nowPage", pageUtil.getPageIndex());
		obj.element("product", product);
		obj.element("total", count.getJSONObject(0).getInt("total"));
		obj.element("size", pageUtil.getPageSize());
		
		
		return obj;
	}
	
	
	
	
	
	@Override
	public JSONArray dosuedpro(int proid) {
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.*,b.name AS tname,c.name AS brName,b.parID ");
		sql.append("FROM supplier_product a ");
		sql.append("LEFT JOIN  shop_pro_type b ON b.id=a.typeID ");
		sql.append("LEFT JOIN  shop_pro_brand c ON c.id=a.brandID ");
		sql.append("WHERE a.id=?");
		
		Object[] queryParam={proid};
		JSONArray product=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), queryParam, null));
		
		return product;
	}




	@Override
	public String updatebatchin(List<String> ids,List<String> prices) {
		StringBuffer sql=new StringBuffer();
		
			sql.append("UPDATE supplier_product ");

			sql.append(" SET  price = CASE id ");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" WHEN " + ids.get(i) + " THEN  "
						+ prices.get(i));

			}
			sql.append(" END ");
			sql.append(" WHERE id=? ");
			for (int i = 1; i < ids.size(); i++) {
				sql.append(" OR id=" + ids.get(i));
			}
			Object[] queryParam = { ids.get(0) };
			dao.updateObjectBySQL(sql.toString(), queryParam);
		
		return Dto.SUCCEED;
	}


	@Override
	public String updatebatchis(List<String> ids,String isUse) {
		StringBuffer sql=new StringBuffer();
		
			sql.append("UPDATE supplier_product ");
			sql.append(" SET isUse = "+isUse);
			sql.append(" WHERE id=? ");
			for (int i = 1; i < ids.size(); i++) {
				sql.append(" OR id=" + ids.get(i));
			}
			Object[] queryParam = { ids.get(0) };
			dao.updateObjectBySQL(sql.toString(), queryParam);
		
		return Dto.SUCCEED;
		
	}


	@Override
	public String delbatchpro(List<String> ids) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete FROM supplier_product ");
		sql.append(" WHERE id=? ");
		for (int i = 1; i < ids.size(); i++) {
			sql.append(" OR id=" + ids.get(i));
		}
		Object[] queryParam = { ids.get(0) };
		dao.updateObjectBySQL(sql.toString(), queryParam);

		return Dto.SUCCEED;
	}


	@Override
	public String start_supgoods(List<String> ids,int supplierId) {
		for(String id:ids){
			String sql = "select * from shop_product where id=? ";
			List<ShopProduct> shops = (List<ShopProduct>) dao.getBeanListBySQL(sql, new Object[]{id}, ShopProduct.class);
			ShopProduct sp = shops.get(0);
			
			SupplierProduct s = new SupplierProduct();
				s.setName(sp.getName());
				s.setProCode(sp.getProCode());
				s.setPrice(sp.getPrice());
				s.setSupplierId(Long.valueOf(supplierId));
				s.setInventory(0);//shop.getInventory()
				s.setCover(sp.getCover());
				s.setBrandId(sp.getBrandId());
				s.setTypeId(sp.getTypeId());
				s.setIsUse(0);
				s.setIsCommission(sp.getIsCommission());
				s.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
				s.setMemo(sp.getMemo());
				s.setIsRecommond(sp.getIsRecommond());
				
			String sql1 = "SELECT id FROM supplier_product WHERE proCode = ? and supplierID  = ?";
			List<String> oneList = dao.getOneList(sql1, new Object[]{sp.getProCode(),supplierId});
			if(oneList.size()==0){
				dao.saveObject(s);
			}
		}
		return Dto.SUCCEED;
	}



	

}
