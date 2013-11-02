package com.fjx.oa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.models.Organization;
import com.fjx.oa.vo.EasyuiTreeNode;

public interface IOrganizationService extends IBaseAbstractService<Organization> {
	
	public void add(Organization organization, Serializable pid)throws Exception;
	
	/**
	 * 保存/修改机构
	 * @param organization
	 * @param pid
	 * @throws Exception
	 */
	public void saveOrUpdate(Organization organization, Serializable pid)throws Exception;
	
	/**
	 * 查询子节点
	 * @param pid 父级编号
	 * @return 
	 * @throws Exception
	 */
	public List<Map<String, Object>> treeGrid4ListMap(Serializable pid)throws Exception;
	
	/**
	 * 判断节点是否是叶子节点
	 * @return
	 * @throws Exception
	 */
	public boolean isleef(Serializable id) throws Exception;
	
	public List<EasyuiTreeNode> tree(Serializable pid)throws Exception;
	
}
