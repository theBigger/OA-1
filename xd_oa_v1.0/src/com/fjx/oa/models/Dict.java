package com.fjx.oa.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 数据字典
 * @author fengjx
 *
 */
@Entity
@Table(name="oa_dict")
public class Dict {
	
	private Long id;
	
	private String dict_key;		//键
	
	private String dict_value;		//值
	
	private String dict_flag;		//标记
	
	private String description;		//描述
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDict_key() {
		return dict_key;
	}

	public void setDict_key(String dict_key) {
		this.dict_key = dict_key;
	}

	public String getDict_value() {
		return dict_value;
	}

	public void setDict_value(String dict_value) {
		this.dict_value = dict_value;
	}

	public String getDict_flag() {
		return dict_flag;
	}

	public void setDict_flag(String dict_flag) {
		this.dict_flag = dict_flag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	}
