package com.fjx.oa.security.init;

import java.util.HashMap;
import java.util.Map;

import com.fjx.common.utils.Dom4jHelper;
import com.fjx.oa.security.models.AuthenUrl;

public class PermissionConfig {

	public static final int CREATE = 0;
	public static final int READ = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;
	
	/**
	 * 需要权限控制的URL
	 */
	public  Map<String, AuthenUrl> security = new HashMap<String, AuthenUrl>();
	
	static{
		
		
		
	}
	
	public void initSecurity() throws Exception{
		Dom4jHelper.parse("", "UTF-8");
		
		
	}
}
