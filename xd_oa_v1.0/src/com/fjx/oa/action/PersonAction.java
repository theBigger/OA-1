package com.fjx.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.models.Person;
import com.fjx.oa.service.IPersonService;
import com.fjx.oa.vo.EasyUIPagination;



public class PersonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IPersonService personService;
	
	/******页面参数********/
	private Pagination<Person> persons;
	private EasyUIPagination<Person> easyPersons;
	
	public String view(){
		return "view";
	}
	
	
	/**
	 * 分页查询人员信息
	 * @return
	 * @throws Exception
	 */
	public String query_page() throws Exception{
		persons = personService.find4page();
		easyPersons = new EasyUIPagination<Person>(persons);
		write(serialize(easyPersons));
		return "persons_page";
	}



	public Pagination<Person> getPersons() {
		return persons;
	}



	public void setPersons(Pagination<Person> persons) {
		this.persons = persons;
	}


	public EasyUIPagination<Person> getEasyPersons() {
		return easyPersons;
	}


	public void setEasyPersons(EasyUIPagination<Person> easyPersons) {
		this.easyPersons = easyPersons;
	}
	
}
