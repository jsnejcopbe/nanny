package com.zhuoan.shh.biz;

import java.io.Serializable;
import java.util.List;

import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;

public interface SSHUtilBiz {

	/**
	 * 将实体对象持久化（保存到数据库）
	 * @param obj 
	 * @return
	 */
	public Serializable saveObject(Object obj);

	/**
	 * 根据实体对象ID获取实体对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	
	public Object getObjectById(Class<?> clazz, Serializable id);
	
	/**
	 * 更新实体对象信息
	 * @param obj
	 */
	public void updateObject(Object obj);
	
	
	/**
	 * 将实体对象删除（慎用：物理删除）
	 * @param obj
	 */
	public void deleteObject(Object obj);
	
	public void updateObjectBySQL(String sql, Object[] queryParam);
	
	
	/**
	 * 根据条件获取列colName的总数
	 * @param clazz
	 * @param colName
	 * @param queryParam
	 * @return
	 */
	
	public Integer getObjectCount(Class<?> clazz, String colName, QueryParam queryParam);
	
	/**
	 * 根据条件获取记录列表
	 * @param hql
	 * @param queryParam
	 * @param pageUtil
	 * @return
	 */
	public List<?> getObjectList(String hql, Object[] queryParam, PageUtil pageUtil);

	/**
	 * 根据条件获取记录列表
	 * @param sql
	 * @param queryParam
	 * @param pageUtil
	 * @return
	 */
	public List<?> getObjectListBySQL(String sql, Object[] queryParam, PageUtil pageUtil);
	
	/**
	 * 根据条件获取记录列表
	 * @param clazz
	 * @param queryParam
	 * @param pageUtil
	 * @return
	 */
	
	public List<?> getObjectList(Class<?> clazz, QueryParam queryParam, PageUtil pageUtil);
	
	/**
	 * 根据条件获取总页数
	 * @param clazz
	 * @param queryParam
	 * @param pageUtil 
	 * @return
	 */
	
	public PageUtil getPageCount(Class<?> clazz, QueryParam queryParam, PageUtil pageUtil);
	
	/**
	 * 根据列名取数据列总和
	 * @param clazz
	 * @param colName
	 * @param queryParam
	 * @return
	 */
	
	public double getSum(Class<?> clazz, String colName, QueryParam queryParam);
	
	/**
	 * 根据列名取该数据列平均值
	 * @param clazz
	 * @param colName
	 * @param queryParam
	 * @return
	 */
	public double getAvg(Class<?> clazz, String colName, QueryParam queryParam);
	
}
