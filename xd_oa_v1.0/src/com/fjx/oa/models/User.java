package com.fjx.oa.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author feng
 *
 */
@Entity
@Table(name="oa_user")
public class User {
	
	/**
	 * @hibernate.id
	 * 		generator-class="native"
	 */
	private int id;
	
	/**
	 * @hibernate.property 	
	 * 		unique="true"
	 * 		not-null="true"
	 */
	private String username;
	
	/**
	 * @hibernate.property
	 * 		not-null="true"
	 */
	private String password;
	
	/**
	 * @hibernate.property
	 */
	private Date createTime;
	
	/**
	 * @hibernate.property
	 */
	private Date expireTime;
	
	/**
	 * @hibernate.many-to-one unique="true"
	 */
	private Person person;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@ManyToOne
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}
