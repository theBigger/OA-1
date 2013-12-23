package com.fjx.oa.init.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fjx.oa.models.Dict;


/**
 * 系统参数
 * @author fengjx-win7
 *
 */
public class SysInfo {
	
	private static Map<String, List<Dict>> dicts = new HashMap<String, List<Dict>>();
	
	
	
	
	
	
	public static Map<String, List<Dict>> getDicts() {
		return dicts;
	}
	public static void setDicts(Map<String, List<Dict>> dicts) {
		SysInfo.dicts = dicts;
	}
}
