package com.fjx.common.framework.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	public void save (T entity) throws Exception;
	/**
	 * 新增或保存
	 * @param entity
	 */
	public void saveOrUpdate(T entity) throws Exception;
	/**
	 * 删除记录
	 * @param pk
	 */
	public void delete (Serializable pk) throws Exception;
	/**
	 * 更新单条记录
	 * @param entity
	 */
	public void update (T entity) throws Exception;
	/**
	 * 查询单个对象
	 * @param pk
	 * @return
	 */
	public T findOne (Serializable pk)throws Exception;
	
	/**
	 * 查询所有记录，根据泛型查询该对象纪录列表
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<T> findAll()throws Exception;
	
	/**
	 * 查询所有记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<T> find (String hql, Object... parameters)throws Exception;
	
	/**
	 * 根据hql查询单条记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public T findOne(String hql, Object... parameters)throws Exception;
	
	/**
	 * 分页查询，根据泛型查询该对象纪录列表
	 * @param parameters
	 * @return 分页对象
	 */
	public Pagination<T> find4page ()throws Exception;
	
	/**
	 * 分页查询
	 * @param hql
	 * @param parameters
	 * @return 分页对象
	 */
	public Pagination<T> find4page (String hql, Object... parameters)throws Exception;
	
	/**
	 * 获取总记录数
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return
	 */
	public int getCount(String ql, boolean isHql, Object... parameters)throws Exception;
	
	/**
	 * 将查询单条记录
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return	以map返回数据
	 */
	public Map<String, Object> findOne4Map(String ql, boolean isHql, Object... parameters)throws Exception;
	
	/**
	 * 查询多条记录
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return	以list返回
	 */
	public List<Map<String, Object>> find4ListMap(String ql, boolean isHql, Object... parameters)throws Exception;
	
	/**
	 * 分页查询
	 * @param ql hql / sql
	 * @param isHql  是否是hql true:hql; false:sql
	 * @param parameters
	 * @return	返回list分页数据
	 */
	public Pagination<List<Map<String, Object>>> find4ListPage (String sql, boolean isHql, Object... parameters)throws Exception;
	
}
