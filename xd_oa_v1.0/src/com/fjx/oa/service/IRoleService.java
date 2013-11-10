package com.fjx.oa.service;

import org.springframework.dao.DataAccessException;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.models.Role;


public interface IRoleService extends IBaseAbstractService<Role> {
	
	public void removeAll(Long[] pks ) throws DataAccessException;
	
}
