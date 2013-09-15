package com.fjx.oa.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.Organization;
import com.fjx.oa.service.IOrganizationService;
import com.fjx.oa.vo.EasyuiTreeNode;



@Service("orgServices")
@Transactional
public class OrganizationService extends BaseAbstractService<Organization> implements IOrganizationService {
	
	
	@Override
	public void saveOrUpdate(Organization organization, Serializable pid)throws Exception {
		try {
			Organization parent = null;
			if(pid != null && !pid.equals("") && pid != "0"){
				parent = findOne(pid);
			}
			organization.setParent(parent);
			saveOrUpdate(organization);
		} catch (Exception e) {
			logger.error("保存或更新机构表异常", e);
			throw new RuntimeException("保存或更新机构表异常",e);
		}
	}
	
	@Override
	public List<Organization> treeGrid(Serializable pid) throws Exception{
		String hql = "from Organization o where o.parent.id = '"+pid+"' ";
		if(null == pid || pid.equals(0)){
			hql = "from Organization o where o.parent is null";
		}
		List<Organization> list = null;
		try {
			list = find(hql);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Organization o = list.get(i);
					if (null != o.getParent()) {
						list.get(i).setParent_id(o.getParent().getId());
						list.get(i).setParent_name(o.getParent().getName());
					}
					if (null != o.getChildren() && o.getChildren().size() > 0) {
						list.get(i).setState("closed");
						List<Organization> children = treeGrid(o.getId());
						list.get(i).setChildren(children);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询树形机构异常", e);
			throw new RuntimeException("查询树形机构异常",e);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> jdbcTreeGrid(Serializable pid)throws Exception {
		String sql = "select * from oa_org o where o.parent_id = '"+pid+"'";
		if(null == pid || pid.equals(0)){
			sql = "select * from oa_org o where o.parent_id is null";
		}
		List<Map<String, Object>> orgs = find4ListMap(sql,false);
		if(null != orgs && orgs.size()>0){
			for(int i = 0; i < orgs.size(); i++){
				List<Map<String, Object>> children = jdbcTreeGrid(orgs.get(i).get("id").toString());
				orgs.get(i).put("children", children);
			}
		}
		return orgs;
	}
	
	@Override
	public List<EasyuiTreeNode> tree(Serializable pid) throws Exception{
		
		String hql = "from Organization o where o.parent.id = '"+pid+"' ";
		if(null == pid || pid.equals(0)){
			hql = "from Organization o where o.parent is null";
		}
		List<Organization> list = find(hql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (Organization org : list) {
			tree.add(tree(org, true));
		}
		return tree;
	}
	
	private EasyuiTreeNode tree(Organization org, boolean recursive)throws Exception {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(org.getId()+"");
		node.setText(org.getName());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("sn", org.getSn());
		attributes.put("desc", org.getDescription());
		attributes.put("in_time", org.getIn_time());
		node.setAttributes(attributes);
		if (org.getChildren() != null && org.getChildren().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Organization> orgList = new ArrayList<Organization>(org.getChildren());
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (Organization o : orgList) {
					EasyuiTreeNode t = tree(o, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	@Override
	public void add(Organization organization, Serializable pid)throws Exception {
		Organization parent = findOne(pid);
		organization.setParent(parent);
		save(organization);
	}

}
