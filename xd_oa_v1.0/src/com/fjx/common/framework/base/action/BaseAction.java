package com.fjx.common.framework.base.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.fjx.common.framework.system.exception.SystemException;
import com.fjx.oa.vo.EasyUIPagination;
import com.fjx.oa.vo.EasyuiTreeNode;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author fengjx
 *
 */
public abstract class BaseAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3494515292315178007L;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private ActionContext context = ActionContext.getContext();
	
	protected HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
	protected HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
	protected Map session = context.getSession();
	
	protected List<Map<String, Object>> jsonList;
	protected Map<String, Object> jsonMap;
	protected EasyUIPagination<Map<String, Object>> easyUIPagination;
	protected List<EasyuiTreeNode> easyuiTreeNodes;
	
	
	/**
	 * jsp视图跳转
	 * @return
	 */
	public String view(){
		logger.debug(this.getClass()+".view()");
		return "view";
	}
	
	/**
	 * 将对象序列化为json
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected String serialize(Object object) throws Exception{
		//如果是字符串，直接返回
		if(object instanceof String ){
			return (String)object;
		}
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
	
	protected void execute4State(MyExecuteCallback callback, String erroeResult) throws Exception{
		Map<String,String> res = new HashMap<String,String>();
		try {
			callback.execute();
			res.put("state", "success");
		} catch (Exception e) {
			res.put("state", "fail");
			if(null == erroeResult){
				erroeResult = "请求异常：";
			}
			logger.error(erroeResult, e);
			throw  new SystemException(erroeResult,e);
		}
		write(res);
	}
	
	protected void execute4ResultJson(MyExecuteCallback callback, String erroeResult) throws Exception{
		Object res = null;
		try {
			res = callback.execute();
		} catch (Exception e) {
			res = null;
			if(null == erroeResult){
				erroeResult = "请求异常：";
			}
			logger.error(erroeResult, e);
			throw  new SystemException(erroeResult,e);
		}
		write(res);
	}

	public List<Map<String, Object>> getJsonList() {
		return jsonList;
	}

	public void setJsonList(List<Map<String, Object>> jsonList) {
		this.jsonList = jsonList;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public EasyUIPagination<Map<String, Object>> getEasyUIPagination() {
		return easyUIPagination;
	}

	public void setEasyUIPagination(
			EasyUIPagination<Map<String, Object>> easyUIPagination) {
		this.easyUIPagination = easyUIPagination;
	}

	public List<EasyuiTreeNode> getEasyuiTreeNodes() {
		return easyuiTreeNodes;
	}

	public void setEasyuiTreeNodes(List<EasyuiTreeNode> easyuiTreeNodes) {
		this.easyuiTreeNodes = easyuiTreeNodes;
	}
	
}
