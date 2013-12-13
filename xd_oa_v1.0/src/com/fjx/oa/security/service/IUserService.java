package com.fjx.oa.security.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.security.models.User;
import com.fjx.oa.security.models.UsersRoles;
import com.fjx.oa.vo.EasyUIPagination;


public interface IUserService extends IBaseAbstractService<User> {
	
	public void addUser(User user,Serializable personId)throws Exception;
	public void updateUser(User user,Serializable personId)throws Exception;

	/**
	 * 在用户与角色之间建立关联
	 * @param userId
	 * @param roleId
	 * @param orderNo
	 */
	public void addOrUpdateUserRole(Serializable userId, Serializable roleId, int orderNo)throws Exception;
	
	/**
	 * 删除用户与角色之间的关联
	 * @param userId
	 * @param roleId
	 */
	public void delUserRole(Serializable userId,Serializable roleId)throws Exception;
	
	/**
	 * 搜索某个用户所拥有的角色列表
	 * @param userId
	 * @return 元素是UsersRoles对象
	 */
	public List<UsersRoles> searchUserRoles(Long userId)throws Exception;
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username,String password)throws Exception;
	
	/**
	 * 搜索某个角色下的用户名列表
	 * @param roleName 角色名
	 * @return 用户名(username)列表
	 */
	public List<User> searchUsersByRole(String roleName)throws Exception;
	
	/**
	 * 分页查询
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public EasyUIPagination<List<Map>> queryPageUsers()throws Exception; 
	
}
