package com.fjx.oa.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.ACL;
import com.fjx.oa.models.Module;
import com.fjx.oa.security.init.Permission;
import com.fjx.oa.service.IAclService;


/**
 * 访问控制
 * @author fengjx
 *
 */
@Service(value="aclService")
@Transactional
public class AclService extends BaseAbstractService<ACL> implements IAclService {
	
	@Override
	public void addOrUpdatePermission(String principalType, Long principalId,
			Long moduleId, int permission, boolean yes) throws Exception {
		ACL acl = findACL(principalType, principalId, moduleId);
		
		//能够找到ACL对象，更新permission
		if(acl != null){
			acl.setPermission(permission, yes);
			update(acl);
			return;
		}
		//找不到ACL对象，则创建ACL对象，并更新permission
		acl = new ACL();
		acl.setPrincipalType(principalType);
		acl.setPrincipalId(principalId);
		acl.setModuleId(moduleId);
		acl.setPermission(permission, yes);
		save(acl);
	}

	@Override
	public void delPermission(String principalType, Long principalId,
			Long moduleId) throws Exception {
		String hql = "delete ACL acl where acl.principalType = ? " +
			"and acl.principalId=? and acl.moduleId = ?";
		bulkUpdate(hql,principalType, principalId, moduleId);
	}

	@Override
	public void addOrUpdateUserExtends(Long userId, Long moduleId, boolean yes) throws Exception {
		//查找ACL对象
		ACL acl = findACL(ACL.TYPE_USER,userId,moduleId);
		//能够找到ACL对象，更新permission
		if(acl != null){
			acl.setExtends(yes);
			update(acl);
			return;
		}
		//找不到ACL对象，则创建ACL对象，并更新permission
		acl = new ACL();
		acl.setPrincipalType(ACL.TYPE_USER);
		acl.setPrincipalId(userId);
		acl.setModuleId(moduleId);
		acl.setExtends(yes);
		save(acl);
	}
	
	@Override
	public boolean hasPermission(Long userId, Long moduleId, int permission) throws Exception {
		//根据用户标识和模块标识查找授权记录
		ACL acl = findACL(ACL.TYPE_USER,userId,moduleId);
		
		//有授权记录
		if( null != acl){
			int yesOrNo = acl.getPermission(permission);
			//如果是确定的授权
			if(yesOrNo != ACL.ACL_NEUTRAL){
				//立刻返回，无需继续查找
				return yesOrNo == ACL.ACL_YES ? true : false;
			}
		}
		//继续查找用户拥有的角色的授权
		
		//查找分配给用户的角色，按优先级从高到低排序
		String hql = "select r.id  from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ? order by ur.orderNo";
		List<Long> roleIds = findListByHql(hql, userId);
		//遍历查询出来的角色，通过角色判断用户权限
		for(Long roleId : roleIds){
			//通过角色查询用户权限
			acl = findACL(ACL.TYPE_ROLE, roleId, moduleId);
			if(acl != null){
				return acl.getPermission(permission) == ACL.ACL_YES ? true : false;
			}
		}
		
		return false;
	}

	@Override
	public boolean hasPermissionByResourceSn(Long userId, String reourceSn,
			int permission)throws Exception  {
		String hql = "select m.id from Module m where m.sn = ? ";
		return hasPermission(
				userId,
				(Long) findUniqueByHql(hql, reourceSn),
				permission);
	}
	
	@Override
	public List<Module> searchModules(Long userId) throws Exception {
		Map<String, ACL> temp = new HashMap<String, ACL>();
		
		//查找用户拥有的角色，并按优先级从低到高排序
		String hql = "select new long(r.id 'role_id') from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ? order by ur.orderNo desc";
		
		//依次查找角色的授权（ACL对象）
		List<Long> roles = findListByHql(hql, userId);
		for(Long roleId : roles){
			List<ACL> acls = findRoleAcls(roleId);
			for(ACL acl:acls){
				temp.put(acl.getModuleId()+"", acl);
			}
		}
		
		//针对用户查找有效的用户授权
		List<ACL> acls = findUserAcls(userId);
		for(ACL acl:acls){
			temp.put(acl.getModuleId()+"", acl);
		}
		
		//去除掉那些没有读取权限的授权记录
		Set entries = temp.entrySet();
		for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			ACL acl = (ACL)entry.getValue();
			if(acl.getPermission(Permission.READ) != ACL.ACL_YES){
				iterator.remove();
			}
		}
		
		if(temp.isEmpty()){
			return new ArrayList();
		}
		
		String queryModules = " from Module m where m.id in (:ids)";
		
		return findListByHql(queryModules, temp.keySet());
	}
	
	
	@Override
	public List<Map> searchAclRecord(String principalType, Long principalId) throws Exception {
		List<Map> result = null;
		//使用原生sql语句
		String sql = "select moduleId,aclState&1 'cState',aclState&2 'rState',aclState&4 'uState',aclState&8 'dState',aclTriState 'extState'" +
				"from oa_acl where principalType = '"+principalType+"' and principalId = "+principalId;
		result = findListMapBySql(sql);
		return result;
	}
	
	
	/**
	 * 通过主体标识、字体编号、资源编号查找唯一授权记录，如果找不到，返回空值
	 * @param principalType
	 * @param principalId
	 * @param moduleId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 * @throws Exception 
	 */
	private ACL findACL(String principalType,Long principalId,Long moduleId) throws Exception {
		ACL acl = null;
		String hql = "from ACL acl where acl.principalType = ? " +
			"and acl.principalId=? and acl.moduleId = ?";
		acl = findOneByHql(hql, principalType,principalId,moduleId);
		return acl;
	}
	
	/**
	 * 通过角色ID查询该角色所拥有的所有授权（ACL）
	 * @param roleId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	private List<ACL> findRoleAcls(Long roleId) throws Exception{
		String hql = "select acl from ACL acl where acl.principalType = ? " +
				"and acl.principalId = ?";
		List<ACL> result = findListByHql(hql, ACL.TYPE_ROLE,roleId);
		return result;
	}
	
	/**
	 * 通过用户ID查询该角色所拥有的所有授权（ACL）
	 * @param userId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	private List<ACL> findUserAcls(Long userId) throws Exception{
		String hql = "select acl from ACL acl where acl.principalType = ? " +
				"and acl.principalId = ? and acl.aclTriState = ? ";
		List<ACL> result = findListByHql(hql, ACL.TYPE_USER ,userId,ACL.ACL_TRI_STATE_UNEXTENDS);
		return result;
	}
}
