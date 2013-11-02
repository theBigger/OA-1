package com.fjx.common.framework.base.action;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.fjx.common.framework.system.exception.SystemException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3494515292315178007L;

	protected Logger logger = Logger.getLogger(this.getClass());
	
	private ActionContext context = ActionContext.getContext();
	
	protected HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
	protected HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
	protected Map session = context.getSession();
	
	/**
	 * 将对象序列化为json
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected String serialize(Object object) throws Exception{
		String res = null; 
		try {
			res = JSONUtil.serialize(object);
		} catch (JSONException e) {
			logger.error("序列化对象异常",e);
			throw new SystemException("序列化对象异常",e);
		}
		return res;
	}
	
	/**
	 * 输出json数据
	 * @param res
	 * @throws Exception
	 */
	protected void write(Object res) throws Exception{
		Writer writer = null;
		try {
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();
			String serializeStr = serialize(res);
			logger.debug("输出："+serializeStr);
			writer.write(serializeStr);
		} catch (IOException e) {
			logger.error("输出JSON字符串异常",e);
			throw e;
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("关闭输出流异常,无法关闭会导致内存溢出",e);
					throw new SystemException("关闭输出流异常,无法关闭会导致内存溢出",e);
				} finally{
					writer = null;
				}
			}
		}
	}
}
