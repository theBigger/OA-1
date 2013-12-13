package com.fjx.common.framework.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fjx.common.framework.system.pagination.Pagination;

/**
 * 
 * @author fengjianxin
 * @date 2013年11月24日
 */

public interface IBaseDao<T> {
	
	/**
	 * 保存泛型指向的实体
	 * @param entity
	 */
	public Serializable save (T entity) throws Exception;
	
	/**
	 * 保存除泛型指向之外的实体
	 * @param entity
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public <X> Serializable add (X entity) throws Exception;
	
	/**
	 * 新增或保存
	 * @param entity
	 */
	public boolean saveOrUpdate(T entity) throws Exception;
	/**
	 * 删除记录
	 * @param 
	 */
	public boolean delete (Serializable pk) throws Exception;
	
	/**
	 * 删除记录
	 * @param 
	 */
	public boolean delete (T entity) throws Exception;
	/**
	 * 
	 * @param entities
	 * @throws DataAccessException
	 */
	public boolean deleteAll(Collection<T> entities) throws Exception;
	
	/**
	 * 更新单条记录
	 * @param entity
	 */
	public boolean update (T entity) throws Exception;
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @return 泛型指向之外的实体
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public <X> X get(Class<X> entityClass, Serializable id)throws Exception;
	/**
	 * 查询单个对象
	 * @param pk
	 * @return 泛型中指向类型对象实例
	 */
	public T get (Serializable pk) throws Exception;
	
	/**
	 * 根据hql查询单条记录
	 * @param hql
	 * @param parameters
	 * @return 返回 X可以是实体或者Map
	 */
	public <X> X findOneByHql(String hql, Object... parameters)throws Exception;
	
	/**
	 * 根据hql查询单条记录
	 * @param sql
	 * @param parameters
	 * @return 返回map
	 */
	public Map findOneBySql(String sql, Object... parameters)throws Exception;
	
	/**
	 * 查询所有记录，根据泛型查询该对象纪录列表
	 * @param hql
	 * @param parameters
	 * @return	
	 */
	public List<T> findList()throws Exception;
	
	/**
	 * 查询所有记录
	 * @param hql
	 * @param parameters
	 * @return 返回列表数据 X 可以是实体对象或者Map类型
	 */
	public <X> List<X> findListByHql (String hql, Object... parameters)throws Exception;
	
	/**
	 * 根据sql查询多条记录
	 * @param parameters
	 * @return 
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public List<Map> findListMapBySql(String sql, Object... parameters)throws Exception;
	
	/**
	 * 将查询单条记录
	 * @param hql
	 * @param parameters
	 * @return	
	 */
	public <X> X findUniqueByHql(String hql, Object... parameters)throws Exception;
	
	/**
	 * 将查询单条记录
	 * @param sql
	 * @param parameters
	 * @return	
	 */
	public <X> X findUniqueBySql(String sql, Object... parameters)throws Exception;
	
	/**
	 * 获取总记录数
	 * @param ql hql / sql
	 * @param parameters
	 * @return
	 */
	public int getCount(String ql, boolean isHql, Object... parameters)throws Exception;
	
	/**
	 * 分页查询，根据泛型查询该对象纪录列表
	 * @param parameters
	 * @return 分页对象 
	 */
	public Pagination<T> page ()throws Exception;
	
	/**
	 * 分页查询
	 * @param hql
	 * @param parameters
	 * @return	返回list分页数据 X 可以是实体对象或者Map类型
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public <X> Pagination<X> pageByHql (String hql, Object... parameters)throws Exception;
	
	/**
	 * 分页查询
	 * @param hql
	 * @param parameters
	 * @return	返回list分页数据
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public Pagination<Map> pageListMapBySql (String sql, Object... parameters)throws Exception;
	
	/**
	 * 执行更新操作（修改、删除）
	 * @param ql
	 * @param parameters
	 * @return
	 * @throws HibernateException, SQLException
	 */
	public int bulkUpdate(String ql,Object... parameters)throws Exception;
	
	/**
	 * 批量执行更新操作（修改、删除）
	 * @param qls
	 * @param paramList
	 * @return 只要不报错就返回true
	 * @throws HibernateException, SQLException
	 */
	public boolean bulkUpdate(List<String> qls, List<Object> paramList)throws Exception;
	
	/**
	 * 批量执行更新操作（修改、删除）
	 * @param qls
	 * @param paramList
	 * @return 只要其中一条语句没有跟新数据就返回false 
	 * @throws HibernateException, SQLException
	 */
	public boolean bulkUpdateInFetch(List<String> qls, List<Object> paramList)throws Exception;
	
}
