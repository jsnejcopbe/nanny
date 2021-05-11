package com.nanny.biz.global.bank.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.bank.BankBiz;
import com.nanny.dto.Dto;
import com.nanny.model.SysBanks;
import com.nanny.model.UserAccount;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class BankBizImpl implements BankBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
		
	@Override
	public JSONArray bankinfo(int userID) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.id as accID,a.accName,a.account,a.accType,a.createTime,a.bankID,b.isUse,b.bankName FROM user_account a ");
		sql.append("LEFT JOIN sys_banks b ON b.id=a.bankID ");
		sql.append("WHERE a.userID=?");
		Object[] queryParam={userID};
		JSONArray bank=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, null));
		
		
		return bank;
	}

	@Override
	public JSONArray banklist(QueryParam queryParam,PageUtil pageUtil) {
		JSONArray bankArray=JSONArray.fromObject( sshUtilDao.getObjectList(SysBanks.class, queryParam,pageUtil));
		
		for(int i=0;i<bankArray.size();i++){
			JSONObject bean=bankArray.getJSONObject(i);		
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			
			if(bean.getInt("isUse")==Dto.BANK_ISUSER)
				bean.element("stats", "开启");
			else 
				bean.element("stats", "关闭");
			
		}
		
		return bankArray;
	}

	
	@Override
	public String updatebank(int userID, int bankID, String bankcard,
			String name) {
		String sql="UPDATE user_account SET accName=?,account=?,bankID=? WHERE userID=?";
		Object[] queryParam={name,bankcard,bankID,userID};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		return "修改成功";
	}

	@Override
	public String addbank(UserAccount userAccount) {
		sshUtilDao.saveObject(userAccount);
		return "添加成功";
	}

	@Override
	public String addsysBank(SysBanks sysBanks) {
		sshUtilDao.saveObject(sysBanks);
		return  "添加成功";
	}

	@Override
	public void delbank(int userID) {
		String sql="DELETE FROM user_account WHERE userID=?";
		Object[] queryParam={userID};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
	}

}
