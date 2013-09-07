package com.fjx.oa.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户角色中间表
 * @author feng
 *
 */
@Entity
@Table(name="oa_users_roles")
public class UsersRoles {
	
	/**
	 * @hibernate.id generator-class="native"
	 */
	private Long id;
	
	/**
	 * @hibernate.many-to-one
	 */
	private Role role;
	
	/**
	 * @hibernate.many-to-one
	 */
	private User user;
	
	/**
	 * @hibernate.property
	 */
	private int orderNo;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
}
