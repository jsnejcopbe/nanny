package com.nanny.biz.impl.login;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.nanny.biz.login.LoginBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseSaleman;
import com.nanny.model.BaseSupplier;
import com.zhuoan.ssh.dao.SSHUtilDao;


@Service("loginBiz")
@Transactional
public class LoginBizImpl implements LoginBiz {

	// 载入资源
	@Resource
	private SSHUtilDao g_BaseDao;
	
	private static final String VCODE = "验证码错误";
	private static final String TELCODE = "登录账号不存在";
	private static final String PASSWORDCODE = "登录密码错误";
	private static final String NULLCODE = "不能为空";

	@Override
	public String getSalesManMsg(BaseSaleman bs,String verifycode,String truecode,HttpSession session) {
		if(truecode == null || !truecode.toLowerCase().equals(verifycode.toLowerCase()))return VCODE;
		
		System.out.println("into next step6\n");
		
		if(bs.getTel() == null || bs.getTel().trim().isEmpty())return NULLCODE;
		
		System.out.println("into next step7\n");
		
		if(bs.getPassword() == null || bs.getPassword().trim().isEmpty())return NULLCODE;
		
		System.out.println("into next step8\n");
		
		String sql = "select id from base_saleman where tel=?";
		
		List<String> oneList = g_BaseDao.getOneList(sql, new Object[]{bs.getTel()});
		
		System.out.println("into next step9\n");
		
		if(oneList.size() == 0) return TELCODE;
		
		System.out.println("into next step10\n");
		
		String sql1 = "select * from base_saleman where tel=? and password=? ";
		@SuppressWarnings("unchecked")
		List<BaseSaleman> datalist1 = (List<BaseSaleman>) g_BaseDao.getBeanListBySQL(sql1, new Object[]{bs.getTel(),bs.getPassword()}, BaseSaleman.class);
		
		System.out.println("into next step11\n");
		
		if(datalist1.size() == 0) return PASSWORDCODE;
		
		System.out.println("into next step12\n");

		session.setAttribute(Dto.LOGIN_SALEMAN, datalist1.get(0));
		return Dto.SUCCEED;
	}

	/**
	 * 
	 * 
	 * 
	 * 查询登录用户信息
	 * 
	 */
	@Override
	public JSONObject getLoginUserMsg(String nickName, String password) {
		// 0.拼接sql
		String sql = " select bu.id as userID,bu.*,bs.id as shopID, bs.shop_name,bs.shop_icon,bs.min_send_price,bs.delivery_price,bs.status,bs.memo as shopTel" +
					 " from base_users bu " +
					 " LEFT  JOIN base_shop bs ON bu.id = bs.userID and bs.situation=? "+ 
					 " where password=? and (tel=? or mail=? or nickName=?)";
		// 1.封装参数
		Object[] queryParam = {Dto.SHOP_SITU,password,nickName,nickName,nickName};
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;

	}

	@Override
	public JSONObject getLoginUserMsg(String token) {
		       // 拼接sql
		String sql = " select bu.id as userID,bu.*,bs.id as shopID, bs.shop_name,bs.shop_icon,bs.min_send_price,bs.delivery_price,bs.status,bs.memo as shopTel" +
				 " from base_users bu " +
				 " LEFT  JOIN base_shop bs ON bu.id = bs.userID and bs.situation=? "+ 
				 " where (bu.token=? or bu.tel=?)";
		// 封装参数
		Object[] queryParam = {Dto.SHOP_SITU,token,token};
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao
				.getObjectListBySQL(sql, queryParam,null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;
		
	}

	@Override
	public JSONObject getLoginUserMsgByOpenId(String openId) {
		String sql=" select bu.id as userID,bu.*,bs.id as shopID, bs.shop_name,bs.shop_icon,bs.min_send_price,bs.delivery_price,bs.status,bs.memo as shopTel " +
				   " from base_users bu" +
				   " LEFT  JOIN base_shop bs ON bu.id = bs.userID and bs.situation=?" +
				   " where bu.originID=?";
		Object[] queryParam = {Dto.SHOP_SITU,openId};
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;
	}
	
	public JSONObject getLoginUserMsgByShopID(Long shopID){
		String sql=" select bu.id as userID,bu.*,bs.id as shopID, bs.shop_name,bs.shop_icon,bs.min_send_price,bs.delivery_price,bs.status,bs.memo as shopTel" +
				   " from base_shop bs,base_users bu" +
				   " where bu.id = bs.userID and bs.situation=? and bs.id=?";
		Object[] queryParam = {Dto.SHOP_SITU,shopID.intValue()};	
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;
	}

	
	@Override
	public void updateUserOpenId(String openId, Long userId) {
		//0.去除openID
		String sql=" update base_users set originID=null where originID=?";
		Object[] param={openId};
		g_BaseDao.updateObjectBySQL(sql, param);
		
		//1.更新新的openID
		sql=" update base_users set originID=? where id=?";
		Object[] param2={openId,userId.intValue()};
		g_BaseDao.updateObjectBySQL(sql, param2);
	}

	@Override
	public void updateSalesOpenId(String openId, Long userId) {
				System.out.println("update openid:"+openId+";userid="+userId);
				//0.去除openID
				String sql=" update base_saleman set memo=null where memo=?";
				Object[] param={openId};
				g_BaseDao.updateObjectBySQL(sql, param);
				System.out.println("delete openid  SUCCEED");
				
				//1.更新新的openID
				sql=" update base_saleman set memo=? where id=?";
				Object[] param2={openId,userId.intValue()};
				g_BaseDao.updateObjectBySQL(sql, param2);
				System.out.println("update openid SUCCEED");
	}

	@Override
	public JSONObject getLoginSalesMsg(String tel, String password) {
		String sql = "select * from base_saleman where tel=? and password=?";
	// 1.封装参数
	Object[] queryParam = {tel,password};
	JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
	if (aUserArray.size() > 0)
		return aUserArray.getJSONObject(0);
	else
		return null;
	}

	@Override
	public JSONObject getLoginSalesMsg(String tel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getLoginSalesMsgByOpenId(String openId) {
		String sql = "SELECT * from base_saleman WHERE memo=?";
		Object[] queryParam = {openId};
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
		if (aUserArray.size() > 0)
			return aUserArray.getJSONObject(0);
		else
			return null;
	}

	
	/**
	 * 
	 * 
	 * 
	 * 查询supplier用户信息
	 * 
	 */
	@Override
	public JSONObject getLoginSupplierMsg(String tel, String password) {

		String sql = " select bu.id as supplierID,bu.* from base_supplier bu where password=? and (tel=? or supplier_name=?)";
	// 1.封装参数
	Object[] queryParam = {password,tel,tel};
	JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
	if (aUserArray.size() > 0)
		return aUserArray.getJSONObject(0);
	else
		return null;
		
	}

	@Override
	public boolean getLoginSupplierMsg(String tel) {
		boolean b=false;
		String sql = " select bu.id as supplierID,bu.* from base_supplier bu where tel=?";
		// 1.封装参数
		Object[] queryParam = {tel};
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
		if (aUserArray.size() > 0){
			b=true;
		}
		
			return b;
	}
	
	@Override
	public boolean getLoginSupplierName(String supplier_name) {
		boolean b=false;
		String sql = " select bu.id as supplierID,bu.* from base_supplier bu where supplier_name=?";
		// 1.封装参数
		Object[] queryParam = {supplier_name};
		JSONArray aUserArray = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql, queryParam, null));
		if (aUserArray.size() > 0){
			b=true;
		}
		
			return b;
	}


	@Override
	public void addSupplier(BaseSupplier supplier) {
		
		g_BaseDao.saveObject(supplier);
	}

	@Override
	public void updateUserToken(String token, Long userId) {
		//0.去除token
		String sql=" update base_users set token=null where token=?";
		Object[] param={token};
		g_BaseDao.updateObjectBySQL(sql, param);
		
		//1.更新新的token
		sql=" update base_users set token=? where id=?";
		Object[] param2={token,userId.intValue()};
		g_BaseDao.updateObjectBySQL(sql, param2);
		
	}

	

}
