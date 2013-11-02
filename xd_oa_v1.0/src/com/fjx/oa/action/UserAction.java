package com.fjx.oa.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.oa.models.User;
import com.fjx.oa.service.IUserService;
import com.fjx.oa.vo.EasyUIPagination;


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
	
	
	public String view(){
		return "view";
	}
	
	public String query_page() throws Exception{
		EasyUIPagination<List<Map>> page = null;
		page = userService.queryPageUsers();
		write(page);
		return null;
	}
	
	
	
	

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
	
}
