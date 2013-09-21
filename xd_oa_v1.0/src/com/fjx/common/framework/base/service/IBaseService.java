package com.fjx.common.framework.base.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.fjx.common.framework.system.pagination.Pagination;

public interface IBaseService<T> {
	/**
	 * 保存
	 * @param entity
	 */
	public void save (T entity)throws HibernateException, SQLException;;
	/**
	 * 新增或保存
	 * @param entity
	 */
	public void saveOrUpdate(T entity)throws HibernateException, SQLException;
	/**
	 * 删除记录
	 * @param pk
	 */
	public void delete (Serializable pk)throws HibernateException, SQLException;;
	/**
	 * 更新单条记录
	 * @param entity
	 */
	public void update (T entity)throws HibernateException, SQLException;;
	/**
	 * 查询单个对象
	 * @param pk
	 * @return
	 */
	public T findOne (Serializable pk)throws HibernateException, SQLException;;
	/**
	 * 查询所有记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public List<T> find (String hql, Object... parameters)throws HibernateException, SQLException;;
	
	/**
	 * 根据hql查询单条记录
	 * @param hql
	 * @param parameters
	 * @return
	 */
	public T findOne(String hql, Object... parameters)throws HibernateException, SQLException;;
	/**
	 * 分页查询
	 * @param hql
	 * @param parameters
	 * @return 分页对象
	 */
	public Pagination<T> find4page (String hql, Object... parameters)throws HibernateException, SQLException;;
	/**
	 * 获取总记录数
	 * @param hql
	 * @param parameters
	 * @param isHql  是否是hql true:hql; false:sql
	 * @return
	 */
	public int getCount(String hql, boolean isHql, Object... parameters)throws HibernateException, SQLException;;
	/**
	 * 将查询单条记录
	 * @param hql
	 * @param parameters
	 * @return	以map返回数据
	 */
	public Map<String, Object> findOne4Map(String sql, Object... parameters)throws HibernateException, SQLException;;
	/**
	 * 查询多条记录
	 * @param hql
	 * @param parameters
	 * @return	以list返回
	 */
	public List<Map<String, Object>> find4ListMap(String sql, Object... parameters)throws HibernateException, SQLException;;
	/**
	 * 分页查询
	 * @param hql
	 * @param parameters
	 * @return	返回list分页数据
	 */
	public Pagination<List<Map<String, Object>>> find4ListPage (String hql, Object... parameters)throws HibernateException, SQLException;;
}
