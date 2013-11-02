package com.fjx.oa.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.Module;
import com.fjx.oa.service.IModuleService;



@Transactional
@Service
public class ModuleService extends BaseAbstractService<Module> implements IModuleService {

	@Override
	public List<Map<String, Object>> treeGrid4ListMap(Serializable pid)
			throws HibernateException, SQLException {
		String hql = "select " +
				" new map(m.id as id,m.name as name,m.sn as sn,m.icon as icon,m.url as url,m.order_no as order_no,m.parent.id as parent_id,m.parent.name as parent_name)" +
				" from Module m where m.parent.id = '"+pid+"'";
		if(null == pid || pid.equals(0)){
			hql = "select " +
				" new map(m.id as id,m.name as name,m.sn as sn,m.icon as icon,m.url as url,m.order_no as order_no,'' as parent_id,'' as parent_name) " +
				" from Module m where m.parent.id is null";
		}
		hql += " order by m.order_no asc";	//排序
		List<Map<String, Object>> modules = find4List(hql,true);
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
	public boolean isleef(Serializable id) throws HibernateException, SQLException{
		boolean flag = true;
		String hql = " from Module m" +
				" where 1=1 and m.parent.id = "+id;
		int count = getCount(hql, true);
		if(count > 0){
			flag = false;
		}
		return flag;
	}
	
	
}
