package com.fjx.oa.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author feng
 *
 */
@Entity
@Table(name="oa_org")
public class Organization {
	
	private Long id;
	
	private String name;
	
	private String sn;
	
	private String description;
	
	private Date in_time = new Date();

	private Organization parent;
	
	private Set<Organization> children;
	
	
//	private Long parent_id ;
//	private String parent_name ;
//	private String state = "open";
	
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
	//唯一约束
	@Column(unique=true)
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getIn_time() {
		return in_time;
	}
	public void setIn_time(Date in_time) {
		this.in_time = in_time;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	public Organization getParent() {
		return parent;
	}
	public void setParent(Organization parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	@OrderBy("id")
	public Set<Organization> getChildren() {
		return children;
	}
	public void setChildren(Set<Organization> children) {
		this.children = children;
	}
	
	//不需要映射数据库字段
//	@Transient
//	public Long getParent_id() {
//		return parent_id;
//	}
//	public void setParent_id(Long parent_id) {
//		this.parent_id = parent_id;
//	}
//	@Transient
//	public String getParent_name() {
//		return parent_name;
//	}
//	public void setParent_name(String parent_name) {
//		this.parent_name = parent_name;
//	}
//	@Transient
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
	
}
