package com.fjx.oa.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.ACL;
import com.fjx.oa.service.IAclService;


/**
 * 访问控制
 * @author fengjx
 *
 */
@Service
@Transactional
public class AclService extends BaseAbstractService<ACL> implements IAclService {
	
	@Override
	public void addOrUpdatePermission(String principalType, Long principalId,
			Long moduleId, int permission, boolean yes) throws HibernateException, SQLException {
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
			Long moduleId) throws HibernateException, SQLException {
		String hql = "delete ACL acl where acl.principalType = ? " +
			"and acl.principalId=? and acl.moduleId = ?";
		bulkUpdate(hql,principalType, principalId, moduleId);
	}

	@Override
	public void addOrUpdateUserExtends(Long userId, Long moduleId, boolean yes) throws HibernateException, SQLException {
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
	public boolean hasPermission(Long userId, Long moduleId, int permission) throws HibernateException, SQLException {
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
		String hql = "select new long(r.id role_id) from UsersRoles ur join ur.role r join ur.user u " +
				"where u.id = ? order by ur.orderNo";
		List<Long> roleIds = find4List(hql, true, userId);
		
		
		return false;
	}

	@Override
	public boolean hasPermissionByResourceSn(Long userId, String reourceSn,
			int permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List searchModules(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List searchAclRecord(String principalType, Long principalId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 查找授权记录，如果找不到，返回空值
	 * @param principalType
	 * @param principalId
	 * @param moduleId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 * @throws Exception 
	 */
	private ACL findACL(String principalType,Long principalId,Long moduleId) throws HibernateException, SQLException {
		ACL acl = null;
		String hql = "from ACL acl where acl.principalType = ? " +
			"and acl.principalId=? and acl.moduleId = ?";
		acl = find4Unique(hql,true, principalType,principalId,moduleId);
		return acl;
	}
	
}
