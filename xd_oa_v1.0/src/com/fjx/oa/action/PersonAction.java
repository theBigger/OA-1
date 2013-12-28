package com.fjx.oa.action;


import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.base.action.MyExecuteCallback;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.models.Person;
import com.fjx.oa.service.IPersonService;


public class PersonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IPersonService personService;
	
	/******页面参数********/
	private Pagination<Person> persons;
	
	/**
	 * 分页查询人员信息
	 * @return
	 * @throws Exception
	 */
	public String query_page() throws Exception{
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return personService.queryPersons("", null, null);
			}
		}, "获取人员分页数据异常");
		return null;
	}

	public Pagination<Person> getPersons() {
		return persons;
	}

	public void setPersons(Pagination<Person> persons) {
		this.persons = persons;
	}

}
