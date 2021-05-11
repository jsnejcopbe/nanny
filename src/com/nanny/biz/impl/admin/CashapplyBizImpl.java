package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.admin.CashapplyBiz;
import com.nanny.model.UserCashApply;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("CashapplyBiz")
public class CashapplyBizImpl implements CashapplyBiz {

	// 载入资源
	@Resource
	private SSHUtilDao g_BaseDao;

	@Override
	public JSONObject getAdminCashApplyByCondition(JSONObject condition,PageUtil pageUtil) {
		JSONObject all = new JSONObject();
		JSONArray data = new JSONArray();
		JSONArray count = new JSONArray();
		StringBuffer sql = new StringBuffer();
		String list ="uca.id,bu.nickName,uca.money,bs.shop_name,uca.createTime,ua.accName,bu.tel,uca.status,ua.account,sa.bankName";
		sql.append("select  $ " +
				" from user_cash_apply uca");
		sql.append(" LEFT JOIN base_users bu ON uca.userID=bu.id LEFT JOIN user_account ua ON uca.userID=ua.userID LEFT JOIN sys_banks sa ON ua.bankID=sa.id LEFT JOIN base_shop bs ON bs.userID=bu.id");
		if (condition.containsKey("status")) {
			sql.append(" where uca.status=?");
			if (condition.containsKey("tel")) {
				sql.append(" and bu.tel=?");
				if (condition.containsKey("logmin")) {
					sql.append(" and uca.createTime  >=?");
					if (condition.containsKey("logmax")) {
						sql.append(" and uca.createTime  <=?");
						Object[] param = { condition.getString("status"),condition.getString("tel"),condition.getString("logmin"),condition.getString("logmax") };
						data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
				           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
				           all.element("data", data);
				           all.element("count", count.getJSONObject(0).getInt("count"));
					} else {
						Object[] param = { condition.getString("status"),condition.getString("tel"),condition.getString("logmin")};
						data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
				           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
				           all.element("data", data);
				           all.element("count", count.getJSONObject(0).getInt("count"));
					}

				} else if (condition.containsKey("logmax")) {
					sql.append(" and uca.createTime  <=?");
					Object[] param = { condition.getString("status"),condition.getString("tel"),condition.getString("logmax") };
					data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
			           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
			           all.element("data", data);
			           all.element("count", count.getJSONObject(0).getInt("count"));
				} else {
					Object[] param = { condition.getString("status"),condition.getString("tel") };
					data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
			           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
			           all.element("data", data);
			           all.element("count", count.getJSONObject(0).getInt("count"));
				}

			} else {
				if (condition.containsKey("logmin")) {
					sql.append(" and uca.createTime  >=?");
					if (condition.containsKey("logmax")) {
						sql.append(" and uca.createTime  <=?");
						Object[] param = { condition.getString("status"),condition.getString("logmin"),condition.getString("logmax") };
						data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
				           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
				           all.element("data", data);
				           all.element("count", count.getJSONObject(0).getInt("count"));
					}else{
						Object[] param = { condition.getString("status"),condition.getString("logmin")};
						data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
				           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
				           all.element("data", data);
				           all.element("count", count.getJSONObject(0).getInt("count"));
					}

				}else if (condition.containsKey("logmax")) {
					sql.append(" and uca.createTime  <=?");
					Object[] param = { condition.getString("status"),condition.getString("logmax") };
					data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
			           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
			           all.element("data", data);
			           all.element("count", count.getJSONObject(0).getInt("count"));
				}else {
					Object[] param = { condition.getString("status")};
					data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
			           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
			           all.element("data", data);
			           all.element("count", count.getJSONObject(0).getInt("count"));
				}

			}

		} else if (condition.containsKey("tel")) {
			sql.append(" where bu.tel=?");
			if (condition.containsKey("logmin")) {
				sql.append(" and uca.createTime  >=?");
				if (condition.containsKey("logmax")) {
					sql.append(" and uca.createTime  <=?");
					Object[] param = { condition.getString("tel"),
							condition.getString("logmin"),
							condition.getString("logmax") };
					data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
			           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
			           all.element("data", data);
			           all.element("count", count.getJSONObject(0).getInt("count"));
				} else {
					Object[] param = { condition.getString("tel"),
							condition.getString("logmin") };
					data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
			           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
			           all.element("data", data);
			           all.element("count", count.getJSONObject(0).getInt("count"));
				}
			} else if (condition.containsKey("logmax")) {
				sql.append(" and uca.createTime  <=?");
				Object[] param = { condition.getString("tel"),
						condition.getString("logmax") };
				data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
		           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
		           all.element("data", data);
		           all.element("count", count.getJSONObject(0).getInt("count"));
			} else {
				Object[] param = { condition.getString("tel") };
				data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
		           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
		           all.element("data", data);
		           all.element("count", count.getJSONObject(0).getInt("count"));
			}
		} else if (condition.containsKey("logmin")) {
			sql.append(" where uca.createTime  >=?");
			if (condition.containsKey("logmax")) {
				sql.append(" and uca.createTime  <=?");
				Object[] param = { condition.getString("logmin"),
						condition.getString("logmax") };
				data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
		           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
		           all.element("data", data);
		           all.element("count", count.getJSONObject(0).getInt("count"));
			} else {
				Object[] param = { condition.getString("logmin") };
				data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list), param, pageUtil));
		           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
		           all.element("data", data);
		           all.element("count", count.getJSONObject(0).getInt("count"));
			}
		} else if (condition.containsKey("logmax")) {
			sql.append(" where uca.createTime  <=?");
			Object[] param = { condition.getString("logmax") };
           data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", list)+" order bt uca.id desc", param, pageUtil));
           count = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(sql.toString().replace("$", "count(uca.id) as count"), param, null)); 
           all.element("data", data);
           all.element("count", count.getJSONObject(0).getInt("count"));
		} else {
			return null;
		}

		return all;
	}
	
	@Override
	public JSONArray getAdminCashApply(PageUtil pageUtil) {
		// sql
		String sql = " select uca.id,bu.nickName,uca.money,bs.shop_name,uca.createTime,ua.accName,bu.tel,uca.status,ua.account,sa.bankName  " +
					 " from user_cash_apply uca "+ 
					 " LEFT JOIN base_users bu ON uca.userID=bu.id " +
					 " LEFT JOIN base_shop bs ON bs.userID=bu.id" +
					 " LEFT JOIN user_account ua ON uca.userID=ua.userID" +
					 " LEFT JOIN sys_banks sa ON ua.bankID=sa.id" +
					 " order by uca.id desc";
		Object[] queryParam = {};
		JSONArray data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(
				sql.toString(), queryParam, pageUtil));

		return data;
	}

	@Override
	public int getAdminCashApplycount(PageUtil pageUtil) {
		String sql = "select userID,money,createTime,accName,accID,tel,status  from user_cash_apply";
		Object[] queryParam = {};
		JSONArray data = JSONArray.fromObject(g_BaseDao.getObjectListBySQL(
				sql.toString(), queryParam, pageUtil));

		String colName = "id";
		int sdas = g_BaseDao.getObjectCount(UserCashApply.class, colName, null);
		return sdas;
	}

}
