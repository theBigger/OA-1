package com.fjx.oa.security.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.security.modules.Role;
import com.fjx.oa.security.service.IRoleService;


@Service
@Transactional
public class RoleService extends BaseAbstractService<Role> implements IRoleService {

	@Override
	public void removeAll(Long[] pks)
			throws Exception {
		Set<Role> roles = new HashSet<Role>();
		for(Serializable pk : pks){
			roles.add(get(Role.class,pk));
		}
		deleteAll(roles);
	}

	
}
