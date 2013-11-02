package com.fjx.oa.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;


public interface IModuleService {
	
	/**
	 * 查询子节点
	 * @param pid 父级编号
	 * @return 
	 * @throws Exception
	 */
	public List<Map<String, Object>> treeGrid4ListMap(Serializable pid)throws HibernateException,SQLException;
	
	
	
}
