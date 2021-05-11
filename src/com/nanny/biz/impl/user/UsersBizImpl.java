package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.user.UsersBiz;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserReceiveAdd;
import com.nanny.model.UserSign;
import com.nanny.model.UserViewShopRec;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;


/**
 * 用户个人中心
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Transactional
@Service
public class UsersBizImpl implements UsersBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
	
	/*
	 * 个人资料更新
	 */
	@Override
	public String updateUsers(JSONObject bean) {
		 BaseUsers  users=(BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, bean.getLong("id"));
		
		 users.setHeadImg(bean.getString("logoSrc"));
		 users.setSex(bean.getString("sex"));
		 users.setNickName(bean.getString("name"));
		 
		 
		 Object qq = bean.get("qq");
		 if(qq != null && !qq.toString().trim().isEmpty()){
			users.setQq(bean.getString("qq"));
		 }
		 Object mail = bean.get("mail");
		 if(mail != null && !mail.toString().trim().isEmpty()){
			 users.setMail(bean.getString("mail"));
		 }
		 
		 Object birthdate = bean.get("birthdate");
		 if(birthdate != null && !birthdate.toString().trim().isEmpty()){
			 users.setBirthdate(DateUtils.str2Timestamp(bean.getString("birthdate")));
		 }
		 
		 
		 
		 
		 sshUtilDao.updateObject(users);
		 
		return "success";
	}

	/* 
	 * 收货地址展示
	 */
	@Override
	public JSONArray doUseraddress(int Userid) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.id,a.address,a.recName,a.tel,a.memo,a.community,a.doorplate FROM  user_receive_add a  ");
		sql.append("INNER JOIN base_users b on b.id=a.userID ");
		sql.append("WHERE b.id=?");
		
		Object[] queryParam={Userid};
		JSONArray address=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, null));
		
		return address;

	}

	/*
	 * 新增地址
	 */
	@Override
	public String addUseraddress(JSONObject bean) {
		UserReceiveAdd receive=new UserReceiveAdd();
		
		receive.setAddress(bean.getString("address"));
		receive.setCreateTime(DateUtils.getTimestamp());
		receive.setMemo(bean.getString("areaID"));//areaid
		receive.setRecName(bean.getString("recName"));
		receive.setTel(bean.getString("tel"));
		receive.setUserId(bean.getLong("uid"));
		receive.setCommunity(bean.getString("community"));
		receive.setDoorplate(bean.getString("doorplate"));

		sshUtilDao.saveObject(receive);
		
		
		BaseUsers bu=(BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, bean.getLong("uid"));
		
		String sql = " select  *  from base_users where tel=? ";
		// 封装参数
		Object[] queryParam = {bean.getString("tel")};
		JSONArray aUserArray = JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, queryParam,null));
		
		if(bu.getTel()==null&&aUserArray.size()==0){
			bu.setTel(bean.getString("tel"));
			sshUtilDao.updateObject(bu);
		}
		
		
		
		
		return "success";
	}

	/* 
	 * 编辑地址
	 */
	@Override
	public String updateUseraddress(JSONObject bean) {
		 UserReceiveAdd rece=(UserReceiveAdd) sshUtilDao.getObjectById(UserReceiveAdd.class, bean.getLong("id"));
		 
		 rece.setAddress(bean.getString("address"));
		 rece.setCreateTime(DateUtils.getTimestamp());
		 rece.setMemo(bean.getString("areaID"));//areaid
		 rece.setRecName(bean.getString("recName"));
		 rece.setTel(bean.getString("tel"));
		 rece.setCommunity(bean.getString("community"));
		 rece.setDoorplate(bean.getString("doorplate"));
		 
		 sshUtilDao.updateObject(rece);
		
		return "success";
	}

	/* 
	 * 删除地址
	 */
	@Override
	public String delUseraddress(long receid) {
		UserReceiveAdd receive=new UserReceiveAdd();
		receive.setId(receid);
		
		sshUtilDao.deleteObject(receive);
		
		return "success";
	}

	@Override
	public long douser(JSONObject bean) {
		  // 拼接sql
		String sql = " select  *  from base_users where tel=? ";
		// 封装参数
		Object[] queryParam = {bean.getString("tel")};
		JSONArray aUserArray = JSONArray.fromObject(sshUtilDao
				.getObjectListBySQL(sql, queryParam,null));
		if(aUserArray.size()>0){
			return aUserArray.getJSONObject(0).getLong("id");
		}else{
			BaseUsers user=new BaseUsers();
			user.setBalance(0D);
			user.setBirthdate(null);
			user.setCreateTime(DateUtils.getTimestamp());
			user.setHeadImg("/nanny/images/vddefault.png");
			user.setId(null);
			user.setIsAdmin(0);
			user.setMail(null);
			user.setNickName(bean.getString("recName"));
			user.setOrigin(null);
			user.setPassword(bean.getString("passw"));
			user.setPoint(0);
			user.setQq(null);
			user.setRecShopId(0L);
			user.setSex(null);
			user.setTel(bean.getString("tel"));
			
			 Object opid = bean.get("openId");
			 if(opid != null && !opid.toString().trim().isEmpty()){
				 user.setOriginId(bean.getString("openId"));
			 }
			
			long uid= (Long) sshUtilDao.saveObject(user);
			return uid;
		}		
	}

	@Override
	public JSONArray dousersign(int userId,PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT b.headImg,b.point,b.nickName,a.signTime,a.score,a.signCount FROM user_sign a ");
		sql.append("LEFT JOIN  base_users b on b.id=a.userID ");
		sql.append("WHERE a.userID=?");
		sql.append(" ORDER BY a.id DESC");
		
		Object[] queryParam={userId};
		
		JSONArray usign=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, pageUtil));
		
		for(int i=0;i<usign.size();i++){
			JSONObject bean=usign.getJSONObject(i);
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd", "signTime");
		}
		return usign;
	}

	@Override
	public void updateusersign(UserSign userSign, long userID) {
		
		//签到记录
		sshUtilDao.saveObject(userSign);
		//更新用户积分
		BaseUsers baus=  (BaseUsers) sshUtilDao.getObjectById(BaseUsers.class, userID);
		baus.setPoint(baus.getPoint()+userSign.getScore());
		sshUtilDao.updateObject(baus);
	}

	public void addUserViewShopRec(UserViewShopRec bean) {
		//0.删除旧记录
	}

	public void updateUserRecShop(String openID, Long shopID) {
		String sql=" update base_users set recShopID=? where originID=?";
		Object[] param={shopID.intValue(),openID};
		sshUtilDao.updateObjectBySQL(sql, param);
	}

	public int getUserPointByUserID(Long userID) {
		String sql=" select point from base_users where id=?";
		Object[] param={userID.intValue()};
		JSONArray array=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("point");
	}
	
	@Override
	public JSONObject doUserCoupon(int userid ,String sta,PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from sys_voucher where userID=?");
		
		if(sta!=null && !sta.equals("")){
			sql.append(" and status="+sta);
		}
		Object[]  param={userid};
		JSONArray arr=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), param, pageUtil));
		
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd", "endTime");
		}
		int count=Integer.parseInt(sshUtilDao.getTotal(sql.toString().replace(" * ", " count(*) "), param));
		JSONObject json=new JSONObject();
		json.element("arr", arr);
		json.element("pageIndex", pageUtil.getPageSize());
		json.element("pageSize",pageUtil.getPageIndex() );
		json.element("count", count);
		
		return json;
	}

}
