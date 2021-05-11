package com.nanny.biz.shop;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;


import com.nanny.model.SysGlobalArea;
import com.nanny.model.SysShopApply;

/**商户基本接口
 * @author syl
 *
 */
public interface ShopBasis {
	/**
	 * 商户申请
	 * @param bean
	 * @param session
	 * @return
	 */
	public String apply(SysShopApply bean,HttpSession session);

	/**
	 * @param mapstr 查询参数 
	 * @return type 0 返回 json数据 1 返回datalist
	 */
	public String apply_init_list(Map<String, String> mapstr,String salesman_id);

	/** 申请改变状态
	 * @param type
	 * @param id
	 * @return
	 */
	public String apply_change_state(String type,String tel,String id) throws UnsupportedEncodingException;
	
	/**店铺基本信息
	 * @return
	 */
	public String baseinfo_init(String usreID);
	
	/**店铺公告初始化
	 * @return
	 */
	public String notice_init(String id);

	/**店铺公告修改
	 * @return
	 */
	public String notice_edit(String id,String data);
	
	/**经营时间初始化
	 * @param id
	 * @return
	 */
	public String runtime_init(String id);
	
	/**经营时间修改
	 * @param id
	 * @param time1
	 * @param time2
	 * @return
	 */
	public String runtime_edit(String id,String time1,String time2,String time_id);

	/** 地址添加
	 * @param bean
	 * @return
	 */
	public String adressAdd(SysGlobalArea bean,String shop_id);
	
	/** 地址修改
	 * @param bean
	 * @return
	 */
	public String adressEdit(String adressID,String shopID);

	/** 获取地址
	 * @return
	 */
	public String getAdress();
	
	/** 获取省
	 * @return
	 */
	public String getProvince();

	/** 获取市
	 * @param province
	 * @return
	 */
	public String getCity(String province);

	/** 配送范围设置
	 * @return
	 */
	public String scopeAdd(String shopID, List<String> list);

	/** 商户配送范围列表
	 * @param map 
	 * @return
	 */
	public String getScopelist(String id, Map<String, String> map);
	
	/** 
	 * 获取地址列表 所有
	 * @param map 
	 * @return
	 */
	public String getAdresslist(String id,Map<String, String> map);

	/** 配送地址删除
	 * @param id
	 * @return
	 */
	public String delRemove(String id,String adressID);
	
	/**获得所有商户地址
	 * @return
	 */
	public String getShopAdress(String data);

	/**设置商户头像
	 * @param url
	 * @return
	 */
	public String change_headimg(String url,String shopID);

	/** 改变配送费 和 起送价
	 * @param type
	 * @param shopID
	 * @return
	 */
	public String change_price(String type,String value, String shopID);

	/** 营业时间删除
	 * @param time_id
	 * @param shopID
	 * @return
	 */
	public String runtime_remove(String time_id, String shopID);

	/** 获取密码
	 * @param id
	 * @return
	 */
	public String getPassword(String id);
	
	/** 商铺关闭
	 * @param shopID
	 * @return
	 */
	public JSONObject closeshop(String shopID);
	/** 商铺打开
	 * @param shopID
	 * @return
	 */
	public JSONObject openshop(String shopID);
	
	
	/**
	 * 店铺电话修改
	 * @param tel
	 * @param userid
	 * @return
	 */
	public String updateshoptel(String tel,int shopID);
	
	/**
	 * 更新店铺二维码
	 * @param shopID
	 * @param url
	 * @return
	 */
	public void updateShopUrl(Long shopID,String url);
}

