package com.fjx.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.common.framework.base.action.MyExecuteCallback;
import com.fjx.oa.models.Organization;
import com.fjx.oa.service.IOrganizationService;


public class OrgAction extends BaseAction {

	//对象注入
	@Autowired
	private IOrganizationService organizationService;
	
	/*******页面参数***************/
	private Long pid;
	private Long id;
	private Organization org;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4333403511458402875L;
	
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
				return organizationService.treeGrid4ListMap(id);
			}
		}, "获取机构列表数据出现异常");
		
//		try {
//			jsonList = organizationService.treeGrid4ListMap(id);
//		} catch (Exception e) {
//			logger.error("", e);
//			throw  new SystemException("获取机构列表数据发生错误",e);
//		}
//		write(jsonList);
		return null;
	}
	/**
	 * 机构树
	 * @return
	 * @throws Exception
	 */
	public String tree() throws Exception {
		execute4ResultJson(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				return organizationService.tree(pid);
			}
		}, "查询机构树出现异常");
//		List<EasyuiTreeNode> list = null;
//		try {
//			list = organizationService.tree(pid);
//		} catch (Exception e) {
//			logger.error("查询机构树出现异常", e);
//			throw new SystemException("查询机构树出现异常",e);
//		}
//		write(list);
		return null;
	}
	
	/**
	 * 保存或者修改
	 * @return	saveOrUpdate
	 */
	public String save() throws Exception{
		execute4State(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				organizationService.saveOrUpdate(org, pid);
				return null;
			}
		}, "保存机构失败");
		return null;
	}
	
	public String delete() throws Exception{
		execute4State(new MyExecuteCallback() {
			@Override
			public Object execute() throws Exception {
				organizationService.saveOrUpdate(org, pid);
				return null;
			}
		}, "删除机构失败");
		return null;
	}
	

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
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
