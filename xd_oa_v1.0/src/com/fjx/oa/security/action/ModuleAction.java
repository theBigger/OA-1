package com.fjx.oa.security.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.base.action.MyExecuteCallback;
import com.fjx.oa.security.service.IAuthenUrlService;
import com.fjx.oa.security.service.IModuleService;

/**
 * 模块管理
 * @date 2013年11月2日
 * @author fengjx
 *
 */
public class ModuleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IModuleService moduleService;
	
	/*******页面参数***************/
	private Long pid;
	private Long id;
	

	@Autowired
	private IAuthenUrlService authenUrlService;
	/**
	 * 树形表格
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String treeGrid  () throws Exception  {
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return moduleService.treeGrid4ListMap(id);
			}
		}, "获取模块列表数据发生错误");
		return null;
	}
	
	/**
	 * 模块树
	 * @return
	 * @throws Exception
	 */
	public String tree() throws Exception {
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return moduleService.tree(pid);
			}
		}, "获取模块列表数据发生错误");
		return null;
	}
	
	public String query_authenUrl_page() throws Exception{
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return authenUrlService.queryPage(id);
			}
		}, null);
		return null;
	}
	
	public IModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
