package com.fjx.oa.security.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.security.modules.ACL;
import com.fjx.oa.security.modules.Module;



public interface IAclService extends IBaseAbstractService<ACL> {
	
	/**
	 * 添加或更新授权
	 * @param principalType 主体类型标识：如角色或用户
	 * @param principalId 主体标识
	 * @param moduleId 模块标识
	 * @param permission 操作标识（C/R/U/D）
	 * @param yes 是否允许
	 */
	public void addOrUpdatePermission(String principalType,
			Long principalId,Long moduleId,
			int permission,boolean yes) throws Exception;
	
	/**
	 * 删除授权
	 * @param principalType 主体类型（用户/角色）
	 * @param principalId 主体标识
	 * @param moduleId 资源标识
	 */
	public void delPermission(String principalType,Long principalId,Long moduleId)throws Exception;
	
	/**
	 * 设置aclTriState的值
	 * @param userId 用户标识
	 * @param moduleId 模块标识
	 * @param yes 是否有效
	 */
	public void addOrUpdateUserExtends(Long userId,Long moduleId,boolean yes) throws Exception;
	
	/**
	 * 即时认证，判断某个用户是否拥有对某个模块的某个操作的权限
	 * @param userId 用户标识
	 * @param moduleId 模块标识
	 * @param permission 操作标识（C/R/U/D）
	 * @return 允许或不允许
	 */
	public boolean hasPermission(Long userId,Long moduleId,int permission)throws Exception;
	
	/**
	 * 判断用户对某模块的某操作的授权（允许或不允许）
	 * @param userId 用户ID
	 * @param reourceSn 资源唯一标识（sn）
	 * @param permission 权限（C/R/U/D）
	 * @return 允许（true）或不允许（false）
	 */
	public boolean hasPermissionByResourceSn(Long userId,String reourceSn,int permission)throws Exception;
	
	/**
	 * 查询用户拥有读取权限的模块列表
	 * @param userId 用户标识
	 * @return 列表元素是Module对象
	 */
	public List<Module> searchModules(Long userId)throws Exception;
	
	/**
	 * 
	 * @param principalType	主体类型
	 * @param principalId	主体标识
	 * @return 
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public List<Map> searchAclRecord(String principalType,Long principalId)throws Exception;
	
	
}
