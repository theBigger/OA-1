package com.fjx.oa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fjx.common.framework.base.dao.IBaseDao;
import com.fjx.oa.models.Organization;
import com.fjx.oa.vo.EasyuiTreeNode;

public interface IOrganizationService extends IBaseDao<Organization> {
	
	public void add(Organization organization, Serializable pid)throws Exception;

	public void saveOrUpdate(Organization organization, Serializable pid)throws Exception;
	
	public List<Organization> treeGrid(Serializable pid)throws Exception;
	
	public List<Map<String, Object>> jdbcTreeGrid(Serializable pid)throws Exception;

	public List<EasyuiTreeNode> tree(Serializable pid)throws Exception;

}
