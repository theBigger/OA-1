package com.fjx.common.framework.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.fjx.common.framework.base.dao.IBaseDao;
import com.fjx.common.framework.system.exception.SystemException;
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
	
	
	//注入sessionFactory
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
	public void save(T entity) throws HibernateException, SQLException{
		getHibernateTemplate().save(entity);
	}
	@Override
	public void saveOrUpdate(T entity) throws HibernateException, SQLException{
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void delete(Serializable pk) throws HibernateException, SQLException{
		T entity = (T) getHibernateTemplate().load(getEntityClass(), pk);
		getHibernateTemplate().delete(entity);
	}
	
	@Override
	public void deleteAll(Collection<T> entities) throws DataAccessException{
		getHibernateTemplate().deleteAll(entities);
	}
	
	@Override
	public void update(T entity) throws HibernateException, SQLException{
		getHibernateTemplate().update(entity);
	}
	
	@Override
	public <X> X loadEntity(Class<X> entityClass, Serializable id)throws DataAccessException{
		return (X)getHibernateTemplate().load(entityClass,id);
	}
	
	@Override
	public T findEntityByPk(Serializable pk) throws DataAccessException {
		if(null == pk || pk.equals("")){
			return null;
		}
		return (T) getHibernateTemplate().load(getEntityClass(), pk);
	}
	
	/*
	@Override
	public T findOne(final String hql, final Object... parameters)throws HibernateException, SQLException {
		T t = (T) getHibernateTemplate().executeFind(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query q = session.createQuery(hql);
						if (parameters != null && parameters.length > 0) {
							for (int i = 0; i < parameters.length; i++) {
								q.setParameter(i, parameters[i]);
							}
						}
						return q.uniqueResult();
					}
				});
		return t;
	}
	*/
	
	@Override
	public List<T> findAllEntity()throws HibernateException, SQLException{
		String hql = "from "+getEntityClass().getName();
		return getHibernateTemplate().find(hql);
	}
	
	/*
	@Override
	public List<T> find(String hql, Object... parameters)throws HibernateException, SQLException {
		return getHibernateTemplate().find(hql, parameters);
	}
	*/
	
	@Override
	public Pagination<T> find4page () throws HibernateException, SQLException{
		String hql = "from "+getEntityClass().getName();
		return find4page(hql);
	}
	
	/**/
	//@Override
	public Pagination<T> find4page(final String hql, final Object... parameters)throws HibernateException, SQLException {
		int total = getCount(hql, true, parameters);
		logger.debug("查询的总记录数: "+total);
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
	public int getCount(String hql, boolean isHql, Object... parameters)throws HibernateException, SQLException {
		final String countSQL = getCountHQL(hql);
		int total ;
		Query q = createMyQuery(countSQL,isHql);
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
	private String getCountHQL(String hql) throws HibernateException, SQLException{
		int index = hql.indexOf("from");
		if (index != -1) {
			return "select count(*) " + hql.substring(index);
		}
		throw new RuntimeException("查询语句错误");
	}
	
	@Override
	public <X> X find4Unique(String ql,boolean isHql, Object... parameters) throws HibernateException, SQLException{
		Query q = createMyQuery(ql,isHql);
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				q.setParameter(i, parameters[i]);
			}
		}
		//如果是sql语句，则将结果转为map类型
		if(!isHql){
			q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		}
		return (X) q.uniqueResult(); 
		//return jdbcTemplate.queryForMap(ql, parameters);
	}

	@Override
	public <X> List<X> find4List(String ql, boolean isHql, Object... parameters) throws HibernateException, SQLException {
		Query q = createMyQuery(ql,isHql);
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				q.setParameter(i, parameters[i]);
			}
		}
		//如果是sql语句，则将结果转为map类型，即：返回List<Map>
		if(!isHql){
			q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		}
		return q.list();
	}
	
	@Override
	public <X> Pagination<List<X>> find4ListPage(final String ql,boolean isHql,
			final Object... parameters) throws HibernateException, SQLException{
		int total = getCount(ql, isHql, parameters);
		logger.debug("query count is: "+total);
		Query q = createMyQuery(ql,isHql);
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				logger.info(ql+"的分页参数："+parameters[i]);
				q.setParameter(i, parameters[i]);
			}
		}
		q.setFirstResult(PaginationContext.getOffset());	//filter拦截到的分页参数
		q.setMaxResults(PaginationContext.getPagesize());	//filter拦截到的分页参数
		//如果是sql语句，则将结果转为map类型，即：返回Pagination<List<Map>>
		if(!isHql){
			q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		}
		List datas = q.list();
		
		Pagination<List<X>> pagination = new Pagination<List<X>>(datas, total);
		return pagination;
	}
	
	
	private Query createMyQuery(String ql, boolean isHql){
		Query q = null;
		if(isHql){
			q = getSession().createQuery(ql);
		}else{
			q = getSession().createSQLQuery(ql);
		}
		return q;
	}

	@Override
	public int bulkUpdate(String ql, Object... parameters) throws HibernateException, SQLException {
		return getHibernateTemplate().bulkUpdate(ql, parameters);
	}

	@Override
	public boolean bulkUpdate(List<String> qls, List<Object> paramList) throws HibernateException, SQLException {
		boolean flag = true;
		for(int i = 0; i < qls.size(); i++){
			try {
				getHibernateTemplate().bulkUpdate(qls.get(i),paramList.get(i));
			} catch (Exception e) {
				flag = false;
				throw new SystemException("语句执行发生异常【 "+qls.get(i)+" 】",e);
			}
		}
		return flag;
	}

	@Override
	public boolean bulkUpdateInFetch(List<String> qls, List<Object> paramList)
			throws HibernateException, SQLException {
		boolean flag = true;
		for(int i = 0; i < qls.size(); i++){
			int tmp = getHibernateTemplate().bulkUpdate(qls.get(i),paramList.get(i));
			//如果没有更新到数据
			if(1 > tmp){
				flag = false;
			}
		}
		return flag;
	}
	
}
