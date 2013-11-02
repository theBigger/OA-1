package com.fjx.oa.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.common.framework.system.exception.SystemException;
import com.fjx.common.framework.system.pagination.Pagination;
import com.fjx.oa.models.Person;
import com.fjx.oa.models.Role;
import com.fjx.oa.models.User;
import com.fjx.oa.models.UsersRoles;
import com.fjx.oa.service.IUserService;
import com.fjx.oa.vo.EasyUIPagination;


@Service("userService")
@Transactional
public class UserService extends BaseAbstractService<User> implements IUserService {
	
	@Override
	public void addUser(User user, Serializable personId) throws HibernateException, SQLException {
		if(null != personId && !personId.equals("") && personId != "0"){
			throw new SystemException("建立用户帐号时，人员信息不允许为空");
		}
		user.setPerson(loadEntity(Person.class, personId));
		user.setCreateTime(new Date());
		save(user);
	}

	@Override
	public void updateUser(User user, Serializable personId) throws HibernateException, SQLException {
		if(null != personId && !personId.equals("") && personId != "0"){
			throw new SystemException("建立用户帐号时，人员信息不允许为空");
		}
		user.setPerson(loadEntity(Person.class, personId));
		update(user);
	}

	@Override
	public void addOrUpdateUserRole(Serializable userId, Serializable roleId, int orderNo) throws HibernateException, SQLException {
		//首先根据userId和roleId，判断这两者之间是否已建立关联
		UsersRoles ur = findUsersRoles(userId, roleId);
		
		if(ur == null){
			ur = new UsersRoles();
			ur.setRole(loadEntity(Role.class, roleId));
			ur.setUser(loadEntity(User.class, userId));
			ur.setOrderNo(orderNo);
			getHibernateTemplate().save(ur);
		}
		
		ur.setOrderNo(orderNo);
		getHibernateTemplate().update(ur);
	}

	@Override
	public void delUserRole(Serializable userId, Serializable roleId) throws HibernateException, SQLException {
		getHibernateTemplate().delete(findUsersRoles(userId, roleId));
	}

	@Override
	public List<UsersRoles> searchUserRoles(Long userId) throws HibernateException, SQLException {
		String hql = " from UsersRoles ur " +
				"where ur.user.id = ? order by ur.orderNo";
		return find4List(hql, true, userId);
	}

	@Override
	public User login(String username, String password) throws HibernateException, SQLException {
		String queryUserHql = "select u from com.bjsxt.oa.model.User u where u.username = ?";
		
		User user = find4Unique(queryUserHql, true, username);
		
		if(user == null){
			throw new SystemException("没有这个用户");
		}
		
		if(!user.getPassword().equals(password)){
			throw new SystemException("密码错误！");
		}
		
		if(user.getExpireTime() != null){
			
			//现在时间
			Calendar now = Calendar.getInstance();
			//失效时间
			Calendar expireTime = Calendar.getInstance();
			expireTime.setTime(user.getExpireTime());
			
			//如果现在在失效时间之后
			if(now.after(expireTime)){
				throw new SystemException("用户帐号已失效！");
			}
		}
		return user;
	}

	@Override
	public List<User> searchUsersByRole(String roleName) throws HibernateException, SQLException {
		String hql = "select u from UsersRoles ur " +
				"join ur.user u join ur.role r where r.name = ? ";
		return find4List(hql, true, roleName);
	}
	
	/**
	 * 通过用户标识和角色标志查询UsersRoles对象
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	private UsersRoles findUsersRoles(Serializable userId,Serializable roleId) throws HibernateException, SQLException{
		String hql = "select ur from UsersRoles ur where " +
				"ur.role.id = ? and ur.user.id = ?";
		return find4Unique(hql, true, roleId,userId);
	}

	@Override
	public EasyUIPagination<List<Map>> queryPageUsers() throws HibernateException,
			SQLException {
		EasyUIPagination<List<Map>> page = null;
		
		String hql = "select " +
				" new map(u.id as id,u.username as username,u.person.sex as sex,u.person.duty as duty,u.person.org.id as org_id,u.person.org.name as org_name,u.createTime as createTime, u.expireTime as expireTime) " +
				" from User u " +
				" where 1=1";
		Pagination<List<Map<String, Object>>> users = find4ListPage(hql,true);
		page = new EasyUIPagination(users);
		return page;
	}

}
