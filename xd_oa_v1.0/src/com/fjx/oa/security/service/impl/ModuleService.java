package com.fjx.oa.security.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.Organization;
import com.fjx.oa.security.models.Module;
import com.fjx.oa.security.service.IModuleService;
import com.fjx.oa.vo.EasyuiTreeNode;



@Transactional
@Service
public class ModuleService extends BaseAbstractService<Module> implements IModuleService {

	@Override
	public List<Map<String, Object>> treeGrid4ListMap(Serializable pid)
			throws Exception {
		String hql = "select " +
				" new map(m.id as id,m.name as name,m.sn as sn,m.icon as icon,m.url as url,m.order_no as order_no,m.parent.id as parent_id,m.parent.name as parent_name)" +
				" from Module m where m.parent.id = '"+pid+"'";
		if(null == pid || pid.equals(0)){
			hql = "select " +
				" new map(m.id as id,m.name as name,m.sn as sn,m.icon as icon,m.url as url,m.order_no as order_no,'' as parent_id,'' as parent_name) " +
				" from Module m where m.parent.id is null";
		}
		hql += " order by m.order_no asc";	//排序
		List<Map<String, Object>> modules = findListByHql(hql);
		for(int i = 0; i < modules.size(); i++){
			if(isleef(modules.get(i).get("id")+"")){
				modules.get(i).put("state", "open");
			}else{
				modules.get(i).put("state", "closed");
			}
		}
		return modules;
	}
	
	/**
	 * 判断节点是否是叶子节点
	 * @return
	 * @throws Exception
	 */
	public boolean isleef(Serializable id) throws Exception{
		boolean flag = true;
		String hql = " from Module m" +
				" where 1=1 and m.parent.id = "+id;
		int count = getCount(hql, true);
		if(count > 0){
			flag = false;
		}
		return flag;
	}
	
	@Override
	public List<EasyuiTreeNode> tree(Serializable pid) throws Exception{
		
		String hql = "from Module m where m.parent.id = '"+pid+"' ";
		if(null == pid || pid.equals(0)){
			hql = "from Module m where m.parent is null";
		}
		hql += " order by m.order_no";
		List<Module> list = findListByHql(hql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (Module org : list) {
			tree.add(tree(org, true));
		}
		return tree;
		
	}
	
	/**
	 * @param org
	 * @param recursive
	 * @return
	 * @throws Exception
	 */
	private EasyuiTreeNode tree(Module module, boolean recursive)throws Exception {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(module.getId()+"");
		node.setText(module.getName());
		if (module.getChildren() != null && module.getChildren().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Module> moduleList = new ArrayList<Module>(module.getChildren());
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (Module m : moduleList) {
					EasyuiTreeNode t = tree(m, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}
}
