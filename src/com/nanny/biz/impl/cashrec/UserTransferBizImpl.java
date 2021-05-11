package com.nanny.biz.impl.cashrec;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.cashrec.UserTransferBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserCashApply;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Service("userTransferBiz")
@Transactional
public class UserTransferBizImpl implements UserTransferBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Long addNewTransferApp(UserCashApply bean,Long shopID) {
		//0.添加新的提现记录
		dao.saveObject(bean);
		//1.更新用户/商户余额
		String sql="";
		if(shopID!=null){
			sql=" call upDateShopBalance(?,?,?,@balance)";
			Object[] par={shopID.intValue(),shopID.intValue(),Double.valueOf("-"+bean.getMoney())};
			JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, par, null));
			bean.setMemo(array.getJSONObject(0).getString("nowBalance"));
		}else{
			sql=" call upDateUserBalance(?,?,?,@balance)";
			Object[] par={bean.getUserId().intValue(),bean.getUserId().intValue(),Double.valueOf("-"+bean.getMoney())};
			JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, par, null));
			bean.setMemo(array.getJSONObject(0).getString("nowBalance"));
		}
		return bean.getId();
	}

	public JSONObject getTransRec(Long userID, PageUtil pageUtil,JSONObject condition) {
		String selectParam="uca.*,ua.account,ua.accName as recName,sb.bankName";//要查询的字段
		//0.拼接sql
		String sql=" select $" +
				   " from user_cash_apply uca,user_account ua,sys_banks sb" +
				   " where uca.accID=ua.id and ua.bankID=sb.id and uca.userID=?";
		if(condition.containsKey("start"))
			sql+=" and uca.createTime>'"+condition.getString("start")+"'";
		if(condition.containsKey("end"))
			sql+=" and uca.createTime<'"+condition.getString("end")+"'";
		sql+=" order by uca.id desc";
		
		//1.查询数据
		Object[] param={userID.intValue()};
		JSONArray totalCount=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", "count(uca.id) as count"), param, null));
		JSONArray data=JSONArray.fromObject(dao.getObjectListBySQL(sql.replace("$", selectParam), param, pageUtil));
		
		//2.组织数据
		JSONObject dataObj=new JSONObject();
		dataObj.element("totalCount", totalCount.getJSONObject(0).getInt("count"));
		dataObj.element("data", dataStateDefine(data));
		dataObj.element("nowPage", pageUtil.getPageIndex());
		return dataObj;
	}

	
	/**
	 * 记录状态判读
	 * @param array
	 * @return
	 */
	private JSONArray dataStateDefine(JSONArray array){
		for(int i=0;i<array.size();i++)
		{
			JSONObject obj=array.getJSONObject(i);
			//0.时间装换
			obj=TimeUtil.transTimeStamp(obj, "yyyy-MM-dd HH:mm", "createTime");
			
			//1.状态判读
			if(obj.getInt("status")==Dto.TRANSFER_WAIT_CHECK)
				obj.element("statuTxt", "待审核");
			else if(obj.getInt("status")==Dto.TRANSFER_PASS_CHECK)
				obj.element("statuTxt", "已发放");
			else
				obj.element("statuTxt", "未通过");
		}
		return array;
	}

	public JSONObject getTransRecByID(Long appID) {
		String sql=" select uca.*,bu.tel as userTel,bs.id as shopID from user_cash_apply uca" +
				   " inner join base_users bu on bu.id=uca.userID" +
				   " left join base_shop bs on bu.id=bs.userID and bs.situation=0" +
				   " where uca.id=?";
		Object[] param={appID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0);
	}
}
