package com.fjx.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
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
	
	public String view(){
		return "view";
	}

	public String query_page() throws Exception{
		persons = personService.find4page();
		write(serialize(persons));
		return null;
	}



	public Pagination<Person> getPersons() {
		return persons;
	}



	public void setPersons(Pagination<Person> persons) {
		this.persons = persons;
	}
	
}
