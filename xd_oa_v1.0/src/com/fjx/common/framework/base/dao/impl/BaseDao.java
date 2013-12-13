package com.fjx.common.framework.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	public Serializable save(T entity) throws Exception{
		Serializable serializable = null;
		try {
			serializable = getHibernateTemplate().save(entity);
		} catch (Exception e) {
			serializable = null;
			logger.error("数据保存失败", e);
			throw new SystemException("数据保存失败",e);
		}
		return serializable;
	}
	
	@Override
	public <X> Serializable add(X entity) throws Exception {
		Serializable serializable = null;
		try {
			serializable = getHibernateTemplate().save(entity);
		} catch (Exception e) {
			serializable = null;
			logger.error("数据保存失败", e);
			throw new SystemException("数据保存失败",e);
		}
		return serializable;
	}
	
	@Override
	public boolean saveOrUpdate(T entity) throws Exception{
		boolean flag = false;
		try {
			 getHibernateTemplate().saveOrUpdate(entity);
			 flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("保存或更新数据失败", e);
			throw new SystemException("保存或更新数据失败",e);
		}
		return flag;
	}

	@Override
	public boolean delete(Serializable pk) throws Exception{
		
		boolean flag = false;
		try {
			T entity = (T) getHibernateTemplate().load(getEntityClass(), pk);
			getHibernateTemplate().delete(entity);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("删除数据失败", e);
			throw new SystemException("删除数据失败",e);
		}
		return flag;
	}
	
	@Override
	public boolean delete(T entity) throws Exception{
		
		boolean flag = false;
		try {
			getHibernateTemplate().delete(entity);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("删除数据失败", e);
			throw new SystemException("删除数据失败",e);
		}
		return flag;
	}
	
	@Override
	public boolean deleteAll(Collection<T> entities) throws Exception{
		boolean flag = false;
		try {
			getHibernateTemplate().deleteAll(entities);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("删除数据失败", e);
			throw new SystemException("删除数据失败",e);
		}
		return flag;
	}
	
	@Override
	public boolean update(T entity) throws Exception{
		boolean flag = false;
		try {
			getHibernateTemplate().update(entity);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("更新数据失败", e);
			throw new SystemException("更新数据失败",e);
		}
		return flag;
	}
	
	@Override
	public <X> X get(Class<X> entityClass, Serializable id)throws Exception{
		return (X)getHibernateTemplate().load(entityClass,id);
	}
	
	@Override
	public T get(Serializable pk) throws Exception {
		if(null == pk || pk.equals("")){
			return null;
		}
		return (T) getHibernateTemplate().load(getEntityClass(), pk);
	}
	
	@Override
	public <X> X findOneByHql(final String hql, final Object... parameters)throws Exception {
		return findOne(hql,true,parameters);	
	}
	
	@Override
	public Map findOneBySql(final String sql, final Object... parameters)throws Exception {
		return findOne(sql,false,parameters);	
	}
	
	@Override
	public List<T> findList()throws Exception{
		String hql = "from "+getEntityClass().getName();
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public <X> List<X> findListByHql(String hql, Object... parameters)throws Exception {
		return getHibernateTemplate().find(hql, parameters);
	}
	
	@Override
	public List<Map> findListMapBySql(String sql, Object... parameters)
			throws Exception {
		Query q = createMyQuery(sql, false);
		q = setQueryParameters(q, parameters);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	@Override
	public Pagination<T> page () throws Exception{
		String hql = "from "+getEntityClass().getName();
		return pageByHql(hql);
	}
	
	@Override
	public <X> Pagination<X> pageByHql(final String hql, final Object... parameters)throws Exception {
		return findPage(hql,true,parameters);
	}
	
	@Override
	public Pagination<Map> pageListMapBySql(final String sql, final Object... parameters)throws Exception {
		return findPage(sql,false,parameters);
	}
	
	@Override
	public <X> X findUniqueByHql(String hql, Object... parameters)
			throws Exception {
		return findUnique(hql,true,parameters);
	}

	@Override
	public <X> X findUniqueBySql(String sql, Object... parameters)
			throws Exception {
		return findUnique(sql,false,parameters);
	}
	
	@Override
	public int getCount(String hql, boolean isHql, Object... parameters)throws Exception {
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
	
	/**
	 * 创建Query
	 * @param ql
	 * @param isHql
	 * @return
	 */
	private Query createMyQuery(String ql, boolean isHql){
		Query q = null;
		if(isHql){
			q = getSession().createQuery(ql);
		}else{
			q = getSession().createSQLQuery(ql);
		}
		return q;
	}
	/**
	 * Query 赋值
	 * @param q
	 * @param parameters
	 * @return
	 */
	private Query setQueryParameters(Query q, Object... parameters){
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				q.setParameter(i, parameters[i]);
			}
		}
		return q;
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
		throw new SystemException("查询语句错误");
	}
	/**
	 * 查询唯一的一个字段
	 * @param ql
	 * @param isHql
	 * @param parameters
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
	private <X> X findUnique(String ql,boolean isHql, Object... parameters) throws HibernateException, SQLException{
		Query q = createMyQuery(ql,isHql);
		q = setQueryParameters(q, parameters);
		return (X) q.uniqueResult(); 
	}
	
	/**
	 * 
	 * @param ql
	 * @param isHql
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	private <X> X findOne(final String ql, boolean isHql, final Object... parameters)throws Exception {
		Query q = createMyQuery(ql, isHql);
		q = setQueryParameters(q, parameters);
		q.setMaxResults(1);
		if(!isHql){
			q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		}
		List list = q.list();
		if(null != null && list.size()>0){
			return (X)list;
		}
		return null;
	}
	/**
	 * 分页查询
	 * @param hql
	 * @param isHql
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	private <X> Pagination<X> findPage(final String hql, boolean isHql, final Object... parameters)throws Exception {
		int total = getCount(hql, isHql, parameters);
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
		Pagination<X> pagination = new Pagination<X>(datas, total);
		return pagination;
	}

}
