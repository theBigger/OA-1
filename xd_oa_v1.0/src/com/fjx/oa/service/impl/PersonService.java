package com.fjx.oa.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.dao.impl.BaseDao;
import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.models.Person;
import com.fjx.oa.service.IPersonService;
import com.fjx.oa.vo.EasyUIPagination;


@Service("personService")
@Transactional
public class PersonService extends BaseAbstractService<Person> implements IPersonService {

	@Override
	public EasyUIPagination<List> queryPersons(String name, Date createDate,
			Date expireTime) throws Exception {
		String hql = "select new Person() from Person p " +
				" where p.name like ? and  p.user.createTime < ? and p.user.expireTime > ? ";
		Object[] parameters = {name,createDate,expireTime};
		Pagination<List<Map<String, Object>>> list = find4ListPage(hql,true, parameters);
		
		return null;
	}
	
	
	
	
}
