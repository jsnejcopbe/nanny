package com.nanny.biz.impl.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.nanny.biz.user.Salesman;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service
@Transactional
public class SalesmanImpl implements Salesman{
	@Resource
	private SSHUtilDao dao;
	
	@Override
	public void init(Model model,Long id) {
		String sql = " SELECT a.*,tmp.total FROM base_saleman a," +
					 " (select count(id) as total from base_shop where salmanID=?) tmp where a.id=? ";
		List<?> datalist = dao.getObjectListBySQL(sql, new Object[]{id.intValue(),id.intValue()}, null);
		model.addAttribute("salesman_info", datalist);
		model.addAttribute("shop_list", getShop(id+""));
	}

	private List<?> getShop(String id){
		String sql = "SELECT id,shop_icon,shop_name FROM base_shop WHERE `salmanID` = ?";
		return dao.getObjectListBySQL(sql, new Object[]{id}, null);
	};
	
	@Override
	public void store_list_init(Model model) {
		String sql = "select * from sys_shop_apply where 1=1 ";
		
	}
}
