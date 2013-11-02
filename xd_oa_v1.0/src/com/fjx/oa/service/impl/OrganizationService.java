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
			if(null != pid && !pid.equals("") && pid != "0"){
				parent = findEntityByPk(pid);
			}
			organization.setParent(parent);
			saveOrUpdate(organization);
		} catch (Exception e) {
			logger.error("保存或更新机构表异常", e);
			throw e;
		}
	}
	
	@Override
	public List<Map<String, Object>> treeGrid4ListMap(Serializable pid)throws Exception {
		String hql = "select " +
				" new map(o.id as id,o.name as name,o.sn as sn,o.description as description,o.in_time as in_time,o.parent.id as parent_id,o.parent.name as parent_name)" +
				" from Organization o where o.parent.id = '"+pid+"'";
		if(null == pid || pid.equals(0)){
			hql = "select " +
				" new map(o.id as id,o.name as name,o.sn as sn,o.description as description,o.in_time as in_time,'' as parent_id,'' as parent_name) " +
				" from Organization o where o.parent.id is null";
		}
		hql += " order by o.in_time asc";	//排序
		List<Map<String, Object>> orgs = find4List(hql,true);
		for(int i = 0; i < orgs.size(); i++){
			if(isleef(orgs.get(i).get("id")+"")){
				orgs.get(i).put("state", "open");
			}else{
				orgs.get(i).put("state", "closed");
			}
		}
		return orgs;
	}
	
	@Override
	public boolean isleef(Serializable id) throws Exception{
		boolean flag = true;
		String hql = " from Organization o" +
				" where 1=1 and o.parent.id = "+id;
		int count = getCount(hql, true);
		if(count > 0){
			flag = false;
		}
		return flag;
	}
	
	
	
	@Override
	public List<EasyuiTreeNode> tree(Serializable pid) throws Exception{
		
		String hql = "from Organization o where o.parent.id = '"+pid+"' ";
		if(null == pid || pid.equals(0)){
			hql = "from Organization o where o.parent is null";
		}
		List<Organization> list = find4List(hql, true);
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
		Organization parent = findEntityByPk(pid);
		organization.setParent(parent);
		save(organization);
	}

}
