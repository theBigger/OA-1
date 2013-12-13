package com.fjx.oa.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.oa.security.service.IAclService;
import com.fjx.oa.security.service.IModuleService;
import com.fjx.oa.vo.EasyuiTreeNode;

public class AclAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*******页面参数***************/
	private Long pid;
	private String role_id;

	@Autowired
	private IAclService aclService;
	
	@Autowired
	private IModuleService moduleService;
	
	
	public String view() {
		return super.view();
	}
	
	public String moduleTree() throws Exception {
		List<EasyuiTreeNode> list = null;
		moduleService.tree(pid);
		write(list);
		return null;
	}
	
	
	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public IAclService getAclService() {
		return aclService;
	}

	public void setAclService(IAclService aclService) {
		this.aclService = aclService;
	}

	public IModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
}
