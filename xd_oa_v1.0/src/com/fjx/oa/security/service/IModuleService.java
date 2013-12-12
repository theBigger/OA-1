package com.fjx.oa.security.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.fjx.oa.vo.EasyuiTreeNode;


public interface IModuleService {
	
	/**
	 * 查询子节点
	 * @param pid 父级编号
	 * @return 
	 * @throws Exception
	 */
	public List<Map<String, Object>> treeGrid4ListMap(Serializable pid)throws Exception;
	
	/**
	 * 通过父级ID查询子节模块
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public List<EasyuiTreeNode> tree(Serializable pid) throws Exception;
	
}
