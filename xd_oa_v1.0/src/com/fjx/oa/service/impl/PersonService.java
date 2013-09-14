package com.fjx.oa.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.dao.impl.BaseDao;
import com.fjx.oa.models.Person;
import com.fjx.oa.service.IPersonService;


@Service("personService")
@Transactional
public class PersonService extends BaseDao<Person> implements IPersonService {
	
	
	
	
}
