package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UserShopCarBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseOrderDetail;
import com.nanny.model.BaseOrders;
import com.nanny.model.BaseShop;
import com.nanny.model.SysVoucher;
import com.nanny.model.SystemSetup;
import com.nanny.model.UserPayMsg;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class UserShopCarBizImpl implements UserShopCarBiz {

	@Resource
	private SSHUtilDao sshUtilDao;
	
	@Override
	public JSONArray getshopList(int userID,int addressID) {
		// TODO Auto-generated method stub
		
		  	StringBuffer sql=new StringBuffer();
		  
		  		sql.append("SELECT a.id,a.shop_name,a.delivery_price,a.isVouchers,sd.fee FROM base_shop a  ");
		  		sql.append("INNER JOIN shop_dispatching sd ON sd.shopID=a.id and sd.adressID=?  ");
		  		sql.append("INNER JOIN user_shopping_car b ON b.shopID=a.id  ");
		  		sql.append("WHERE b.userID=? ");
		  		sql.append(" GROUP BY a.id");
		  		
		  	Object[] queryParam={addressID,userID};
			JSONArray shop=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, null));
		
			 SystemSetup sts=(SystemSetup) sshUtilDao.getObjectById(SystemSetup.class, 1L);
		for(int i=0;i<shop.size();i++){
        	   JSONObject bean=shop.getJSONObject(i);
        	   
        	   	StringBuffer sql1=new StringBuffer();
       			sql1.append("SELECT b.`name`,b.isExchange,b.price,a.count,a.shopID,a.proID,c.delivery_price AS deprice,a.memo FROM  user_shopping_car a   ");
       			sql1.append("INNER JOIN shop_product b on b.id=a.proID ");
       			sql1.append("INNER JOIN base_shop c on c.id=a.shopID ");
       			sql1.append("WHERE a.userID=? and a.shopID=?");
       			Object[] queryParam1={userID,bean.getInt("id")};
       			 JSONArray shopde=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql1.toString(), queryParam1, null));
       			
       			  
       		
       			 double cash=sts.getCash();
       			 for(int j=0;j<shopde.size();j++){
       				int point=0;
       				if(!shopde.getJSONObject(j).get("memo").toString().equals("null") && !shopde.getJSONObject(j).get("memo").toString().equals("0")){
       					double productprice=shopde.getJSONObject(j).getDouble("price");
       					point= (int) (productprice/cash);
       				}
       				shopde.getJSONObject(j).element("point", point);
       			}
       			
       			
       			  bean.element("shopde", shopde);
       			 
       			
           }
		
		return shop;
	}

	/*
	 * 订单保存，删除购物记录
	 * return 订单ID 数组
	 */
	@Override
	public JSONArray addShopOrder(JSONObject bean,int userID) {
		
		
	
			JSONArray sharry = bean.getJSONArray("shopId");
			JSONArray orIdarrey = new JSONArray();
			// 根据商铺ID 循环生成订单
			for (int j = 0; j < sharry.size(); j++) {
				BaseOrders order = new BaseOrders();
				JSONObject shar = sharry.getJSONObject(j);
				if(MathDelUtil.halfUp(shar.getDouble("tolal"))>0){
					 BaseShop be= (BaseShop) sshUtilDao.getObjectById(BaseShop.class, shar.getLong("id"));
					
					order.setCreateTime(DateUtils.getTimestamp());
	
					String code = TimeUtil.getNowDate("yyyyMMddHHmmssSS");
	
					order.setOrderCode(code);
					order.setPayUserId(Long.valueOf(userID));
					order.setRecName(bean.getString("recName"));
					order.setFee(be.getDeliveryPrice());
					String ress = bean.getString("address") + ","
							+ bean.getString("community") + ","
							+ bean.getString("doorplate");
	
					order.setAddress(ress);
					//order.setTotalPrice(bean.getDouble("total"));
					order.setStatus(Dto.STATU_WAITPAY);
					order.setRecTel(bean.getString("tel"));
					order.setRecShopId(shar.getLong("id"));
					Object costPrice = bean.get("masg");
					if(costPrice != null && !costPrice.toString().trim().isEmpty()){
						order.setMessage(bean.getString("masg"));
					}else{
						order.setMessage("没有留言");
					}
					long orId = (Long) sshUtilDao.saveObject(order);
					
					orIdarrey.add(orId);
					
					JSONArray arryd = shar.getJSONArray("arryData");
					// 循环存入商品详情
					double total=0;
					for (int i = 0; i < arryd.size(); i++) {
	
						JSONObject arr = arryd.getJSONObject(i);
						if (arr.getInt("count") != 0
								&& arr.getInt("sid") == shar.getInt("id")) {
							
							//如果商品为积分兑换（1）则不算入总价
							if(arr.getInt("isex")==0){
								total=total+arr.getDouble("price")*arr.getInt("count");
							}
	
							
							BaseOrderDetail detail = new BaseOrderDetail();
							detail.setCreateTime(DateUtils.getTimestamp());
							detail.setOrderId(orId);
							detail.setProId(arr.getLong("id"));
							detail.setPrice(arr.getDouble("price"));
							detail.setCount(arr.getInt("count"));
							detail.setMemo(arr.getString("isex"));
							
							sshUtilDao.saveObject(detail);
						}
					}
					
					
					//更新积分记录列表 中未完成兑换的商品
					int ii= new Long(orId).intValue();  
					String sql="UPDATE user_point_redeem SET orderID=? WHERE userID=? AND shopID=? AND memo=0 AND orderID IS NULL";
					Object[] queryParam={ii,userID,shar.getInt("id")};
					sshUtilDao.updateObjectBySQL(sql, queryParam);
					
					if(shar.getInt("vid")>0){
						SysVoucher sv=(SysVoucher) sshUtilDao.getObjectById(SysVoucher.class, shar.getLong("vid"));
						if(sv.getStatus()==Dto.VOUCHER_STATUS_0){
							double to=total+be.getDeliveryPrice()-sv.getMoney();
							System.out.println("订单额度："+to);
							order.setTotalPrice(to);
							sv.setStatus(Dto.VOUCHER_STATUS_1);//已使用
							sv.setUsageTime(DateUtils.getTimestamp());//使用时间
							sshUtilDao.updateObject(sv);
							order.setVcId(shar.getLong("vid"));
						}else{
							order.setVcId(0L);
							order.setTotalPrice(total+be.getDeliveryPrice());
						}
					}else{
						order.setVcId(0L);
						order.setTotalPrice(total+be.getDeliveryPrice());
					}
					
					
						
						
					sshUtilDao.updateObject(order);
					
				}
			}
			String sql = "DELETE FROM user_shopping_car WHERE userID=?";
			Object[] queryParam = { userID };
			sshUtilDao.updateObjectBySQL(sql, queryParam);

			return orIdarrey;
		
		
		
	}

	@Override
	public String delShopOrder(int userID) {
		String sql = "DELETE FROM user_shopping_car WHERE userID=?";
		Object[] queryParam = { userID };
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		return "清除成功";
	}

	@Override
	public JSONObject doPointRedeem(JSONObject obj) {
		JSONArray shopid=obj.getJSONArray("arryData");
		
		
		StringBuffer sql=new StringBuffer();
		sql.append("select a.proID,a.shopID,a.point,a.number,c.`name`,c.price from user_point_redeem a ");
		sql.append(" LEFT JOIN shop_product c ON c.id=a.proID ");
		sql.append(" WHERE a.userID=? $ and a.memo=0 AND orderID IS NULL");
		Object[] queryParam={obj.getInt("userID")};
		
		int lineNum=shopid.size();
		JSONArray arryData=new JSONArray();
		for(int i=0;i<lineNum;i++){
			JSONObject bb=new JSONObject();
			
			JSONArray upr=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "AND a.shopID="+shopid.getJSONObject(i).getInt("shopid")), queryParam, null));
			bb.element("shopid", shopid.getJSONObject(i).getInt("shopid"));
			bb.element("data", upr);
			if(upr.size()>0){
				
				arryData.element(bb);
			}
			
		
		}
		System.out.println(arryData);
		
		JSONObject oo=new JSONObject();
		oo.element("arryData", arryData);
		return oo;
	}

	@Override
	public String getshopcarvs(int userID) {
		String sql="select * from sys_voucher where userID=? and status=1 and to_days(usageTime)=to_days(now())";
		Object[] queryParam={userID};
		
		JSONArray vs=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, queryParam, null));
		if(vs.size()>0){
			return "1";
		}else{
			return "0";
		}
		
	}

	public JSONObject getUserPayMsg(Long userID,Double money) {
		//0.取得支付记录
		String sql=" select * from user_pay_msg where userID=? and totalMoney=? order by id desc";
		Object[] param={userID.intValue(),money};
		JSONArray data=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		if(data.size()>0)
		{
			JSONObject payData=data.getJSONObject(0);
			//1.清除支付记录
			sql=" delete from user_pay_msg where userID=?";
			Object[] param2={userID.intValue()};
			sshUtilDao.updateObjectBySQL(sql, param2);
			return payData;
		}else
			return null;
	}

	public void savePayMsg(UserPayMsg upm) {
		sshUtilDao.saveObject(upm);
		
	}
}
