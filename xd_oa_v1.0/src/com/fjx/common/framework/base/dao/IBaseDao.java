package com.fjx.common.framework.base.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.fjx.common.framework.system.pagination.Pagination;

/**
 * 
 * @author fengjianxin
 *
 */

public interface IBaseDao<T> {
	/**
	 * 保存
	 * @param entity
	 */
	public void save (T entity) throws HibernateException, SQLException;
	/**
	 * 新增或保存
	 * @param entity
	 */
	public void saveOrUpdate(T entity) throws HibernateException, SQLException;
	/**
	 * 删除记录
	 * @param pk
	 */
	public void delete (Serializable pk) throws HibernateException, SQLException;
	/**
	 * 更新单条记录
	 * @param entity
	 */
	public void update (T entity) throws HibernateException, SQLException;
	/**
	 * 查询单个对象
	 * @param pk
	 * @return
	 */
	public T findEntityByPk (Serializable pk)throws HibernateException, SQLException;
	
	/**
	 * 查询所有记录，根据泛型查询该对象纪录列表
	 * @param hql
	 * @param parameters
	 * @return	
	 */
	public List<T> findAllEntity()throws HibernateException, SQLException;
	
	/**
	 * 查询所有记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	//public List<T> find (String hql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 根据hql查询单条记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	//public T findOne(String hql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 分页查询，根据泛型查询该对象纪录列表
	 * @param parameters
	 * @return 分页对象 
	 */
	public Pagination<T> find4page ()throws HibernateException, SQLException;
	
	/**
	 * 分页查询
	 * @param hql
	 * @param parameters
	 * @return 分页对象
	 */
	//public Pagination<T> find4page (String hql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 获取总记录数
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return
	 */
	public int getCount(String ql, boolean isHql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 将查询单条记录
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return	使用泛型：可以返回map数据，也可以返回实体对象
	 * 如果是sql语句，则将结果转为map类型
	 * 如果是hql语句，可以在hql里控制返回值类型
	 */
	public <X> X find4Unique(String ql, boolean isHql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 
	 * 查询多条记录
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return 使用泛型：可以返回map数据，也可以返回实体对象
	 * 如果是sql语句，则将结果转为map类型，并放到List中
	 * 如果是hql语句，如果是hql语句，可以在hql里控制返回值类型，并放到List中
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public <X> List<X> find4List(String ql, boolean isHql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 
	 * 分页查询
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return	返回list分页数据，使用hql可以在hql里控制返回值类型
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public <X> Pagination<List<X>> find4ListPage (String sql, boolean isHql, Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 执行更新操作（修改、删除）
	 * @param ql
	 * @param parameters
	 * @return
	 * @throws HibernateException, SQLException
	 */
	public int bulkUpdate(String ql,Object... parameters)throws HibernateException, SQLException;
	
	/**
	 * 批量执行更新操作（修改、删除）
	 * @param qls
	 * @param paramList
	 * @return 只要不报错就返回true
	 * @throws HibernateException, SQLException
	 */
	public boolean bulkUpdate(List<String> qls, List<Object> paramList)throws HibernateException, SQLException;
	
	/**
	 * 批量执行更新操作（修改、删除）
	 * @param qls
	 * @param paramList
	 * @return 只要其中一条语句没有跟新数据就返回false 
	 * @throws HibernateException, SQLException
	 */
	public boolean bulkUpdateInFetch(List<String> qls, List<Object> paramList)throws HibernateException, SQLException;
	
}
