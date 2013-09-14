package com.fjx.common.framework.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.fjx.common.framework.base.dao.IBaseDao;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.common.framework.system.pagination.PaginationContext;

/**
 * 		
 * @author fengjianxin
 * @param <T>
 */
@Repository("baseDao")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private Class<T> getEntityClass (){
		Type parentType = getClass().getGenericSuperclass();
		if(parentType instanceof ParameterizedType){
			return (Class<T>) ((ParameterizedType) parentType).getActualTypeArguments()[0];
		}
		throw new RuntimeException("未指定实体类型！");
	}
	
	@Override
	public void save(T entity) throws Exception{
		getHibernateTemplate().save(entity);
	}
	@Override
	public void saveOrUpdate(T entity) throws Exception{
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void delete(Serializable pk) throws Exception{
		T entity = (T) getHibernateTemplate().load(getEntityClass(), pk);
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) throws Exception{
		getHibernateTemplate().update(entity);
	}

	@Override
	public T findOne(Serializable pk)throws Exception {
		if(null == pk || pk.equals("")){
			return null;
		}
		return (T) getHibernateTemplate().load(getEntityClass(), pk);
	}
	
	@Override
	public T findOne(final String hql, final Object... parameters)throws Exception {
		T t = (T) getHibernateTemplate().executeFind(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(hql);
						if (parameters != null && parameters.length > 0) {
							for (int i = 0; i < parameters.length; i++) {
								logger.info(hql+"的分页参数："+parameters[i]);
								q.setParameter(i, parameters[i]);
							}
						}
						return q.uniqueResult();
					}
				});
		return t;
	}
	
	public List<T> findAll()throws Exception{
		String hql = "from "+getEntityClass().getName();
		return find(hql);
	}
	
	@Override
	public List<T> find(String hql, Object... parameters)throws Exception {
		return getHibernateTemplate().find(hql, parameters);
	}
	
	@Override
	public Pagination<T> find4page () throws Exception{
		String hql = "from "+getEntityClass().getName();
		return find4page(hql);
	}
	
	@Override
	public Pagination<T> find4page(final String hql, final Object... parameters)throws Exception {
		int total = getCount(hql, true, parameters);
		logger.debug("query count is: "+total);
		List datas = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(hql);
						
						if (parameters != null && parameters.length > 0) {
							for (int i = 0; i < parameters.length; i++) {
								logger.info(hql+"的分页参数："+parameters[i]);
								q.setParameter(i, parameters[i]);
							}
						}
						q.setFirstResult(PaginationContext.getOffset());	//filter拦截到的分页参数
						q.setMaxResults(PaginationContext.getPagesize());	//filter拦截到的分页参数
						return q.list();
					}
				});
		Pagination<T> pagination = new Pagination<T>(datas, total);
		return pagination;
	}
	
	
	@Override
	public int getCount(String hql, boolean isHql, Object... parameters)throws Exception {
		final String countSQL = getCountHQL(hql);
		
		int total ;
		Query q = null;
		if(isHql){
			q = getSession().createQuery(countSQL);
		}else{
			q = getSession().createSQLQuery(countSQL);
		}
		
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				q.setParameter(i, parameters[i]);
			}
		}
		total = ((Long) q.uniqueResult()).intValue();
		return total;
	}
	/**
	 * 将hql语句转换为查询记录总数的hql
	 * @param hql	hql/sql语句
	 * @return
	 */
	private String getCountHQL(String hql) throws Exception{
		int index = hql.indexOf("from");
		if (index != -1) {
			return "select count(*) " + hql.substring(index);
		}
		throw new RuntimeException("查询语句错误");
	}

	@Override
	public Map<String, Object> findOne4Map(String sql, Object... parameters) throws Exception{
		return jdbcTemplate.queryForMap(sql, parameters);
	}

	@Override
	public List<Map<String, Object>> find4ListMap(String sql, Object... parameters) {
		return jdbcTemplate.queryForList(sql, parameters);
	}

	@Override
	public Pagination<List<Map<String, Object>>> find4ListPage(final String sql,
			final Object... parameters) throws Exception{
		int total = getCount(sql, false, parameters);
		logger.debug("query count is: "+total);
		List datas = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(sql);
						
						if (parameters != null && parameters.length > 0) {
							for (int i = 0; i < parameters.length; i++) {
								logger.info(sql+"的分页参数："+parameters[i]);
								q.setParameter(i, parameters[i]);
							}
						}
						q.setFirstResult(PaginationContext.getOffset());	//filter拦截到的分页参数
						q.setMaxResults(PaginationContext.getPagesize());	//filter拦截到的分页参数
						return q.list();
					}
				});
		Pagination<List<Map<String, Object>>> pagination = new Pagination<List<Map<String, Object>>>(datas, total);
		return pagination;
	}

}
