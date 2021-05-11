package com.nanny.biz.impl.admin;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nanny.biz.admin.ExpandList;
import com.nanny.dto.Dto;
import com.nanny.model.BaseSaleman;
import com.nanny.util.BasisUtil;
import com.nanny.util.DateUtil;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

/**
 * @author syl
 * 平台推广
 */
@Service
@Transactional
public class ExpandListImpl implements ExpandList {
	@Resource
	private SSHUtilDao dao;

	@Override
	public String init(Map<String, String> map) {
		String sql = "SELECT * FROM base_saleman AS a LEFT JOIN base_shop AS b ON a.`id` = b.`salmanId`";
		Map<String, String> where = null;
		if(map!=null){
			where = new HashMap<String, String>();
			String many = map.get("many");
			if(many!=null && !many.trim().isEmpty()){
				where.put("name like", many);
				where.put("tel like", many);
				where.put("guid like", many);
			}
		}
		StringBuffer sqlMerge = BasisUtil.SqlMerge(sql, where, new int[]{0,1,1});
		String sql2 = BasisUtil.getSql(sqlMerge, new String[]{"a.`id` "}, "a.createTime", "desc",null, null);
		return JsonUtil.getJson(BasisUtil.myTableHelp(null, dao.getObjectListBySQL(sql2.replace("*", " *, COUNT(b.`salmanId`) AS salman_total "), null, null)));
	}

	@Override
	public String add(BaseSaleman bs) {
		// TODO Auto-generated method stub
		//System.out.println(bs);
		if(null != bs.getId() && !"".equals(""+bs.getId())){
			String sql = "UPDATE base_saleman SET name=?, mail=?, qq=?, tel=? where id=? ";
			dao.updateObjectBySQL(sql, new Object[]{bs.getName(),bs.getMail()==null?"":bs.getMail(),bs.getQq()==null?"":bs.getQq(),bs.getTel(),bs.getId()+""});
			return Dto.SUCCEED;
		}
		String strs[] = new String[]{bs.getName(),bs.getTel()};
		if(BasisUtil.save_empty_verify(strs))return Dto.NULL_ERROR;
		if(isExtst(bs.getTel()))return Dto.EXIST_ERROR;
		bs.setExtension(0);
		bs.setGuid(BasisUtil.getRandomStr(8));
		bs.setPassword(BasisUtil.getRandomStr(6));
		bs.setCreateTime(Timestamp.valueOf(DateUtil.get4yMdHms()));
		dao.saveObject(bs);
		return Dto.SUCCEED;
	}
 
	private boolean isExtst(String str){
		String sql = "select id from base_saleman where tel = ?";
		List<String> oneList = dao.getOneList(sql, new Object[]{str});
		if(oneList.size() > 0)return true;
		return false;
	}

	@Override
	public String find(String id) {
		String sql = "select * from base_saleman where id = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{id}, null));
	}

}
