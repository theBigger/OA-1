package com.fjx.oa.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.system.exception.SystemException;
import com.fjx.oa.service.IModuleService;
import com.fjx.oa.vo.EasyuiTreeNode;

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
	
	
	public String view(){
		return super.view();
	}
	
	/**
	 * 树形表格
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String treeGrid  () throws Exception  {
		List<Map<String, Object>> list = null;
		try {
			list = moduleService.treeGrid4ListMap(id);
		} catch (Exception e) {
			logger.error("获取模块列表数据发生错误", e);
			throw  new SystemException("获取模块列表数据发生错误",e);
		}
		write(list);
		return null;
	}
	
	
	/**
	 * 模块树
	 * @return
	 * @throws Exception
	 */
	public String tree() throws Exception {
		List<EasyuiTreeNode> list = null;
		try {
			list = moduleService.tree(pid);
		} catch (Exception e) {
			logger.error("查询机构树出现异常", e);
			throw new SystemException("查询机构树出现异常",e);
		}
		write(list);
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
