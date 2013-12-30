package com.fjx.oa.security.service;

import java.util.Map;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.security.models.AuthenUrl;
import com.fjx.oa.vo.EasyUIPagination;



public interface IAuthenUrlService extends IBaseAbstractService<AuthenUrl> {
	
	/**
	 * 查询某个模块下的所有安全认证的请求
	 * @param module_id
	 * @return
	 * @throws Exception
	 */
	public EasyUIPagination<Map<String, Object>> queryPage(Long module_id) throws Exception;
	
	
}
