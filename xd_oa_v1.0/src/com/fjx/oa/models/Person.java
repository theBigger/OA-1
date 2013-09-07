package com.fjx.oa.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="oa_person")
public class Person {
	
	private Long id;
	
	/**
	 * @hibernate.property
	 */
	private String name;
	
	/**
	 * @hibernate.property
	 */
	private String sex;
	
	/**
	 * @hibernate.property
	 */
	private int age;
	
	/**
	 * @hibernate.property
	 */
	private String address;
	
	/**
	 * @hibernate.property
	 */
	private String duty;
	
	/**
	 * @hibernate.property
	 */
	private String phone;
	
	/**
	 * @hibernate.many-to-one
	 */
	private Organization org;
	
	/**
	 * @hibernate.one-to-one property-ref="person"
	 */
	private User user;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ManyToOne
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
	@OneToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
