package com.nanny.biz.impl.shop;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.login.LoginBiz;
import com.nanny.biz.shop.ShopBasis;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;
import com.nanny.model.ShopDispatching;
import com.nanny.model.ShopNotice;
import com.nanny.model.ShopOfficehours;
import com.nanny.model.ShopProduct;
import com.nanny.model.SysGlobalArea;
import com.nanny.model.SysShopApply;
import com.nanny.util.BasisUtil;
import com.nanny.util.DateUtil;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.GbkTransUtil;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.WeixinUserMsgPush;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/** 商户申请、列表
 * @author syl
 *
 */
@Service
@Transactional
public class ShopApplyImpl implements ShopBasis{
	@Resource 
	private SSHUtilDao dao;
	@Resource 
	private LoginBiz loginBiz;
	
	private static final String PASS = "1";
	private static final String NOPASS = "2";
	private static final String DEFAULT = "/nanny/work/pc/images/defalut.jpg";
	
	
	@Override
	public String getPassword(String id) {
		String sql = "select ";
		return null;
	}

	//======================================================================================================
	/** 验证店名，电话是否重复
	 * @author syl
	 *
	 */
	@Override
	public String apply(SysShopApply bean,HttpSession session) {
		String strs[] = new String[]{bean.getName(),bean.getTel(),bean.getShopName()};
		if(BasisUtil.save_empty_verify(strs))return Dto.NULL_ERROR;
		if(isExist(bean.getShopName()))return Dto.EXIST_ERROR;
		if(isExistTel(bean.getTel())){
			if(!isShop(bean.getUserId().intValue()))return Dto.EXIST_ERROR+1;
		}
		bean.setState(Dto.WAIT_OK);
		bean.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		dao.saveObject(bean);
		//session.removeAttribute(Dto.SHOP_ADD_TYPE);
		return Dto.SUCCEED;
	}

	@Override
	public String apply_init_list(Map<String, String> mapstr,String salesman_id) {
		StringBuffer sb = new StringBuffer("select * from sys_shop_apply where 1=1 ");
		String many = mapstr.get("many");
		String state = mapstr.get("state");
		//System.out.println(mapstr);
		if(many !=null && !many.trim().isEmpty())sb.append(" and (name like '%"+many+"%' or shopName like '%"+many+"%' or tel like '%"+many+"%' or weixinAcc like '%"+many+"%') ");
		if(state !=null && !Dto.ERROR_CODE.equals(state))sb.append(" and state = "+state+" ");
		if(salesman_id != null) sb.append(" and memo = '"+salesman_id+"' ");
		String total = sb.toString().replace("*", " COUNT(1) ");
		String page = mapstr.get("now_page");
		String row = mapstr.get("row");
//		String order = mapstr.get("order");
//		String orderType = mapstr.get("orderType");
		String sql = BasisUtil.getSql(sb, null, "createTime", "desc", page, row);
		//System.out.println(sql);
		List<Map<String, String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(sql, null, null);
		for(Map<String, String> map:datalist){
			Object obj = map.get("state");
			map.put("state", Dto.getShopState(obj.toString()));
		}
		return JsonUtil.getJson(BasisUtil.myTableHelp(dao.getTotal(total, null), datalist));
	}
	
	
	
	/** 总后台通过开店申请
	 * @author syl
	 *
	 */
	private String password;
	@Override
	public String apply_change_state(String type,String tel,String id) throws UnsupportedEncodingException {
		String q_sql = "select * from sys_shop_apply where id = ?";
		List<SysShopApply> list = (List<SysShopApply>) dao.getBeanListBySQL(q_sql, new Object[]{id}, SysShopApply.class);
		if(tel != null && !tel.trim().isEmpty()){
			if(!isExistTel(tel)){
				if(!help_save(list.get(0),type))return Dto.EXIST_ERROR;
				String u_sql = "UPDATE sys_shop_apply SET state = ? WHERE id = ?";
				dao.updateObjectBySQL(u_sql, new Object[]{type,id});
				return password;
			}else{
				//如果用户电话存在 则升级为店家
				if(doCreate_shop(list.get(0),type)){
				String sql = "UPDATE sys_shop_apply SET state = ? WHERE id = ?";
				dao.updateObjectBySQL(sql, new Object[]{type,id});
				return password;
				}else
					return Dto.EXIST_ERROR+1;
			}
		}else{
			return Dto.NULL_ERROR;
		}
	}
	
	/** 插入店铺数据
	 * @author syl
	 * @throws UnsupportedEncodingException 
	 *
	 */
	private boolean help_save(SysShopApply shop,String type) throws UnsupportedEncodingException{
		//0.重复检查
		if(NOPASS.equals(type))return true;
		if(isExist(shop.getShopName()))return false;
		
		//1.插入用户数据
		BaseUsers bu = new BaseUsers();
		BaseShop bs = new BaseShop();
		bu.setNickName(shop.getName());
		bu.setTel(shop.getTel());
		bu.setPassword(BasisUtil.getRandomStr(6));
		bu.setBalance(0d);
		bu.setPoint(0);
		bu.setIsAdmin(0);
		bu.setRecShopId(0L);
		bu.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		dao.saveObject(bu);//添加用户
		password = bu.getPassword();//临时密码
		
		//2.插入商户数据
		bs.setUserId(bu.getId());
		bs.setShopName(shop.getShopName());
		bs.setDeliveryPrice(0d);
		bs.setMinSendPrice(0d);
		bs.setShopIcon(DEFAULT);
		bs.setBalance(0d);
		bs.setStatus(0);
		bs.setIsHeadShop(1);
		bs.setSituation(0);
		bs.setMemo(shop.getTel());//店铺电话
		bs.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		bs.setShopDes(getQrcodeUrl(bu.getId()));
		bs.setIsSubsidy(0);
		bs.setPrinterType(1);
		bs.setIsTransfer(0);
		bs.setIsVouchers(0);
		
		if(shop.getMemo()!=null && shop.getMemo().indexOf("shop")==-1){
			bs.setSalmanId(Long.valueOf(find_guid(shop.getMemo())));
			bs.setItroShopId((long) 0);
		}else{
			bs.setSalmanId((long) 0);
			bs.setItroShopId(Long.valueOf(shop.getMemo().split("_")[1]));
		}
//		else{
//			bs.setSalmanId((long) 0);
//			bs.setItroShopId((long) 0);
//		}
		long shopid= (Long) dao.saveObject(bs);
		
		//3.初始化商户营业时间
		ShopOfficehours hoBean=new ShopOfficehours();
		hoBean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		hoBean.setEndTime("23:59");
		hoBean.setShopId(shopid);
		hoBean.setStartTime("09:00");
		dao.saveObject(hoBean);
		
		//4.推荐商品自动铺货
		distribution(shopid);
		return true;
	}

	/**用户存在下，升级为店家
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public boolean doCreate_shop(SysShopApply shop,String type) throws UnsupportedEncodingException {
		if(NOPASS.equals(type))return true;
		if(isExist(shop.getShopName()))return false;
		
		JSONObject users=loginBiz.getLoginUserMsg(shop.getTel());
		
		if(!isShop(users.getInt("userID")))return false;
		
		//0.插入店铺数据
		BaseShop bs = new BaseShop();
		bs.setUserId(users.getLong("userID"));
		bs.setShopName(shop.getShopName());
		bs.setDeliveryPrice(0d);
		bs.setMinSendPrice(0d);
		bs.setShopIcon(DEFAULT);
		bs.setBalance(users.getDouble("balance"));
		bs.setStatus(0);
		bs.setIsHeadShop(1);
		bs.setSituation(0);
		bs.setMemo(shop.getTel());//店铺电话
		bs.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		bs.setShopDes(getQrcodeUrl(users.getLong("userID")));
		bs.setPrinterType(1);
		bs.setIsSubsidy(0);
		bs.setIsTransfer(0);
		bs.setIsVouchers(0);
		
		password =users.getString("password");
		
		
		
		if(shop.getMemo()!=null){
			bs.setSalmanId(Long.valueOf(find_guid(shop.getMemo())));
			bs.setItroShopId((long) 0);
		}else{
			bs.setSalmanId((long) 0);
			bs.setItroShopId(Long.valueOf(shop.getMemo().split("_")[1]));
		}
		long shopid= (Long) dao.saveObject(bs);
		
		//1.初始化商户营业时间
		ShopOfficehours hoBean=new ShopOfficehours();
		hoBean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		hoBean.setEndTime("23:59");
		hoBean.setShopId(shopid);
		hoBean.setStartTime("09:00");
		dao.saveObject(hoBean);
		
		//2.推荐商品铺货
		distribution(shopid);
		
		//3.更新用户余额
		String sql="UPDATE `base_users` SET balance =0  WHERE id = ?";
		Object[] queryParam={users.getInt("userID")};
		dao.updateObjectBySQL(sql, queryParam);
		
		return true;
		
	}
	
	
	
	/**
	 * 新店铺铺货
	 * @param shopid
	 */
	public void distribution(long shopid) {
		String sql="SELECT  *  FROM shop_product WHERE isRecommond=1 AND shopID=0";
		Object[] queryParam={};
		JSONArray sp= JSONArray.fromObject(dao.getObjectListBySQL(sql, queryParam, null));
		for(int i=0;i<sp.size();i++){
			JSONObject bean=sp.getJSONObject(i);
			
			ShopProduct spro=new ShopProduct();
			spro.setShopId(shopid);
			spro.setBrandId(bean.getLong("brandID"));
			spro.setTypeId(bean.getLong("typeID"));
			spro.setName(bean.getString("name"));
			spro.setProCode(bean.getString("proCode"));
			spro.setPrice(bean.getDouble("price"));
			spro.setCostPrice(bean.getDouble("costPrice"));
			spro.setDisPrice(bean.getDouble("disPrice"));
			spro.setInventory(bean.getInt("inventory"));
			spro.setCover(bean.getString("cover"));
			spro.setViewCount(bean.getInt("viewCount"));
			spro.setBuyCount(bean.getInt("buyCount"));
			spro.setCreateTime(DateUtils.getTimestamp());
			spro.setIsCommission(bean.getInt("isCommission"));
			spro.setIsUse(bean.getInt("isUse"));
//			spro.setIsUse(1);
			spro.setProDes(bean.getString("proDes"));
			spro.setProTag(bean.getString("proTag"));
			spro.setIsRecommond(bean.getInt("isRecommond"));
			spro.setIsExchange(0);
			dao.saveObject(spro);
			
		}
	}
	
	
	
	private boolean isShop(int userid){
		String sql = "SELECT id FROM base_shop WHERE userID = ?";
		List<String> oneList = dao.getOneList(sql, new Object[]{userid});
		if(oneList.size()>0)return false;
		return true;
	}
	
	private boolean isExist(String name){
		String sql = "SELECT id FROM base_shop WHERE shop_name = ?";
		List<String> oneList = dao.getOneList(sql, new Object[]{name});
		if(oneList.size()>0)return true;
		return false;
	}
	
	private boolean isExistTel(String name){
		String sql = "SELECT id FROM base_users WHERE tel = ?";
		List<String> oneList = dao.getOneList(sql, new Object[]{name});
		if(oneList.size()>0)return true;
		return false;
	}
	
	private boolean isExistTel0(String name){
		String sql = "SELECT id FROM sys_shop_apply WHERE tel = ?";
		List<String> oneList = dao.getOneList(sql, new Object[]{name});
		if(oneList.size()>0)return true;
		return false;
	}
	
	private String find_guid(String guid) {
		String sql = "select id from base_saleman where guid =?";
		return dao.getTotal(sql, new Object[]{guid});
	}
	
	//店铺基本信息
	//=================================================================================================
	@Override
	public String baseinfo_init(String userID) {
		String sql = "SELECT bs.*,bu.`tel`,sn.`title`,sn.`con`,sga.`detAdd`,sga.addName FROM `base_shop` AS bs LEFT JOIN `base_users` AS bu ON bs.`userID` = bu.`id` LEFT JOIN `shop_notice` AS sn ON bs.`id` = sn.`shopID` LEFT JOIN `sys_global_area` AS sga ON sga.`id` = bs.`adressID`  WHERE bs.`userID` = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{userID}, null));
	}
	
	@Override
	public String notice_init(String id) {
		String sql = "SELECT title,con FROM shop_notice WHERE shopID = ? AND id = (SELECT MAX(id) FROM shop_notice  WHERE shopID = ?)";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{id,id}, null));
	}

	@Override
	public String notice_edit(String id, String data) {
		
		String sql1="select count(id) as count from shop_notice WHERE shopID=?";
		Object[] queryParam1={Integer.valueOf(id)};
		JSONArray  notice =JSONArray.fromObject( dao.getObjectListBySQL(sql1, queryParam1, null));
		
		if(notice.getJSONObject(0).getInt("count")>0){
			String sql2="UPDATE shop_notice SET con=? WHERE shopID=?";
			Object[] queryParam2={data,Integer.valueOf(id)};
			dao.updateObjectBySQL(sql2, queryParam2);
		}else{
			
			ShopNotice sn=new ShopNotice();
			sn.setShopId(Long.valueOf(id));
			sn.setCon(data);
			sn.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
			dao.saveObject(sn);
			
		}
	
		return Dto.SUCCEED;
	}
	
	@Override
	public String runtime_init(String id) {
		String sql1 = "select * from shop_officehours where shopID = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql1, new Object[]{id}, null));
	}

	@Override
	public String runtime_edit(String id, String time1, String time2,String time_id) {
		ShopOfficehours so = new ShopOfficehours();
		System.out.println(time_id);
		so.setId((time_id==null || time_id.trim().isEmpty()) ? null : Long.valueOf(time_id));
		so.setShopId(Long.valueOf(id));
		so.setStartTime(time1);
		so.setEndTime(time2);
		so.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		if( time_id==null || time_id.trim().isEmpty()) dao.saveObject(so);
		else dao.updateObject(so);
		return Dto.SUCCEED;
	}
	
	@Override
	public String adressAdd(SysGlobalArea bean,String shop_id) { 
		System.out.println(bean.getCity());
		bean.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		try {
			bean.setMemo(GbkTransUtil.getPinYinHeadChar(bean.getCity()));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bean.getId() == null)dao.saveObject(bean);
		else dao.updateObject(bean);
		if(shop_id == null || shop_id.trim().isEmpty())return Dto.SUCCEED;
		String sql = "UPDATE  `base_shop` SET adressID = ? WHERE id = ?";
		dao.updateObjectBySQL(sql, new Object[]{bean.getId()+"",shop_id});
		return Dto.SUCCEED;
	}
	
	@Override
	public String adressEdit(String adressID,String shopID) {
		String sql = "UPDATE `base_shop` SET adressID = ? WHERE id = ?";
		dao.updateObjectBySQL(sql, new Object[]{adressID,shopID});
		return Dto.SUCCEED;
	}

	private List<String> adressExist(String str){
		String sql = "select id from sys_global_area where addName = ?";
		return dao.getOneList(sql, new Object[]{str});
	}
	
	@Override
	public String getAdress() {
		String sql = "SELECT * FROM sys_global_area order by createTime DESC";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, null, null));
	}

	@Override
	public String getProvince() {
		String sql = "SELECT DISTINCT province AS s_value, province AS s_text FROM sys_global_area";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, null, null));
	}

	@Override
	public String getCity(String province) {
		String sql = "SELECT DISTINCT city AS s_value, city AS s_text FROM sys_global_area where province = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{province}, null));
	}

	@Override
	public String scopeAdd(String shopID, List<String> list) {
		if(list==null || list.size() <= 0)return Dto.ONE_ERROR;
		int i = 0;
		for(String s:list){
			if(isExist(shopID, s)){
				i = i+1;
				continue;
			}
			else{
				ShopDispatching sd = new ShopDispatching();
				sd.setShopId(Long.valueOf(shopID));
				sd.setadressID(Long.valueOf(s));
				sd.setFee((double)0);
				sd.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
				dao.saveObject(sd);
			}
		}
		return Dto.SUCCEED+i;
	}

	private boolean isExist(String id1,String id2){
		String sql = "select id from shop_dispatching where shopID = ? and adressID = ?";
		return dao.getOneList(sql, new Object[]{id1,id2}).size() > 0 ? true : false;
	}
	
	@Override
	public String getScopelist(String id,Map<String, String> map) {
		String address = null;
		if(map != null)address = map.get("address");
		StringBuffer sb = new StringBuffer("SELECT sga.* FROM `shop_dispatching` AS sd JOIN `sys_global_area` AS sga ON sd.`adressID` = sga.`id` WHERE shopID = ? ");
		if(address !=null && !address.trim().isEmpty())sb.append(" and ( sga.detAdd like '%"+address+"%' or sga.addName like '%"+address+"%' )");
		//System.out.println(sb.toString());
		List<Map<String,String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(sb.toString(), new Object[]{id}, null);
		return JsonUtil.getJson(BasisUtil.myTableHelp(null, datalist));
	}
	
	@Override
	public String getAdresslist(String id,Map<String, String> map) {
		String address = null;
		if(map != null)address = map.get("address");
		StringBuffer sb = new StringBuffer("SELECT * FROM sys_global_area where 1=1  ");
		if(address !=null && !address.trim().isEmpty())sb.append("and addName like '%"+address+"%' ");
		if(id !=null) sb.append(" AND id NOT IN (SELECT adressID FROM shop_dispatching WHERE shopID = ?) ");
		sb.append("  order by createTime desc ");
		//System.out.println(sb.toString());
		Object obj[] = id == null ? null : new Object[]{id};
		List<Map<String,String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(sb.toString(), obj, null);
		return JsonUtil.getJson(BasisUtil.myTableHelp(null, datalist));
	}

	@Override
	public String delRemove(String id,String adressID) {
		String sql = "DELETE FROM `shop_dispatching` WHERE shopID = ? AND adressID = ?";
		dao.updateObjectBySQL(sql, new Object[]{id,adressID});
		return Dto.SUCCEED;
	}

	@Override
	public String getShopAdress(String data) {
		StringBuffer sb = new StringBuffer("SELECT bs.*,sga.`detAdd`,sga.`addName` FROM `base_shop` AS bs LEFT JOIN `sys_global_area` AS sga ON bs.`adressID` = sga.`id` where 1=1 ");
		if(data != null && !data.trim().isEmpty())  sb.append("and (sga.detAdd like '%"+data+"%' or sga.addName like '%"+data+"%' )");
		sb.append(" ORDER BY bs.createTime DESC ");
		return JsonUtil.getJson(dao.getObjectListBySQL(sb.toString(), null, null));
	}

	@Override
	public String change_headimg(String url,String shopID) {
		String sql = "UPDATE base_shop set shop_icon = ? where id = ?";
		dao.updateObjectBySQL(sql, new Object[]{url,shopID});
		return Dto.SUCCEED;
	}

	@Override
	public String change_price(String type,String value, String shopID) {
		String sql = "UPDATE base_shop set "+type+" = ? where id = ?";
		dao.updateObjectBySQL(sql, new Object[]{value,shopID});
		return Dto.SUCCEED;
	}

	@Override
	public String runtime_remove(String time_id, String shopID) {
		String sql = "DELETE FROM shop_officehours WHERE id = ? ";
		dao.updateObjectBySQL(sql, new Object[]{time_id});
		return Dto.SUCCEED;
	}

	@Override
	public JSONObject closeshop(String shopID) {
	//拼接sql
		String sql = "UPDATE `base_shop` SET status =1  WHERE id = ?";
		dao.updateObjectBySQL(sql, new Object[]{shopID});
		String sql2 = "SELECT status from base_shop where id =?";
		//封装参数
		Object[] param = {shopID};
		JSONArray aUserArray = JSONArray.fromObject(dao.getObjectListBySQL(sql2, param, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;
	}
	@Override
	public JSONObject openshop(String shopID) {
	//拼接sql
		String sql = "UPDATE `base_shop` SET status =0  WHERE id = ?";
		dao.updateObjectBySQL(sql, new Object[]{shopID});
		String sql2 = "SELECT status from base_shop where id =?";
		//封装参数
		Object[] param = {shopID};
		JSONArray aUserArray = JSONArray.fromObject(dao.getObjectListBySQL(sql2, param, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;
	}

	@Override
	public String updateshoptel(String tel, int shopID) {
		String sql = "UPDATE `base_shop` SET memo =?  WHERE id = ?";
		dao.updateObjectBySQL(sql, new Object[]{tel,shopID});
		return Dto.SUCCEED;
	}
	
	public void updateShopUrl(Long shopID, String url) {
		String sql=" update base_shop set shop_des=? where id=?";
		Object[] param={url,shopID.intValue()};
		dao.updateObjectBySQL(sql, param);
	}
	
	/**
	 * 创建商户专用推广二维码链接
	 * @param userID
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getQrcodeUrl(Long userID) throws UnsupportedEncodingException{
		//0.拼接数据
		JSONObject data=new JSONObject();
		data.element("action_name", "QR_LIMIT_STR_SCENE");
		JSONObject passParam=new JSONObject();
		passParam.element("scene", (new JSONObject()).element("scene_str", userID.toString()));
		data.element("action_info", passParam);
		
		//1.获得ticket
		JSONObject ticket=WeixinUserMsgPush.getTicket(data);
		if(ticket.containsKey("errcode"))
		{
			System.out.println("create qrcode failed,error msg is"+ticket.getString("errmsg"));
			return null;
		}else
			return ticket.getString("url");
	}

}
