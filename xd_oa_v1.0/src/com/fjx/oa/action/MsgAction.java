package com.fjx.oa.action;

import com.fjx.common.framework.base.action.BaseAction;





public class MsgAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String msg;
	
	
	public String result() throws Exception{
		logger.info("msg输出："+msg);
		return SUCCESS;
	}
	
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	
	
	
	
	
	
}
