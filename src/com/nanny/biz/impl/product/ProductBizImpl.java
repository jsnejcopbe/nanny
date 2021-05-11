package com.nanny.biz.impl.product;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.product.ProductBiz;
import com.nanny.dto.Dto;
import com.nanny.model.AttributeBean;
import com.nanny.model.AttributesBean;
import com.nanny.model.ShopProAttribute;
import com.nanny.model.ShopProduct;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;


/**
 * 商品管理
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Transactional
@Service
public class ProductBizImpl implements ProductBiz {
	@Resource
	private SSHUtilDao sshUtilDao;

	
	/**
	 * 商品信息
	 */
	@Override
	public JSONArray edProduct(int id) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.*,b.id AS s_value,b.name AS s_text,c.name AS brName,b.parID,c.id AS brandId ");
		sql.append("FROM shop_product a ");
		sql.append("LEFT JOIN  shop_pro_type b ON b.id=a.typeID ");
		sql.append("LEFT JOIN  shop_pro_brand c ON c.id=a.brandID ");
		sql.append("WHERE a.id=?");
		
		Object[] queryParam={id};
		JSONArray product=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, null));
		
		return product;
	}
	
	/*
	 * 商品添加
	 */
	@Override
	public String addProduct(JSONObject bean) { 
		 ShopProduct bean1 =new ShopProduct();
			ShopProAttribute bean2=new ShopProAttribute();
			if(!"".equals(bean.getString("name"))&&!"".equals(bean.getString("price"))&&!"".equals(bean.getString("inventory"))){
				String code=UUID.randomUUID().toString().replace("-", "");
				
				bean1.setName(bean.getString("name"));
				Object costPrice = bean.get("costPrice");
				if(costPrice != null && !costPrice.toString().trim().isEmpty()){
					bean1.setCostPrice(bean.getDouble("costPrice"));
				}else{
					bean1.setCostPrice(0D);
				}
				
				Object disPrice = bean.get("disPrice");
				if(disPrice != null && !disPrice.toString().trim().isEmpty()){
					bean1.setDisPrice(bean.getDouble("disPrice"));
				}else{
					bean1.setDisPrice(0D);
				}
				
				bean1.setPrice(bean.getDouble("price"));
				bean1.setInventory(bean.getInt("inventory"));
				bean1.setIsCommission(bean.getInt("isCommission"));
				bean1.setIsUse(bean.getInt("isUse"));
				
				bean1.setCover(bean.getString("cover"));
				
				
				Object proDes = bean.get("proDes");
				if(proDes != null && !proDes.toString().trim().isEmpty()){
					bean1.setProDes(bean.getString("proDes"));//描述
				}else{
					bean1.setProDes("");
				}
				
				/*bean.setProTag(proTag);//标签*/
				
				Object brandId = bean.get("brandId");
				if(brandId != null && !brandId.toString().trim().isEmpty()){
					bean1.setBrandId(bean.getLong("brandId"));
				}else{
					bean1.setBrandId(0L);
				}
				
				Object fatyId = bean.get("fatyId");
				Object typeId = bean.get("typeId");
				if(typeId != null && !typeId.toString().trim().isEmpty()&&!"-1".equals(typeId)){
					bean1.setTypeId(bean.getLong("typeId"));//类别
				}else if(fatyId != null && !fatyId.toString().trim().isEmpty()){
					bean1.setTypeId(bean.getLong("fatyId"));//类别
				}else{
					bean1.setTypeId(0L);
				}
				
					
				bean1.setProCode(code);//编号
				
				bean1.setShopId(bean.getLong("shopid"));
				
				bean1.setCreateTime(DateUtils.getTimestamp());
				bean1.setViewCount(0);
				bean1.setBuyCount(0);
				bean1.setIsRecommond(0);
				bean1.setIsExchange(0);
				sshUtilDao.saveObject(bean1);
				if(!"undefined".equals(bean.getString("data4"))){
				Object obj = JsonUtil.getRightBean(bean.getString("data4"), AttributeBean.class, AttributesBean.class);
				if(obj instanceof AttributeBean){
					AttributeBean attr = (AttributeBean) obj;
					bean2.setProCode(code);
					bean2.setAttrName(attr.getButeName());
					bean2.setAttrDes(attr.getButeDes());
					bean2.setCreateTime(DateUtils.getTimestamp());
					
					bean2.setShopId(bean.getLong("shopid"));
					
					sshUtilDao.saveObject(bean2);
				}else{
					AttributesBean atts = (AttributesBean) obj;
					List<String> buteNames = atts.getButeName();
					List<String> buteDess = atts.getButeDes();
					//循环插入商品属性表
					for(int i=0;i<atts.getButeName().size();i++){
						String buteName = buteNames.get(i);
						String buteDes = buteDess.get(i);
						
						ShopProAttribute saveBean=new ShopProAttribute();
						
						saveBean.setProCode(code);
						saveBean.setAttrName(buteName);
						saveBean.setAttrDes(buteDes);
						saveBean.setCreateTime(DateUtils.getTimestamp());
						
						saveBean.setShopId(bean.getLong("shopid"));
						
						sshUtilDao.saveObject(saveBean);
					}
				}
			  }
				return "success";
			}else{
				return "基础数据不完整，请填好在提交";
			}
		
	}

	/*
	 * 商品更新
	 */
	@Override
	public JSONArray updateProduct(JSONObject bean) {
		
	
		//更新商品表
		 ShopProduct bean1=(ShopProduct) sshUtilDao.getObjectById(ShopProduct.class,bean.getLong("id"));
		 	if(bean1.getShopId()>1){
		 	bean1.setName(bean.getString("name"));
			bean1.setDisPrice(bean.getDouble("disPrice"));
			bean1.setPrice(bean.getDouble("price"));
			bean1.setInventory(bean.getInt("inventory"));
			bean1.setIsCommission(bean.getInt("isCommission"));
			bean1.setIsUse(bean.getInt("isUse"));
			bean1.setCostPrice(0D);
			bean1.setCover(bean.getString("cover"));
			
			Object proDes = bean.get("proDes");
			if(proDes != null && !proDes.toString().trim().isEmpty()){
				bean1.setProDes(bean.getString("proDes"));//描述
			}else{
				bean1.setProDes(bean1.getProDes());
			}
			
			
			
			
			/*bean.setProTag(proTag);//标签*/
			Object brandId = bean.get("brandId");
			if(brandId != null && !brandId.toString().trim().isEmpty()){
				bean1.setBrandId(bean.getLong("brandId"));
			}else{
				bean1.setBrandId(bean1.getBrandId());
			}
			
			Object fatyId = bean.get("fatyId");
			Object typeId = bean.get("typeId");
			if(typeId != null && !typeId.toString().trim().isEmpty()&&!"-1".equals(typeId)){
				bean1.setTypeId(bean.getLong("typeId"));//类别
			}else if(fatyId != null && !fatyId.toString().trim().isEmpty()){
				bean1.setTypeId(bean.getLong("fatyId"));//类别
			}else{
				bean1.setTypeId(0L);
			}
					
			sshUtilDao.updateObject(bean1);
		 	}else{
		 		int bid;
		 		Object brandId = bean.get("brandId");
				if(brandId != null && !brandId.toString().trim().isEmpty()){
					bid= bean.getInt("brandId");
				}else{
					bid= Integer.valueOf(bean1.getBrandId().toString());
				}
				
					int tid;
				Object fatyId = bean.get("fatyId");
				Object typeId = bean.get("typeId");
				if(typeId != null && !typeId.toString().trim().isEmpty()&&!"-1".equals(typeId)){
					 tid= bean.getInt("typeId");//类别
				}else if(fatyId != null && !fatyId.toString().trim().isEmpty()&&!"-1".equals(fatyId)){
					tid= bean.getInt("fatyId");//类别
				}else{
					tid=Integer.valueOf(bean1.getTypeId().toString());
				}
				
				
				String des;
				Object proDes = bean.get("proDes");
				if(proDes != null && !proDes.toString().trim().isEmpty()){
					des=bean.getString("proDes");//描述
				}else{
					des=bean1.getProDes();
				}
				
//		 		String sql=" UPDATE shop_product SET  name=?,costPrice=?,price=?,inventory=?,cover=?,proDes=?,brandID=?,typeID=?,isUse=?,isCommission=? WHERE  proCode=?";
//		 		
//		 		Object[] queryParam={bean.getString("name"),bean.getDouble("costPrice"),bean.getDouble("price"),bean.getInt("inventory"),bean.getString("cover"),des,bid,tid,bean.getInt("isUse"),bean.getInt("isCommission"),bean1.getProCode()};
//		 		sshUtilDao.updateObjectBySQL(sql, queryParam);
				
				String sql=" UPDATE shop_product SET  name=?,costPrice=?,inventory=?,cover=?,proDes=?,brandID=?,typeID=?,isUse=?,isCommission=? WHERE  proCode=?";
		 		
		 		Object[] queryParam={bean.getString("name"),bean.getDouble("costPrice"),bean.getInt("inventory"),bean.getString("cover"),des,bid,tid,bean.getInt("isUse"),bean.getInt("isCommission"),bean1.getProCode()};
		 		sshUtilDao.updateObjectBySQL(sql, queryParam);
		 		
		 		/*****************************
		 		 *****************************/
		 		sql=" UPDATE shop_product SET  price=? WHERE  proCode=? and shopID=0";
		 		Object[] queryParam2={bean.getDouble("price"),bean1.getProCode()};
		 		sshUtilDao.updateObjectBySQL(sql, queryParam2);
		 		
		 	}
			//更新属性表
			if(!"undefined".equals(bean.getString("data4"))){
				Object obj = JsonUtil.getRightBean(bean.getString("data4"), AttributeBean.class, AttributesBean.class);
				
			ShopProAttribute bean2=new ShopProAttribute();
			
			String sql="delete from shop_pro_attribute where proCode=?";
			Object[] queryParam={bean1.getProCode()};
			sshUtilDao.updateObjectBySQL(sql, queryParam);
			
			if(obj instanceof AttributeBean){
				AttributeBean attr = (AttributeBean) obj;
				bean2.setProCode(bean1.getProCode());
				bean2.setAttrName(attr.getButeName());
				bean2.setAttrDes(attr.getButeDes());
				bean2.setCreateTime(DateUtils.getTimestamp());
				
				bean2.setShopId(bean1.getShopId());
				
				sshUtilDao.saveObject(bean2);
			}else{
				AttributesBean atts = (AttributesBean) obj;
				List<String> buteNames = atts.getButeName();
				List<String> buteDess = atts.getButeDes();
				//循环插入商品属性表
				for(int i=0;i<atts.getButeName().size();i++){
					String buteName = buteNames.get(i);
					String buteDes = buteDess.get(i);
					
					ShopProAttribute saveBean=new ShopProAttribute();
					
					saveBean.setProCode(bean1.getProCode());
					saveBean.setAttrName(buteName);
					saveBean.setAttrDes(buteDes);
					saveBean.setCreateTime(DateUtils.getTimestamp());
					
					saveBean.setShopId(bean1.getShopId());
					
					sshUtilDao.saveObject(saveBean);
				}
			}
		}
		return null;
	}
	
	
	/* 
	 * 批量更新库存
	 */
	@Override
	public String updatebatchin(List<String> ids, List<String> inventorys,List<String> prices,List<String> proCodes,String shopid) {
		StringBuffer sql=new StringBuffer();
		if ( !"0".equals(shopid)) {
			sql.append("UPDATE shop_product ");

			sql.append(" SET inventory = CASE id ");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" WHEN " + ids.get(i) + " THEN  "
						+ inventorys.get(i));

			}
			sql.append(" END ");
			sql.append("  ,price = CASE id ");
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
			sshUtilDao.updateObjectBySQL(sql.toString(), queryParam);
		} else {
			sql.append("UPDATE shop_product ");

			sql.append(" SET inventory = CASE proCode ");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" WHEN '" + proCodes.get(i) + "' THEN  "
						+ inventorys.get(i));
			}
			sql.append(" END ");
			sql.append("  ,price = CASE proCode ");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" WHEN '" + proCodes.get(i) + "' THEN  "
						+ prices.get(i));
			}
			sql.append(" END ");
			sql.append(" WHERE (proCode= '"+proCodes.get(0)+"'");
			for (int i = 1; i < ids.size(); i++) {
				sql.append(" OR proCode='" + proCodes.get(i)+"'");
			}
			sql.append(") and shopID=0"); 
			Object[] queryParam = {  };
			sshUtilDao.updateObjectBySQL(sql.toString(), queryParam);
		}
		return Dto.SUCCEED;
		
	}

	/* 
	 * 批量上下架
	 */
	@Override
	public String updatebatchis(List<String> ids, List<String> proCodes,
			String isUse, String shopid) {
		
		StringBuffer sql=new StringBuffer();
		if (!"0".equals(shopid)) {
			sql.append("UPDATE shop_product ");
			sql.append(" SET isUse = "+isUse);
			sql.append(" WHERE id=? ");
			for (int i = 1; i < ids.size(); i++) {
				sql.append(" OR id=" + ids.get(i));
			}
			Object[] queryParam = { ids.get(0) };
			sshUtilDao.updateObjectBySQL(sql.toString(), queryParam);
		} else {
			sql.append("UPDATE shop_product ");
			sql.append(" SET isUse ="+ isUse);
			sql.append(" WHERE proCode='"+proCodes.get(0)+"'");
			for (int i = 1; i < ids.size(); i++) {
				sql.append(" OR proCode='" + proCodes.get(i)+"'");
			}
			Object[] queryParam = {  };
			sshUtilDao.updateObjectBySQL(sql.toString(), queryParam);
		}
		return Dto.SUCCEED;
	}

	/* 
	 * 批量删除
	 */
	@Override
	public String delbatchpro(List<String> ids, List<String> proCodes,
			String shopid) {
		if (!"0".equals(shopid)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete FROM shop_product ");
			sql.append(" WHERE id=? ");
			for (int i = 1; i < ids.size(); i++) {
				sql.append(" OR id=" + ids.get(i));
			}
			Object[] queryParam = { ids.get(0) };
			sshUtilDao.updateObjectBySQL(sql.toString(), queryParam);
		} else {

			StringBuffer sql = new StringBuffer();
			sql.append("delete FROM shop_product ");
			sql.append(" WHERE proCode= '"+proCodes.get(0)+"'");
			for (int i = 1; i < proCodes.size(); i++) {
				sql.append(" OR proCode=" + proCodes.get(i));
			}
			Object[] queryParam = {  };
			sshUtilDao.updateObjectBySQL(sql.toString(), queryParam);
		}
		return Dto.SUCCEED;
	}

	@Override
	public JSONObject dodaynewpro(JSONObject obj, PageUtil pageUtil) {
		StringBuffer sql=new StringBuffer();
		 sql.append("SELECT  $  FROM shop_product AS sp ");
		 sql.append("LEFT JOIN `shop_pro_type` AS spt ON sp.`typeID` = spt.`id` ");
		 sql.append("LEFT JOIN `shop_pro_brand` AS spb ON spb.`id` = sp.`brandID`  ");
		 sql.append("where 1=1 AND sp.shopID=0 AND sp.isUse =0 ");
		 sql.append(" AND sp.proCode NOT IN (SELECT proCode FROM shop_product WHERE shopID="+ obj.getString("shopid")+" )");
		 
		 sql.append(" and to_days(now())-to_days(sp.createTime)="+obj.getInt("days"));
		 
		 Object more = obj.get("more");
		 if(more!=null && !more.toString().trim().isEmpty()){
			 sql.append(" and (sp.name like '%"+obj.getString("more")+"%'  or sp.proCode like '%"+obj.getString("more")+"%')");
			}
		 	
		 Object type2 = obj.get("type2");
		 Object type1 = obj.get("type1");
		 if(type2!=null && !Dto.ERROR_CODE.equals(obj.getString("type2"))){
				 sql.append(" and spt.id ="+obj.getString("type2"));
			}else if(type1!=null && !Dto.ERROR_CODE.equals(obj.getString("type1"))){
				 sql.append(" and spt.parID ="+obj.getString("type1"));
			}
			
		Object brand = obj.get("brand");
		if(brand!=null && !Dto.ERROR_CODE.equals(obj.getString("brand"))) sql.append(" and sp.brandID ="+obj.getString("brand"));
		 
		 
		 Object[] queryParam={};
		
		//1.查询数据
		 JSONArray product=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "sp.*,spt.`name` AS typeName, spb.`name` AS brandName,spb.icon"), queryParam, pageUtil));
		
		//2.查询总条数
		JSONArray count=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "count(sp.id) as total"), queryParam, null));
		
		
		for(int i=0;i<product.size();i++){
			JSONObject bean=product.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
		}
		
		JSONObject obje=new JSONObject();
		obje.element("nowPage", pageUtil.getPageIndex());
		obje.element("produc", product);
		obje.element("total", count.getJSONObject(0).getInt("total"));
		obje.element("size", pageUtil.getPageSize());
		return obje;
	}

	@Override
	public int dodaynewprocount() {
		String sql="SELECT  COUNT(id) as count  FROM shop_product WHERE to_days(now())=to_days(createTime) AND shopID=0";
		Object[] queryParam={};
		 JSONArray nwcount=JSONArray.fromObject( sshUtilDao.getObjectListBySQL(sql, queryParam, null));
		
		 return nwcount.getJSONObject(0).getInt("count");
		
		
	}

	public void updateProByCloum(JSONObject param) {
		String sql=" update shop_product set "+param.getString("colName")+"='"+param.getString("colValue")+"'";
		if(param.containsKey("proCode"))
			sql+=" where proCode='"+param.getString("proCode")+"' and shopID=0";
		else
			sql+=" where id="+param.getString("proID");
		sshUtilDao.updateObjectBySQL(sql, null);
	}

	public JSONArray getProMsgByCheckName(String name, Long shopID) {
		String sql=" select sp.id as proID,sp.`name`,sp.cover" +
				   " from base_shop bs,shop_product sp" +
				   " where sp.shopID=bs.id and sp.`name` like ? and bs.id=?" +
				   " LIMIT 9";
		Object[] param={"%"+name+"%",shopID.intValue()};
		JSONArray proList=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		
		System.out.println("param1 is "+param[0].toString()+",param2 is"+param[1].toString());
		
		return proList;
	}

}
