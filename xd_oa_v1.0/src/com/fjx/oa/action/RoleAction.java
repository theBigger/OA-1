package com.fjx.oa.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.oa.models.Role;
import com.fjx.oa.service.IRoleService;

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
	
	
	public String view(){
		return super.view();
	}
	
	public String roleList() throws Exception{
		List<Role> list = null;
		list = roleService.findAllEntity();
		write(list);
		return null;
	}
	
	public String addOrUpdateRole() throws Exception {
		String res = "error";
		try {
			roleService.saveOrUpdate(role);
			res = "success";
		} catch (Exception e) {
			logger.error("保存或修改角色异常", e);
		}
		write(res);
		return null;
	}
	
	public String deleteRole() throws Exception {
		String res = "error";
		try {
			roleService.removeAll(ids);
			res = "success";
		} catch (Exception e) {
			logger.error("删除角色异常", e);
		}
		write(res);
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
