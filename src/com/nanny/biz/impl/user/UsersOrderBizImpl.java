package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UsersOrderBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseOrders;
import com.nanny.model.BaseUsers;
import com.nanny.model.SysVoucher;
import com.nanny.model.SystemSetup;
import com.nanny.model.UserCashRefund;
import com.nanny.model.UserPointRedeem;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class UsersOrderBizImpl implements UsersOrderBiz {

	@Resource
	private SSHUtilDao sshUtilDao;
	
	@Override
	public JSONObject doUserorder(int Userid, String sta,String orid,PageUtil pageUtil) {

		StringBuffer sql=new StringBuffer();
		sql.append("SELECT $ FROM base_orders a ");
		sql.append("LEFT JOIN base_shop c on c.id=a.recShopID ");
		sql.append("LEFT JOIN sys_global_area b on b.id=c.adressID ");
		sql.append("LEFT JOIN base_users d on d.id=a.payUserID ");
		sql.append("WHERE  1=1  ");
		if(!("").equals(orid)&& orid!=null){
			sql.append("and a.id="+orid);
		}else{
			sql.append("and a.payUserID="+Userid);
		}
		
		if(!("").equals(sta)&& sta!=null){
			if(!("3").equals(sta)){
				sql.append(" and a.status="+sta);
			}else{
				sql.append(" and (a.status='3' or a.status='4') ");
			}
		}
		sql.append("  ORDER BY a.id DESC ");
		Object[] queryParam={};
		JSONArray order=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "a.id,a.payUserID,a.orderCode,a.recShopID,a.totalPrice,a.`status`,a.fee,a.address,a.message,a.recName,a.recTel,a.createTime,c.shop_name,c.shop_icon,b.detAdd,d.tel,a.vcID,a.memo"), queryParam, pageUtil));
		
		
		//2.查询总条数
		JSONArray count=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "count(a.id) as total"), queryParam, null));
		
		
		
		SystemSetup sts=(SystemSetup) sshUtilDao.getObjectById(SystemSetup.class, 1L);
		for(int i=0;i<order.size();i++){
			JSONObject bean=order.getJSONObject(i);
			
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			StringBuffer sql1=new StringBuffer();
			sql1.append("SELECT a.price,a.count,c.`name`,c.cover,c.isExchange,a.memo,a.orderID  FROM base_order_detail a  ");
			sql1.append("INNER JOIN shop_product c ON c.id=a.proID ");
			sql1.append("WHERE a.orderID=? ");
			Object[] queryParam1={bean.getInt("id")};
			JSONArray company=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql1.toString(), queryParam1, null));
		
   			 double cash=sts.getCash();
   			 for(int j=0;j<company.size();j++){
   				int point=0;
   				if(company.getJSONObject(j).get("memo").toString().equals("1")){
   					double productprice=company.getJSONObject(j).getDouble("price");
   					point= (int) (productprice/cash);
   				}
   				company.getJSONObject(j).element("point", point);
   			}
			
   			 bean.element("company", company);
			//抵用卷信息
   			 if(bean.getInt("vcID")>0){
   				 SysVoucher sv=(SysVoucher) sshUtilDao.getObjectById(SysVoucher.class, bean.getLong("vcID"));
   				bean.element("svMoney", sv.getMoney());
   				bean.element("svName", sv.getName());
   				bean.element("svCode", sv.getVouCode());
   			 }
   			 
			if(bean.getInt("status")==Dto.STATU_WAITPAY)
				bean.element("staName", "待付款");
			else if(bean.getInt("status")==Dto.STATU_WAITSEND)
				bean.element("staName", "待发货");
			else if(bean.getInt("status")==Dto.STATU_WAITREV)
				bean.element("staName", "已发货");
			else if(bean.getInt("status")==Dto.STATU_DONE)
				bean.element("staName", "交易完成");
			else if(bean.getInt("status")==Dto.STATU_REFUND)
				bean.element("staName", "订单取消");
			
		}		
		 JSONObject obj=new JSONObject();
			obj.element("nowPage", pageUtil.getPageIndex());
			obj.element("order", order);
			obj.element("total", count.getJSONObject(0).getInt("total"));
			obj.element("size", pageUtil.getPageSize());
		return obj;
	}

	@Override
	public String updateUserorder(JSONArray orid, long uid, double totalPrice,int point) {
		
		 BaseUsers user=(BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, uid);
		 
		//更新用户余额
		 user.setBalance(user.getBalance()-totalPrice);
		 
		 //更新用户积分
		 //user.setPoint(user.getPoint()-point);
		 
		 sshUtilDao.updateObject(user);
		 
		//更新订单状态
		 for(int i=0;i<orid.size();i++){
			 long bean=orid.getLong(i);
			 
			 System.out.println("the orid for pay:"+bean);
			 
			 BaseOrders order=(BaseOrders) sshUtilDao.getObjectById(BaseOrders.class, bean); 
			 order.setStatus(1);
			 sshUtilDao.updateObject(order);	
			 
			 System.out.println("the orderID after update action:"+order.getStatus());
			 
			 /*BaseShop  shop=(BaseShop) sshUtilDao.getObjectById(BaseShop.class,order.getRecShopId());
			 double tp=order.getTotalPrice()+shop.getBalance();
			 shop.setBalance(tp);
			 sshUtilDao.updateObject(shop);	*/
			 
			 String sql="update base_shop SET balance=balance+? where id=?";
			 Object[] queryParam={order.getTotalPrice(),order.getRecShopId().intValue()};
			 sshUtilDao.updateObjectBySQL(sql, queryParam);
			 
		}
		
		
		return "支付成功";
	}

	@Override
	public String updateShoporder(long orid,long userid) {
		BaseOrders order=(BaseOrders) sshUtilDao.getObjectById(BaseOrders.class, orid);
		 //确认收货
		if(order.getStatus()==Dto.STATU_WAITREV)
		{
			order.setStatus(Dto.STATU_DONE);
			
			
			BaseUsers user=(BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, userid);
			
			//用户账户积分增加
			/* String sql="SELECT integral FROM sys_return_points WHERE cash<=? ORDER BY id DESC";
		 
			 Object[] queryParam={order.getTotalPrice()};
			 
			 JSONArray  polist=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, queryParam, null));
			 int po=0;
			 if(polist.size()!=0){
				 po=polist.getJSONObject(0).getInt("integral");
			 }*/
			
			int po=opoint(order.getTotalPrice());
			user.setPoint(user.getPoint()+po);
			
			sshUtilDao.updateObject(user);
			sshUtilDao.updateObject(order);
		}
		return "成功收货";
	}
	
	
	//获取返还积分
	@Override
	public int opoint(double TotalPrice) {
			String sql="SELECT integral FROM sys_return_points WHERE cash<=? ORDER BY cash DESC";
		 
		 Object[] queryParam={TotalPrice};
		 
		 JSONArray  polist=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, queryParam, null));
		 if(polist.size()!=0){
			 return polist.getJSONObject(0).getInt("integral");
		 }else{
			 return 0;
		 }
	}
	
	
	
	

	@Override
	public String ordeRefund(long userid,long orid, String memo) {
		BaseOrders order=(BaseOrders) sshUtilDao.getObjectById(BaseOrders.class, orid);
		
		UserCashRefund cash=new UserCashRefund();
		
		cash.setCreateTime(DateUtils.getTimestamp());
		cash.setOrderCode(order.getOrderCode());
		cash.setUserId(userid);
		cash.setShopId(order.getRecShopId());
		cash.setStatus(0);
		if(memo!=null&&!"".equals(memo)){
				cash.setMemo(memo);
			}
		sshUtilDao.saveObject(cash);
		
		
		
		return "已提交取消订单申请，等待处理";
	}

	@Override
	public JSONArray cod(JSONArray orid,long userid) {
		JSONArray printList=new JSONArray();
		//BaseUsers bu=(BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, userid);
		
		int po=0;
		for(int i=0;i<orid.size();i++){
			long bean=orid.getLong(i);
			
			 JSONObject pg= PointGoods(bean);
			 
			 po=po+pg.getInt("gpoint");
			
			 BaseOrders order=(BaseOrders) sshUtilDao.getObjectById(BaseOrders.class, bean);
			 order.setStatus(1);
			 order.setMemo("货到付款");
			 sshUtilDao.updateObject(order);
			 
			 JSONObject tmpObj=new JSONObject();
			tmpObj.element("orderID", bean);
			tmpObj.element("shopID", order.getRecShopId());
			printList.element(tmpObj);
		}
		
		/*bu.setPoint(bu.getPoint()-po);
		sshUtilDao.updateObject(bu);*/
		return printList;
	}

	@Override
	public JSONObject PointGoods(long orid) {
		int ii= new Long(orid).intValue();  
		String sql="SELECT orderID,proID,price,count,memo from base_order_detail where orderID=?";
		
		Object[] queryParam={ii};
		
		JSONArray orde=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, queryParam, null));
		
		SystemSetup sst=(SystemSetup) sshUtilDao.getObjectById(SystemSetup.class, 1L);
		
		double gtotal=0;
		int gpoint=0;
		
		
		//订单中的积分商品
		JSONArray expro=new JSONArray(); 
		
		
		for(int i=0;i<orde.size();i++){
			JSONObject bean=orde.getJSONObject(i);
			JSONObject jsobj=new JSONObject();
			
			//ShopProduct sp=(ShopProduct) sshUtilDao.getObjectById(ShopProduct.class, bean.getLong("proID"));
			
			//是否积分商品
			if(!bean.get("memo").toString().equals("1")){
				gtotal=gtotal+bean.getDouble("price")*bean.getInt("count");
			}else{
				
				int po=(int) (bean.getDouble("price")/sst.getCash())*bean.getInt("count");
				gpoint+=po;
				//积分商品id
				jsobj.element("proID", bean.getLong("proID"));
				//数量
				jsobj.element("count", bean.getInt("count"));
				//积分
				jsobj.element("point", po);
				expro.add(jsobj);
			}
		}
		JSONObject obj=new JSONObject();
		obj.element("gtotal", gtotal);
		obj.element("gpoint", gpoint);
		obj.element("expro", expro);
		
		return obj;
	}

	public void updateUserPointForExc(UserPointRedeem bean) {
		BaseUsers bu=(BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, bean.getUserId());
	
		bu.setPoint(bu.getPoint()-bean.getPoint());
		sshUtilDao.updateObject(bu);
	}

	
}
