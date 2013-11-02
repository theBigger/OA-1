package com.fjx.oa.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 权限认证
 * @author fengjx
 *
 */
@Entity
@Table(name="oa_acl")
public class ACL {
	
	/*
	 * 主体类型为角色
	 */
	public static String TYPE_ROLE = "role";
	/*
	 * 主体类型为用户
	 */
	public static String TYPE_USER = "user";
	/*
	 * ACL的状态为继承（即无效，判断的时候应该判断其所属角色的授权）
	 */
	public static final int ACL_TRI_STATE_EXTENDS = 0xFFFFFFFF;
	/*
	 * ACL的状态为不继承（即有效，判断的时候，直接根据aclState判断授权）
	 */
	public static final int ACL_TRI_STATE_UNEXTENDS = 0;
	/*
	 * 授权允许
	 */
	public static final int ACL_YES = 1;
	/*
	 * 授权不允许
	 */
	public static final int ACL_NO = 0;
	/*
	 * 授权不确定
	 */
	public static final int ACL_NEUTRAL = -1;
	
	
	private Long id;
	//主体类型
	private String principalType;
	
	//主体标识
	private Long principalId;
	//资源标识
	private Long moduleId;
	//ACL的状态
	private int aclState;
	//ACL的继承状态
	private int aclTriState;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrincipalType() {
		return principalType;
	}

	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}

	public Long getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public int getAclState() {
		return aclState;
	}

	public void setAclState(int aclState) {
		this.aclState = aclState;
	}

	public int getAclTriState() {
		return aclTriState;
	}

	public void setAclTriState(int aclTriState) {
		this.aclTriState = aclTriState;
	}
	
	/**
	 * 设置权限
	 * 与运算符用符号“&”表示，其使用规律如下：两个操作数中位都为1，结果才为1，否则结果为0
	 * 或运算符用符号“|”表示，其运算规律如下：两个位只要有一个为1，那么结果就是1，否则就为0
	 * 位移运算：将数字从左边第一个不为0的数字起到最后一位全部移动
	 * @param permission
	 * @param yes
	 */
	public void setPermission(int permission, boolean yes){
		int temp = 1;
		temp = temp << permission;
		if(yes){
			aclState |= temp;
		}else{
			aclState &= ~temp;
		}
		
	}
	/**
	 * 取得权限值
	 * @param permission
	 * @return ACL_NO：不允许； ACL_YES：允许
	 */
	public int getPermission(int permission){
		if(aclTriState == ACL_TRI_STATE_EXTENDS){
			return ACL_NEUTRAL;
		}
		int temp = 1;
		temp = temp << permission;
		temp &= aclState;
		if(temp != 0){
			return ACL_YES;
		}
		return ACL_NO;
	}
	
	/**
	 * 设置ACL的继承状态
	 * @param yes true标识继承，false表示不继承
	 */
	public void setExtends(boolean yes){
		if(yes){
			aclTriState = ACL_TRI_STATE_EXTENDS;
		}else{
			aclTriState = ACL_TRI_STATE_UNEXTENDS;
		}
	}
	
	
	
	
}
