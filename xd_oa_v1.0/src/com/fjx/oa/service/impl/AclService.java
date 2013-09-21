package com.fjx.oa.service.impl;

import java.util.List;

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
	public void addOrUpdateUserExtends(Long userId, Long moduleId, boolean yes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasPermission(Long userId, Long moduleId, int permission) {
		// TODO Auto-generated method stub
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
	 * @throws Exception 
	 */
	private ACL findACL(String principalType,Long principalId,Long moduleId) throws Exception {
		ACL acl = null;
		String hql = "from ACL acl where acl.principalType = ? " +
			"and acl.principalId=? and acl.moduleId = ?";
		acl = findOne(hql, principalType,principalId,moduleId);
		
		return acl;
	}
	
}
