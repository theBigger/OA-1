package com.fjx.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.Role;
import com.fjx.oa.service.IRoleService;


@Service
@Transactional
public class RoleService extends BaseAbstractService<Role> implements IRoleService {
	
}
