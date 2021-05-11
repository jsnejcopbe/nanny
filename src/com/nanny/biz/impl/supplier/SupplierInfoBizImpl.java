package com.nanny.biz.impl.supplier;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.supplier.SupplierInfoBiz;
import com.nanny.model.Area;
import com.nanny.model.BaseSupplier;
import com.nanny.model.City;
import com.nanny.model.Province;
import com.nanny.model.SupplierGlobal;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;

@Transactional
@Service
public class SupplierInfoBizImpl implements SupplierInfoBiz {
	
	@Resource
	private SSHUtilDao dao;
	
	
	@Override
	public JSONObject getsupinfo(int supid) {
		String sql="SELECT  * FROM base_supplier WHERE id=? ";
		Object[] queryParam={supid};
		 JSONArray supinfo=JSONArray.fromObject(dao.getObjectListBySQL(sql, queryParam, null));
		
		 String sql1="SELECT  * FROM supplier_global WHERE supplierID=? ";
		 Object[] queryParam1={supid};
		 JSONArray global=JSONArray.fromObject(dao.getObjectListBySQL(sql1, queryParam1, null));
		 
		 JSONObject obj=new JSONObject();
		 obj.element("supinfo", supinfo);
		 obj.element("global", global);
		 
		 
		 return obj;
	}

	@Override
	public String updinfo(JSONObject obj) {
		BaseSupplier bs=(BaseSupplier) dao.getObjectById(BaseSupplier.class, obj.getLong("sid"));
		
		bs.setSupplierIcon(obj.getString("simg"));
		bs.setSupplierDes(obj.getString("des"));
		
		Object pa=obj.get("pass");
		if(pa!=null&&pa.toString().isEmpty()&&!"".equals(pa)){
			bs.setPassword(obj.getString("pass"));
		}
		
		bs.setSupplierName(obj.getString("sname"));
		Object q=obj.get("qq");
		if(q!=null&&q.toString().isEmpty()&&!"".equals(q)){
			bs.setQq(obj.getString("qq"));
		}
		
		dao.updateObject(bs);
		return "更改成功";
	}

	@Override
	public String getProvince() {
		String sql = "SELECT  id AS s_value, pname AS s_text FROM province ";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, null, null));
	}

	@Override
	public String getCity(String provinceid) {
		String sql = "SELECT  id AS s_value, city AS s_text FROM city WHERE provinceID=? ";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{provinceid}, null));
	}

	@Override
	public String getArea(String cityid) {
		String sql = "SELECT  id AS s_value, area AS s_text FROM area WHERE cityID=? ";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{cityid}, null));
	}

	@Override
	public String addGlobal(JSONObject obj) {
		Province p=(Province) dao.getObjectById(Province.class, obj.getLong("province"));
		City c=(City) dao.getObjectById(City.class, obj.getLong("city"));
		Area a=(Area) dao.getObjectById(Area.class, obj.getLong("area"));
		
		SupplierGlobal sg=new SupplierGlobal();
		sg.setArea(a.getArea());
		sg.setCity(c.getCity());
		sg.setProvince(p.getPname());
		sg.setCreateTime(DateUtils.getTimestamp());
		sg.setSupplierId(obj.getLong("fid"));
		/*sg.setMemo(obj.getString("memo"));*/
		dao.saveObject(sg);
		
		return "添加成功";
	}

	@Override
	public String delGlobal(int gid) {
		SupplierGlobal sg=new SupplierGlobal();
		sg.setId(Long.valueOf(gid));
		dao.deleteObject(sg);
		return "删除成功";
	}

}
