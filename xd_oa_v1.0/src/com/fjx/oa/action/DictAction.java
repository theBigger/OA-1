package com.fjx.oa.action;

import com.fjx.common.framework.base.action.BaseAction;
import com.fjx.oa.service.IDictiService;

/**
 * 数据字典
 * 
 * @author fengjx-win7
 * 
 */
public class DictAction extends BaseAction {

	private IDictiService dictiService;

	private String dict_flag;

	public void getDictByFlag() {
		
	}

	public IDictiService getDictiService() {
		return dictiService;
	}

	public void setDictiService(IDictiService dictiService) {
		this.dictiService = dictiService;
	}

	public String getDict_flag() {
		return dict_flag;
	}

	public void setDict_flag(String dict_flag) {
		this.dict_flag = dict_flag;
	}

}
