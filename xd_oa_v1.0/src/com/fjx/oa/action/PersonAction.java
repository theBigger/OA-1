package com.fjx.oa.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.system.exception.SystemException;
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
	
	/**
	 * 分页查询人员信息
	 * @return
	 * @throws Exception
	 */
	public String query_page() throws Exception{
		EasyUIPagination<List> easyPersons = null;
		try {
			easyPersons = personService.queryPersons("", null, null);
		}  catch (Exception e) {
			logger.error("获取人员分页数据异常", e);
			throw new SystemException("获取人员分页数据异常",e);
		}
		write(easyPersons);
		return null;
	}



	public Pagination<Person> getPersons() {
		return persons;
	}



	public void setPersons(Pagination<Person> persons) {
		this.persons = persons;
	}

}
