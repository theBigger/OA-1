package com.fjx.oa.security.modules;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 资源
 * 
 * @author fengjx
 * 
 */
@Entity
@Table(name = "oa_module",
	uniqueConstraints = {@UniqueConstraint(columnNames="sn")})
public class Module {

	private Long id;

	private String name;

	private String sn;

	private String icon;

	private String url;

	private String order_no;

	private Module parent;

	private Set<Module> children;
	
	
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
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	@ManyToOne
	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	@OrderBy("order_no")
	public Set<Module> getChildren() {
		return children;
	}

	public void setChildren(Set<Module> children) {
		this.children = children;
	}

}
