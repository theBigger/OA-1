package com.fjx.oa.security.init;

import java.util.HashMap;
import java.util.Map;

public interface Permission {

//	public static final int CREATE = 0;
//	public static final int UPDATE = 2;
//	public static final int DELETE = 3;
	
	/**
	 * 权限类型
	 */
	public  Map<String, Integer> type = new HashMap<String, Integer>();
	
	
	public static int READ = type.get("READ");
	
}
