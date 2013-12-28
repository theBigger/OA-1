package com.fjx.oa.service;

import java.util.Date;
import java.util.Map;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.models.Person;
import com.fjx.oa.vo.EasyUIPagination;


/**
 * 
 * @author fengjx
 * @date 2013年9月14日
 * 人员管理
 */
public interface IPersonService extends IBaseAbstractService<Person> {
	
	
	EasyUIPagination<Map<String, Object>> queryPersons(String name,Date createDate,Date expireTime)throws Exception;
	
	
}
