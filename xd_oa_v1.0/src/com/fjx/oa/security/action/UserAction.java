package com.fjx.oa.security.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.base.action.MyExecuteCallback;
import com.fjx.oa.security.service.IUserService;

/**
 * 用户管理
 * @date 2013年11月1日
 * @author fengjx
 *
 */
public class UserAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IUserService userService;
	
	public String query_page() throws Exception{
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return userService.queryPageUsers();
			}
		}, null);
		return null;
	}
	

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
	
}
