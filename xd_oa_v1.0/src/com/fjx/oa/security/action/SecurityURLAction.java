package com.fjx.oa.security.action;

import javax.annotation.Resource;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.security.models.AuthenUrl;
import com.fjx.oa.security.service.ISecurityURLService;




public class SecurityURLAction extends BaseAction {
	
	
	private ISecurityURLService securityURLService;
	private AuthenUrl authenUrl;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String query_page() throws Exception{
		Pagination<AuthenUrl> page = securityURLService.page();
		write(page);
		return null;
	}
	
	
	public String saveOrUpdate() throws Exception{
		boolean flag = securityURLService.saveOrUpdate(authenUrl);
		if(flag){
			write("success");
		}else{
			write("fail");
		}
		return null;
	}
	
	
	public String delete() throws Exception{
		boolean flag = securityURLService.delete(authenUrl);
		if(flag){
			write("success");
		}else{
			write("fail");
		}
		return null;
	}


	public ISecurityURLService getSecurityURLService() {
		return securityURLService;
	}

	@Resource
	public void setSecurityURLService(ISecurityURLService securityURLService) {
		this.securityURLService = securityURLService;
	}


	public AuthenUrl getAuthenUrl() {
		return authenUrl;
	}


	public void setAuthenUrl(AuthenUrl authenUrl) {
		this.authenUrl = authenUrl;
	}


}
