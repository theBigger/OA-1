package com.fjx.oa.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.oa.models.Organization;
import com.fjx.oa.service.IOrganizationService;
import com.fjx.oa.vo.EasyuiTreeNode;



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
	
	
	public String view (){
		logger.debug("com.fjx.oa.action.view()");
		return "view";
	}
	
	/**
	 * 树形表格
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String treeGrid  ()  {
		List<Map<String, Object>> list = null;
		String res = "";
		try {
			list = organizationService.treeGrid4ListMap(id);
			res = serialize(list);
		} catch (Exception e) {
			logger.error("获取机构列表数据发生错误", e);
		}
		write(res);
		return null;
	}
	/**
	 * 机构树
	 * @return
	 * @throws Exception
	 */
	public String tree() throws Exception {
		List<EasyuiTreeNode> list = null;
		try {
			list = organizationService.tree(pid);
		} catch (Exception e) {
			logger.error("查询机构树出现异常", e);
			throw new RuntimeException("查询机构树出现异常",e);
		}
		write(serialize(list));
		return null;
	}
	
	/**
	 * 保存或者修改
	 * @return	saveOrUpdate
	 */
	public String save() throws Exception{
		String res = null;
		try{
			organizationService.saveOrUpdate(org, pid);
			res = "success";
		}catch (Exception e) {
			res = "fail";
			logger.error("保存机构失败", e);
			throw new RuntimeException("保存机构失败",e);
		}
		write(res);
		return null;
	}
	
	public String delete(){
		String flag = "fail";
		try {
			organizationService.delete(id);
			flag = "success";
		} catch (Exception e) {
			throw new RuntimeException("删除机构失败",e);
		}
		write(flag);
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
