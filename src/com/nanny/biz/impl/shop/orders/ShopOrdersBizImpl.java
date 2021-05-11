package com.nanny.biz.impl.shop.orders;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.aopdel.GlobalCashRecBiz;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.biz.user.UsersOrderBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SystemSetup;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service("shopOrdersBiz")
public class ShopOrdersBizImpl implements ShopOrdersBiz {

	@Resource
	private SSHUtilDao dao;
	@Resource
	private UsersOrderBiz uob;
	
	@Resource(name="globalCashRecBiz")
	private GlobalCashRecBiz g_CashBiz;
	
	public JSONObject getOrdersIdByCon(JSONObject condition,PageUtil pageUtil){
		String sql=" select $ from base_orders bo,base_users bu" +
				   " where bo.recShopID=? and bo.payUserID=bu.id";
		JSONArray result;
		JSONArray totalCount;
		if(!("").equals(condition.getString("status"))){
			if(("a").equals(condition.getString("status")))
				sql+=" and bo.status>2";
			else
				sql+=" and bo.status="+condition.getInt("status");
		}
		if(!("").equals(condition.getString("orderCode")) ||
		   !("").equals(condition.getString("userName")) ||
		   !("").equals(condition.getString("userTel")))
		{
			sql+=" and (bo.recName=? or bo.recTel=? or bo.orderCode=?)";
			Object[] param={condition.getInt("shopID"),
							condition.getString("userName"),
							condition.getString("userTel"),
							condition.getString("orderCode")};
			result=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", " bo.id,bo.orderCode,bo.totalPrice,bo.fee,bo.address,bo.recName,bo.recTel,bo.createTime,bo.`status`,bo.memo,bo.vcID")+" order by bo.id desc", param, pageUtil));
			totalCount=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", " count(bo.id) as count"), param, null));
		}else{
			Object[] param={condition.getInt("shopID")};
			result=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", " bo.id,bo.orderCode,bo.totalPrice,bo.fee,bo.address,bo.recName,bo.recTel,bo.createTime,bo.`status`,bo.memo,bo.vcID")+" order by bo.id desc", param, pageUtil));
			totalCount=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", " count(bo.id) as count"), param, null));
		}
		
		//返回数据
		JSONObject data=new JSONObject();
		data.element("nowPage", pageUtil.getPageIndex());
		data.element("data", TimeUtil.transTimestamp(result, "createTime"));
		data.element("total", totalCount.getJSONObject(0).getInt("count"));
		return data;
	}
	
	public JSONObject getOrderBaseByOrderID(Long orderId) {
		String sql=" select bo.*,bs.shop_name,bs.memo as shopTel,bs.shop_des,bs.userID as shopUserID" +
				   " from base_orders bo,base_shop bs" +
				   " where bo.id=? and bs.id=bo.recShopID";
		Object[] param={orderId.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return TimeUtil.transTimeStamp(array.getJSONObject(0), "yyyy-MM-dd HH:mm:ss", "createTime");
	}
	
	public JSONObject getOrderBaseByOrderCode(String code){
		String sql=" select * from base_orders where orderCode=?";
		Object[] param={code};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return TimeUtil.transTimeStamp(array.getJSONObject(0), "yyyy-MM-dd HH:mm:ss", "createTime");
	}
	
	public JSONArray getShopOrdersByOrderID(Long orderID) {
		//0.组织sql
		String sql=" select bod.id,bod.proID,bod.count,bod.price,sp.`name`,sp.cover,sp.isExchange,bod.memo,sp.price as orginPrice" +
				   " from base_order_detail bod,shop_product sp" +
				   " where bod.proID=sp.id and bod.orderID=?";
		Object[] param={orderID.intValue()};
		//1.查询数据
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		
		 SystemSetup sts=(SystemSetup) dao.getObjectById(SystemSetup.class, 1L);
    		
			 double cash=sts.getCash();
			 for(int j=0;j<array.size();j++){
				int point=0;
				if(array.getJSONObject(j).get("memo").toString().equals("1")){
					double productprice=array.getJSONObject(j).getDouble("price");
					point= (int) (productprice/cash);
				}
				array.getJSONObject(j).element("point", point);
			}
		return array;
	}

	public void updateOrderSta(Long orderID, int status) {
		String sql=" update base_orders set status=? where id=?";
		Object[] param={status,orderID.intValue()};
		dao.updateObjectBySQL(sql, param);
		
		//更新积分记录表商品兑换状态
		String sql1="UPDATE user_point_redeem SET memo=1 WHERE orderID=?";
		Object[] param1={orderID.intValue()};
		dao.updateObjectBySQL(sql1, param1);
		
	}

	public void updateOrderStaForRef(JSONObject param) { 
		//0.更新订单状态
		String sql=" update base_orders set status=?,shopmsg=? where id=?";
		Object[] par1={param.getInt("status"),param.containsKey("shopmsg")?param.getString("shopmsg"):"",param.getInt("orderID")};
		dao.updateObjectBySQL(sql, par1);
		//1.更新用户余额
		sql=" call upDateUserBalance(?,?,?,@balance)";
		Object[] par2={param.getInt("userID"),param.getInt("userID"),param.getDouble("cost")};
		dao.updateObjectBySQL(sql, par2);
		//2.更新商户余额
		sql=" call upDateShopBalance(?,?,?,@balance)";
		Object[] par3={param.getInt("shopID"),param.getInt("shopID"),Double.valueOf("-"+param.getString("cost"))};
		dao.updateObjectBySQL(sql, par3);
		//3.更新退款申请记录
		sql=" update user_cash_refund set status=1 where orderCode=?";
		Object[] par4={param.getString("orderCode")};
		dao.updateObjectBySQL(sql, par4);
	}

	public void updateOrderStaForRefOfRecPay(JSONObject param) {
		//0.更新订单状态
		String sql=" update base_orders set status=?,shopmsg=? where id=?";
		Object[] par1={param.getInt("status"),param.getString("shopmsg"),param.getInt("orderID")};
		dao.updateObjectBySQL(sql, par1);
		//2.更新退款申请记录
		sql=" update user_cash_refund set status=1 where orderCode=?";
		Object[] par4={param.getString("orderCode")};
		dao.updateObjectBySQL(sql, par4);
	}
	
	public void updateOrderStaForOverTime(String limitTime) {
		/*String sql=" update base_orders set status=? where status=? and createTime<?";
		Object[] param={Dto.STATU_DONE,Dto.STATU_WAITREV,limitTime};
		dao.updateObjectBySQL(sql, param);*/
		
		String sql1=" select id,payUserID from base_orders  where status=? and createTime<?";
		Object[] param1={Dto.STATU_WAITREV,limitTime};
		JSONArray bo=JSONArray.fromObject(dao.getObjectListBySQL(sql1, param1,null));
		for (int i = 0; i < bo.size(); i++) {
			uob.updateShoporder(bo.getJSONObject(i).getLong("id"), bo.getJSONObject(i).getLong("payUserID"));
		}
		
	}
	
	
	public void updateOrderStaForNoPay(){
		String sql=" select payUserID as userID,recShopID as shopID,id as orderID from base_orders where `status`=?";
		Object[] param={Dto.STATU_WAITPAY};
		JSONArray data=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		for(int i=0;i<data.size();i++)
		{
			sql=" update base_orders set status=? where id=?";
			Object[] param2={Dto.STATU_REFUND,data.getJSONObject(i).getInt("orderID")};
			dao.updateObjectBySQL(sql, param2);
			
			//返还积分
			g_CashBiz.delPointRec(data.getJSONObject(i));
		}
		
//		String sql=" update base_orders set status=? where status=?";
//		Object[] param={Dto.STATU_REFUND,Dto.STATU_WAITPAY};
//		dao.updateObjectBySQL(sql, param);
	}

	public int getCountOfWaitDetOrders(Long shopID) {
		String sql=" select count(id) as total from base_orders where recShopID=? and status=?";
		Object[] param={shopID.intValue(),Dto.STATU_WAITSEND};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("total");
	}

	public int getCountOfWaitDetOrders(String time, Long shopID) {
		String sql=" select count(id) as total from base_orders where recShopID=? and status=? and createTime>=?";
		Object[] param={shopID.intValue(),Dto.STATU_WAITSEND,time};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("total");
	}

	public JSONObject getOrderMsgForWXMail(String orderCode) {
		String sql=" select sp.`name`,bo.* " +
				   " from base_order_detail bod,base_orders bo,shop_product sp " +
				   " where bod.orderID=bo.id and sp.id=bod.proID and bo.ordercode=? limit 1";
		Object[] param={orderCode};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()>0)
			return TimeUtil.transTimeStamp(array.getJSONObject(0), "yyyy-MM-dd HH:mm:ss", "createTime");
		else
			return null;
	}

	public JSONArray getTodayOrderedShopList(String star,String end) {
		String sql=" select bo.orderCode,bo.recShopID as shopID,bs.shop_name,bs.userID" +
				   " from base_orders bo,base_shop bs" +
				   " where bo.createTime>? and bo.createTime<?" +
				   " and bo.recShopID=bs.id and bs.isSubsidy=1 and bo.`status`=3" +
				   " and (select count(id) from base_orders where createTime>? and createTime<?)>0" +
				   " group by bo.recShopID";
		Object[] param={star,end,star,end};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	public int getTodyFirstOrderCount(Long shopID,String star,String end) {
		String sql=" select count(id) as count from ( select id from base_orders where recShopID=? and `status`=3 and createTime>? and createTime<? group by payUserID ) t";
		Object[] param={shopID.intValue(),star,end};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("count");
	}

	public int getRefundCountByOrderID(Long orderID) {
		String sql=" select count(ucr.id) as count" +
				   " from base_orders bo,user_cash_refund ucr" +
				   " where bo.orderCode=ucr.orderCode and ucr.status=0 and bo.id=?";
		Object[] param={orderID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("count");
	}
}
