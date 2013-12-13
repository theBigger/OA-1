package com.fjx.oa.security.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.fjx.oa.security.annotation.SystemPermission;
import com.fjx.oa.security.service.IAclService;
import com.fjx.oa.utils.SpringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 权限认证拦截器
 * 
 * @author fengjx
 * 
 */
public class AuthenticationInterceptor implements Interceptor {

	private Logger logger;
	private IAclService aclService;

	@Override
	public void destroy() {
		logger.debug("安全认证拦截器销毁");

	}

	@Override
	public void init() {
		logger = Logger.getLogger(this.getClass());
		logger.debug("初始化安全认证拦截器");

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		
		logger.debug("进入安全认证拦截器");
		String module_sn = null;
		int permisson = -1;
		
		String methodName = invocation.getProxy().getMethod();
		
		Object action = invocation.getAction();
		Method method = action.getClass().getMethod(methodName);
		
		//如果方法上有权限控制注解
		if(null != method && method.isAnnotationPresent(SystemPermission.class)){
			SystemPermission systemPermission = method.getAnnotation(SystemPermission.class);
			module_sn = systemPermission.module_sn();
			permisson = systemPermission.permissionType();
			aclService = (IAclService) SpringUtil.getBean("aclService");
			boolean flag = aclService.hasPermissionByResourceSn(new Long(2), module_sn, permisson);
			if(!flag){//沒有權限
				Map msg = new HashMap();
				msg.put("msg", "没有操作权限"); 
				invocation.getInvocationContext().setParameters(msg);
				return "out";
			}
		}
		return invocation.invoke();
	}
}
