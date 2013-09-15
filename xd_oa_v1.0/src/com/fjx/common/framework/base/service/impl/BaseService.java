package com.fjx.common.framework.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.dao.IBaseDao;
import com.fjx.common.framework.base.service.IBaseService;
import com.fjx.common.framework.system.pagination.Pagination;


@Service
@Transactional
public class BaseService<T> implements IBaseService<T> {
	
	
	private IBaseDao<T> baseDao;
	
	@Override
	public void save(T entity) throws Exception{
		baseDao.save(entity);
	}

	@Override
	public void saveOrUpdate(T entity)throws Exception {
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Serializable pk) throws Exception{
		baseDao.delete(pk);
	}

	@Override
	public void update(T entity)throws Exception {
		baseDao.update(entity);
	}

	@Override
	public T findOne(Serializable pk)throws Exception {
		return baseDao.findOne(pk);
	}
	
	@Override
	public T findOne(String hql, Object... parameters)throws Exception {
		return baseDao.findOne(hql, parameters);
	}

	@Override
	public List<T> find(String hql, Object... parameters)throws Exception {
		return baseDao.find(hql, parameters);
	}

	@Override
	public Pagination<T> find4page(String hql, Object... parameters)throws Exception {
		return baseDao.find4page(  hql, parameters);
	}

	@Override
	public int getCount(String hql, boolean isHql, Object... parameters) throws Exception{
		return baseDao.getCount(hql, isHql, parameters);
	}
	
	@Override
	public Map<String, Object> findOne4Map(String sql, Object... parameters)throws Exception {
		return baseDao.findOne4Map(sql,false, parameters);
	}

	@Override
	public List<Map<String, Object>> find4ListMap(String sql, Object... parameters) throws Exception{
		return baseDao.find4ListMap(sql,false, parameters);
	}

	@Override
	public Pagination<List<Map<String, Object>>> find4ListPage(String hql,
			Object... parameters) throws Exception{
		return baseDao.find4ListPage(hql,false, parameters);
	}
	
	@Resource(name="baseDao")
	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

}
