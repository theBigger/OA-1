package com.fjx.oa.security.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.base.action.MyExecuteCallback;
import com.fjx.oa.security.models.Role;
import com.fjx.oa.security.service.IRoleService;

/**
 * 角色管理
 * @author fengjx
 * 
 */
public class RoleAction extends BaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/******页面参数*******/
	private Role role;
	private Long[] ids;
	
	/*****实体注入*******/
	@Autowired
	private IRoleService roleService;
	

	public String roleList() throws Exception{
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return roleService.findList();
			}
		}, null);
		return null;
	}
	
	public String addOrUpdateRole() throws Exception {
		execute4State(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				roleService.saveOrUpdate(role);
				return null;
			}
		}, null);
		return null;
	}
	
	public String deleteRole() throws Exception {
		execute4State(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				roleService.removeAll(ids);
				return null;
			}
		}, null);
		return null;
	}
	
	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
}
