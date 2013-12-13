package com.fjx.oa.security.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 需要权限控制的URL
 * @author fengjx
 *
 */
@Entity
@Table(name="oa_authen_url")
public class AuthenUrl {
	
	private Long id;
	
	private String module_sn;

	private int permission;

	private String url;
	
	//是否启用
	private int is_valid;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getModule_sn() {
		return module_sn;
	}

	public void setModule_sn(String module_sn) {
		this.module_sn = module_sn;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}
	
	
}
