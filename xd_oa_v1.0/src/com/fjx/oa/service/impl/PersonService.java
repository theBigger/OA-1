package com.fjx.oa.service.impl;


import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.models.Person;
import com.fjx.oa.service.IPersonService;
import com.fjx.oa.vo.EasyUIPagination;


@Service("personService")
@Transactional
public class PersonService extends BaseAbstractService<Person> implements IPersonService {

	@Override
	public EasyUIPagination<Map<String, Object>> queryPersons(String name, Date createDate,
			Date expireTime) throws Exception {
		String hql = "select " +
				" new map(p.id as id,p.name as name,p.sex as sex,p.age as age,p.address as address,p.duty as duty,p.phone as phone,p.org.id as org_id,p.org.name as org_name,p.user.id,p.user.createTime as createTime) " +
				" from Person p " +
				" where 1=1";
		if(null != name && !"".equals(name)){
			hql += " p.name like '%"+name+"%'"; 
		}
		Object[] parameters = {name,createDate,expireTime};
		Pagination<Map<String, Object>> list = pageByHql(hql);
		EasyUIPagination<Map<String, Object>> easyPagination = new EasyUIPagination<Map<String, Object>>(list);
		return easyPagination;
	}
	
	
	
	
}
