package com.zhuoan.ssh.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Repository
public class SSHUtilDaoImpl implements SSHUtilDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}
	

	/**
	 * 获取对象对应参数的类型 
	 * @param bindValue
	 * @return
	 */
	private final Type[] typesFactory(Object[] bindValue) {
	
		Type[] types = new Type[bindValue.length];
		
		for (int i = 0; i < bindValue.length; i++) {
			if (bindValue[i].getClass().getName().endsWith("String")) {
				types[i] = new StringType();
			} else if (bindValue[i].getClass().getName().endsWith("Integer")) {
				types[i] = new IntegerType();
			} else if (bindValue[i].getClass().getName().endsWith("Long")) {
				types[i] = new IntegerType();
			} else if (bindValue[i].getClass().getName().endsWith("Float")) {
				types[i] = new FloatType();
			} else if (bindValue[i].getClass().getName().endsWith("Double")) {
				types[i] = new DoubleType();
			} else if (bindValue[i].getClass().getName().endsWith("Short")) {
				types[i] = new ShortType();
			} else if (bindValue[i].getClass().getName().endsWith("Date")) {
				types[i] = new DateType();
			} else if (bindValue[i].getClass().getName().endsWith("Boolean")) {
				types[i] = new BooleanType();
			}else if(bindValue[i].getClass().getName().endsWith("Timestamp")){
				types[i] = new TimestampType();
			}
		}
		
		return types;
	}


	@Override
	public Serializable saveObject(Object obj) {

		return getSession().save(obj);
	}

	
	@Override
	public Object getObjectById(Class<?> clazz, Serializable id) {

		return getSession().get(clazz, id);
	}

	@Override
	public void updateObject(Object obj) {

		getSession().update(obj);
	}

	@Override
	public void deleteObject(Object obj) {

		getSession().delete(obj);
	}

	
	@Override
	public Integer getObjectCount(Class<?> clazz, String colName,
			QueryParam queryParam) {

		Criteria criteria = getSession().createCriteria(clazz);

		// select count(colName)
		criteria.setProjection(Projections.count(colName));

       if (queryParam != null) {
			
		if (queryParam.getTypeMap() != null) {
		// 遍历操作条件
		for (Iterator<String> it = queryParam.getTypeMap().keySet().iterator(); it
				.hasNext();) {

			String key = it.next();

			String type = queryParam.getTypeMap().get(key);

			if ("eq".equals(type)) { // 等于

				criteria.add(Restrictions.eq(key,
						queryParam.getParamMap().get(key)));

			} else if ("ne".equals(type)) { // 不等于

				criteria.add(Restrictions.not(Restrictions.eq(key, queryParam
						.getParamMap().get(key))));

			} else if ("gt".equals(type)) { // 大于

				criteria.add(Restrictions.gt(key, queryParam.getParamMap().get(key)));

			} else if ("lt".equals(type)) { // 小于

				criteria.add(Restrictions.lt(key, queryParam.getParamMap().get(key)));

			} else if ("ge".equals(type)) { // 大于等于

				criteria.add(Restrictions.ge(key, queryParam.getParamMap().get(key)));

			} else if ("le".equals(type)) { // 小于等于

				criteria.add(Restrictions.le(key, queryParam.getParamMap().get(key)));

			} else if ("bet".equals(type)) { // 介于...之间

				Object[] i = (Object[]) queryParam.getParamMap().get(key);
				
				criteria.add(Restrictions.between(key, i[0], i[1]));

			} else if ("like".equals(type)) { // like查询

				criteria.add(Restrictions.like(key, queryParam
						.getParamMap().get(key)));

			} else if ("isNull".equals(type)) { // 为null

				criteria.add(Restrictions.isNull(key));

			} else if ("notNull".equals(type)) { // 不为null

				criteria.add(Restrictions.isNotNull(key));

			} else if ("isEmpty".equals(type)) { // 为空

				criteria.add(Restrictions.isEmpty(key));

			} else if ("notEmpty".equals(type)) { // 不为空

				criteria.add(Restrictions.isNotEmpty(key));

			}
			// ... 可拓展

		}
	}
}

		Integer count = (Integer) criteria.uniqueResult();

		return count;
	}

	
	@Override
	public List<?> getObjectList(Class<?> clazz, QueryParam queryParam,
			PageUtil pageUtil) {

		Criteria criteria = getSession().createCriteria(clazz);

		if (queryParam != null) {
			
			if (queryParam.getTypeMap() != null) {
				
				// 遍历操作条件
				for (Iterator<String> it = queryParam.getTypeMap().keySet().iterator(); it
						.hasNext();) {
					
					String key = it.next();
					
					String type = queryParam.getTypeMap().get(key);
					
					if ("eq".equals(type)) { // 等于
						
						criteria.add(Restrictions.eq(key,
								queryParam.getParamMap().get(key)));
						
					} else if ("ne".equals(type)) { // 不等于
						
						criteria.add(Restrictions.not(Restrictions.eq(key, queryParam
								.getParamMap().get(key))));
						
					} else if ("gt".equals(type)) { // 大于
						
						criteria.add(Restrictions.gt(key, queryParam.getParamMap().get(key)));
						
					} else if ("lt".equals(type)) { // 小于
						
						criteria.add(Restrictions.lt(key, queryParam.getParamMap().get(key)));
						
					} else if ("ge".equals(type)) { // 大于等于
						
						criteria.add(Restrictions.ge(key, queryParam.getParamMap().get(key)));
						
					} else if ("le".equals(type)) { // 小于等于
						
						criteria.add(Restrictions.le(key, queryParam.getParamMap().get(key)));
						
					} else if ("bet".equals(type)) { // 介于...之间
						
						Object[] i = (Object[]) queryParam.getParamMap().get(key);
						
						criteria.add(Restrictions.between(key, i[0], i[1]));
						
					} else if ("like".equals(type)) { // like查询

						criteria.add(Restrictions.like(key, queryParam
								.getParamMap().get(key)));

					} else if ("isNull".equals(type)) { // 为null

						criteria.add(Restrictions.isNull(key));

					} else if ("notNull".equals(type)) { // 不为null

						criteria.add(Restrictions.isNotNull(key));

					} else if ("isEmpty".equals(type)) { // 为空

						criteria.add(Restrictions.isEmpty(key));

					} else if ("notEmpty".equals(type)) { // 不为空

						criteria.add(Restrictions.isNotEmpty(key));

					}
					// ... 可拓展
					
				}
			}
			
			if (queryParam.getOrderMap() != null) {
				
				for (Iterator<String> it = queryParam.getOrderMap().keySet()
						.iterator(); it.hasNext();) {
					
					String key = it.next();
					
					if ("desc".equals(queryParam.getOrderMap().get(key))) {
						
						criteria.addOrder(Order.desc(key));
					} else {
						
						criteria.addOrder(Order.asc(key));
					}
				}
			}
		}
		
		if (pageUtil == null)
			return criteria.list();
		else
			return criteria
				.setFirstResult(
						(pageUtil.getPageIndex() - 1) * pageUtil.getPageSize())
				.setMaxResults(pageUtil.getPageSize()).list();
	}

	@Override
	public double getSum(Class<?> clazz, String colName, QueryParam queryParam) {

		Criteria criteria = getSession().createCriteria(clazz);

		criteria.setProjection(Projections.sum(colName));

		// 遍历操作条件
		for (Iterator<String> it = queryParam.getTypeMap().keySet().iterator(); it
				.hasNext();) {

			String key = it.next();

			String type = queryParam.getTypeMap().get(key);

			if ("eq".equals(type)) { // 等于

				criteria.add(Restrictions.eq(key,
						queryParam.getParamMap().get(key)));

			} else if ("ne".equals(type)) { // 不等于

				criteria.add(Restrictions.not(Restrictions.eq(key, queryParam
						.getParamMap().get(key))));

			} else if ("gt".equals(type)) { // 大于

				criteria.add(Restrictions.gt(key, queryParam.getParamMap().get(key)));

			} else if ("lt".equals(type)) { // 小于

				criteria.add(Restrictions.lt(key, queryParam.getParamMap().get(key)));

			} else if ("ge".equals(type)) { // 大于等于

				criteria.add(Restrictions.ge(key, queryParam.getParamMap().get(key)));

			} else if ("le".equals(type)) { // 小于等于

				criteria.add(Restrictions.le(key, queryParam.getParamMap().get(key)));

			} else if ("bet".equals(type)) { // 介于...之间

				Object[] i = (Object[]) queryParam.getParamMap().get(key);
				
				criteria.add(Restrictions.between(key, i[0], i[1]));

			} else if ("like".equals(type)) { // like查询

				criteria.add(Restrictions.like(key, queryParam
						.getParamMap().get(key)));

			} else if ("isNull".equals(type)) { // 为null

				criteria.add(Restrictions.isNull(key));

			} else if ("notNull".equals(type)) { // 不为null

				criteria.add(Restrictions.isNotNull(key));

			} else if ("isEmpty".equals(type)) { // 为空

				criteria.add(Restrictions.isEmpty(key));

			} else if ("notEmpty".equals(type)) { // 不为空

				criteria.add(Restrictions.isNotEmpty(key));

			}
			// ...

		}

		Double sum = (Double) criteria.uniqueResult();

		if (sum != null) {

			return sum;
		} else {

			return 0;
		}
	}

	@Override
	public List<?> getObjectList(String hql, Object[] queryParam,
			PageUtil pageUtil) {
		
		Query query = getSession().createQuery(hql);
		
		if (queryParam != null && queryParam.length >= 1) {  
           
			Type[] types = typesFactory(queryParam);  
            query.setParameters(queryParam, types);  
        }  
		if(pageUtil==null)
			return query.list();
		else
			return query
					.setFirstResult(
							(pageUtil.getPageIndex() - 1) * pageUtil.getPageSize())
					.setMaxResults(pageUtil.getPageSize()).list();
	}
	
	@Override
	public List<?> getObjectListBySQL(String sql, Object[] queryParam,
			PageUtil pageUtil) {
		Query query = getSession().createSQLQuery(sql);   
	
		if (queryParam != null && queryParam.length >= 1) {  
           
			Type[] types = typesFactory(queryParam);  
            query.setParameters(queryParam, types);  
        }  
		
		if(pageUtil==null)
			return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		else
			return query
					.setFirstResult((pageUtil.getPageIndex() - 1) * pageUtil.getPageSize())
					.setMaxResults(pageUtil.getPageSize())
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Override
	public void updateObjectBySQL(String sql, Object[] queryParam) {
		Query query = getSession().createSQLQuery(sql); 
		
		if (queryParam != null && queryParam.length >= 1) {  
	           
			Type[] types = typesFactory(queryParam);  
            query.setParameters(queryParam, types);  
        } 
		
		query.executeUpdate();
	}

	@Override
	public double getAvg(Class<?> clazz, String colName, QueryParam queryParam) {
		
		Criteria criteria = getSession().createCriteria(clazz);

		criteria.setProjection(Projections.avg(colName));
		
        if (queryParam != null) {
			
			if (queryParam.getTypeMap() != null) {
		// 遍历操作条件
		for (Iterator<String> it = queryParam.getTypeMap().keySet().iterator(); it
				.hasNext();) {

			String key = it.next();

			String type = queryParam.getTypeMap().get(key);

			if ("eq".equals(type)) { // 等于

				criteria.add(Restrictions.eq(key,
						queryParam.getParamMap().get(key)));

			} else if ("ne".equals(type)) { // 不等于

				criteria.add(Restrictions.not(Restrictions.eq(key, queryParam
						.getParamMap().get(key))));

			} else if ("gt".equals(type)) { // 大于

				criteria.add(Restrictions.gt(key, queryParam.getParamMap().get(key)));

			} else if ("lt".equals(type)) { // 小于

				criteria.add(Restrictions.lt(key, queryParam.getParamMap().get(key)));

			} else if ("ge".equals(type)) { // 大于等于

				criteria.add(Restrictions.ge(key, queryParam.getParamMap().get(key)));

			} else if ("le".equals(type)) { // 小于等于

				criteria.add(Restrictions.le(key, queryParam.getParamMap().get(key)));

			} else if ("bet".equals(type)) { // 介于...之间

				Object[] i = (Object[]) queryParam.getParamMap().get(key);
				
				criteria.add(Restrictions.between(key, i[0], i[1]));

			} else if ("like".equals(type)) { // like查询

				criteria.add(Restrictions.like(key, queryParam
						.getParamMap().get(key)));

			} else if ("isNull".equals(type)) { // 为null

				criteria.add(Restrictions.isNull(key));

			} else if ("notNull".equals(type)) { // 不为null

				criteria.add(Restrictions.isNotNull(key));

			} else if ("isEmpty".equals(type)) { // 为空

				criteria.add(Restrictions.isEmpty(key));

			} else if ("notEmpty".equals(type)) { // 不为空

				criteria.add(Restrictions.isNotEmpty(key));

			}
			// ...
		  }
		 }
		}

		Double avg = (Double) criteria.uniqueResult();

		if (avg != null) {

			return avg;
		} else {

			return 0;
		}
	}

	@Override
	public String getTotal(String sql,Object[] queryParam) {
		Query query = getSession().createSQLQuery(sql);
		if(queryParam !=null){
			for(int i=0;i<queryParam.length;i++){
				query.setString(i, queryParam[i].toString());
			}
		}
		return query.uniqueResult().toString();
	}
	
	@Override
	public List<String> getOneList(String sql,Object[] queryParam) {
		Query query = getSession().createSQLQuery(sql);
		if(queryParam !=null){
			for(int i=0;i<queryParam.length;i++){
				query.setString(i, queryParam[i].toString());
			}
		}
		return (List<String>) query.list();
	}

	@Override
	public <T> List<?> getBeanListBySQL(String sql, Object[] queryParam, Class<T> T){
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(T);
		if (queryParam != null && queryParam.length >= 1) {
			Type[] types = typesFactory(queryParam);  
            query.setParameters(queryParam, types);  
        }  
		return query.list();
	}	
}
