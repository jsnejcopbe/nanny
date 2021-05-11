package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.user.AdminadveritsBiz;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;

@Service
@Transactional
/**
 * 
 * @author admin
 *wzp
 */
public class adminadvertisImpl implements AdminadveritsBiz {
	@Resource
	private SSHUtilBiz sshUtilBiz;

	//查询全部,或有条件的查询
	@Override
	public JSONArray getweelcon(String id) {
		StringBuffer sql=new StringBuffer();
			sql.append("select * from user_wheel where 1=1");
		if (id!=null&&id!="") {
			sql.append(" and id="+id+"");
		}
	JSONArray array=JSONArray.fromObject(sshUtilBiz.getObjectListBySQL(sql.toString(), null, null));
		return array;
	}
	
  //分页
	@Override
	public JSONArray getweel(PageUtil pageUtil,String bname) {
		StringBuffer sql=new StringBuffer();
		sql.append("select uw.*,bs.shop_name,bs.id as bsid from user_wheel uw ,base_shop bs  WHERE 1=1");
		if (bname!=null) {
			sql.append(" and bs.id=uw.shopID AND bs.shop_name like '%"+bname+"%'");
		}
		else {
			sql.append(" and uw.shopID=bs.id");
		}
		JSONArray array=JSONArray.fromObject(sshUtilBiz.getObjectListBySQL(sql.toString(), null,pageUtil));;
		return array;
		
	}
	
	//添加
	@Override
	public void inertweel(Long bank,String imgStc,String jumpSrc,String isall,String isUse,String time) {
		String sql="insert into user_wheel  (shopID,imgStc,jumpSrc,isAllUser,isUser,cerateTime) value (?,?,?,?,?,?)";
		Object[] objects={bank.intValue(),imgStc,jumpSrc,isall,isUse,time};
		sshUtilBiz.updateObjectBySQL(sql, objects);
		
		
	}
	
	//查找全部商家
	@Override
	public JSONArray getbaseshop() {
		String sql1="select bs.shop_name,bs.id from base_shop bs";
		JSONArray baseshop=JSONArray.fromObject(sshUtilBiz.getObjectListBySQL(sql1, null, null));
		return baseshop;
	}
	
	//操作
	@Override
	public void operation(String Disid,String Enid,String Delid) {	
		String id="";
		String sql="";
      if (Disid!=null) {
    	 sql="update user_wheel set isUser=1 where id=?";
    	  id=Disid;
	}
	else {
		sql="update user_wheel set isUser=0 where id=?";
	    id=Enid;
	}
		
	if (Delid!=null) {
		 sql="DELETE from user_wheel where id=?";
	id=Delid;
	}
	Object[] objects={id};
	sshUtilBiz.updateObjectBySQL(sql, objects);
	}


	//保存编辑
	@Override
	public void updatewheel(String imgStc, String jumpSr, String isAllUser, String isUser,String time,Long wheelid,String shopID) {
		String sql="update user_wheel set imgStc=?,jumpSrc=?,isAllUser=?,isUser=?,cerateTime=?,shopID=? where id=?";
		Object[] objects={imgStc,jumpSr,isAllUser,isUser,time,shopID,wheelid.intValue()};
		sshUtilBiz.updateObjectBySQL(sql, objects);
	}
}
