package com.nanny.biz.product;

import java.util.List;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商品
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @change WPH
 * @version 0.1
 */

public interface ProductBiz {
	
	/**
	 * 商品ID取商品信息
	 * @param id
	 * @return
	 */
	public JSONArray edProduct(int id);
	
	
	/**
	 * 每日最新商品
	 * @return
	 */
	public JSONObject dodaynewpro(JSONObject obj ,PageUtil pageUtil);
	
	/**
	 * 每日最新商品数量
	 * @return
	 */
	public int dodaynewprocount();
	
	/**
	 * 添加商品
	 * @param bean
	 * @return
	 */
	public String addProduct(JSONObject bean);
	
	/**
	 * 更新商品信息
	 * @param bean
	 * @return
	 */
	public JSONArray updateProduct(JSONObject bean);
	
	/**
	 * 批量更新库存
	 * @param ids
	 * @param inventorys
	 * @return
	 */
	public String updatebatchin(List<String> ids,List<String> inventorys,List<String> prices,List<String> proCodes,String shopid);
	
	
	/**
	 * 批量上下架
	 * @param ids
	 * @param proCodes
	 * @param isUse
	 * @param shopid
	 * @return
	 */
	public String updatebatchis(List<String> ids,List<String> proCodes,String isUse,String shopid);
	
	/**
	 * 批量删除
	 * @param ids
	 * @param proCodes
	 * @param shopid
	 * @return
	 */
	public String delbatchpro(List<String> ids,List<String> proCodes,String shopid);
	
	/**********************************************16.3.9 商品更新修改*******************************************************/

	/**
	 * 根据列名更新商品
	 * @param param
	 */
	public void updateProByCloum(JSONObject param);
	
	/**********************************************16.3.14 商品查询修改*******************************************************/
	
	public JSONArray getProMsgByCheckName(String name,Long shopID);
}
