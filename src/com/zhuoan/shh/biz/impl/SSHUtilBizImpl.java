package com.zhuoan.shh.biz.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service
public class SSHUtilBizImpl implements SSHUtilBiz {

	@Resource
	private SSHUtilDao sshUtilDao;

	@Override
	public Serializable saveObject(Object obj) {

		return sshUtilDao.saveObject(obj);
	}

	@Override
	public Object getObjectById(Class<?> clazz, Serializable id) {

		return sshUtilDao.getObjectById(clazz, id);
	}

	@Override
	public void updateObject(Object obj) {

		sshUtilDao.updateObject(obj);
	}

	@Override
	public void deleteObject(Object obj) {

		sshUtilDao.deleteObject(obj);
	}
	@Override
	public Integer getObjectCount(Class<?> clazz, String colName, QueryParam queryParam) {

		return sshUtilDao.getObjectCount(clazz, colName, queryParam);
	}

	@Override
	public List<?> getObjectList(Class<?> clazz, QueryParam queryParam, PageUtil pageUtil) {

		return sshUtilDao.getObjectList(clazz, queryParam, pageUtil);
	}

	@Override
	public PageUtil getPageCount(Class<?> clazz, QueryParam queryParam, PageUtil pageUtil) {
		
		// 总记录数
		int totalCount = sshUtilDao.getObjectCount(clazz, "id", queryParam);
		
	    // 总页数
		int pageCount = ((totalCount + pageUtil.getPageSize() - 1)/pageUtil.getPageSize());
		
		
		pageUtil.setPageCount(pageCount);
		pageUtil.setTotalCount(totalCount);
		
		return pageUtil;
	}

	@Override
	public double getSum(Class<?> clazz, String colName, QueryParam queryParam) {
		
		return sshUtilDao.getSum(clazz, colName, queryParam);
	}

	@Override
	public List<?> getObjectList(String hql, Object[] queryParam,
			PageUtil pageUtil) {
		
		return sshUtilDao.getObjectList(hql, queryParam, pageUtil);
	}
	
	@Override
	public List<?> getObjectListBySQL(String sql, Object[] queryParam,
			PageUtil pageUtil) {
		
		return sshUtilDao.getObjectListBySQL(sql, queryParam, pageUtil);
	}

	@Override
	public double getAvg(Class<?> clazz, String colName, QueryParam queryParam) {
		
		return sshUtilDao.getAvg(clazz, colName, queryParam);
	}

	@Override
	public void updateObjectBySQL(String sql, Object[] queryParam) {
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		
	}
	

}
