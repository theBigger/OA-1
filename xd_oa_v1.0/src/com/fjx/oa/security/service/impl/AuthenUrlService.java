package com.fjx.oa.security.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.security.models.AuthenUrl;
import com.fjx.oa.security.service.IAuthenUrlService;
import com.fjx.oa.vo.EasyUIPagination;

@Service
@Transactional
public class AuthenUrlService extends BaseAbstractService<AuthenUrl> implements IAuthenUrlService {


	@Override
	public EasyUIPagination<Map<String, Object>> queryPage(String module_id) throws Exception {
		String hql = "from AuthenUrl a where 1=1 and a.is_valid = 1" +
				" and a.module_id = ?";
		Pagination<Map<String, Object>> page = pageByHql(hql,module_id);
		EasyUIPagination<Map<String, Object>> easyPagination = new EasyUIPagination<Map<String, Object>>(page);
		return easyPagination;
	}

}
