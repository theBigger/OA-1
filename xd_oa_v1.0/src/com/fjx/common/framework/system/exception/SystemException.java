package com.fjx.common.framework.system.exception;

/**
 * @author Administrator
 *
 */
public class SystemException extends RuntimeException {
	
	
	//´íÎó´úÂë
	private String key;
	
	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}
	
	public SystemException(String code,String message){
		super(message);
		
		this.key = code;
	}
	
	public SystemException(String code,String message, Throwable cause) {
		super(message, cause);
		this.key = code;
	}

	public String getKey() {
		return key;
	}
	
}
