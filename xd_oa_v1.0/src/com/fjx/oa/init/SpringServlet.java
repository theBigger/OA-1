package com.fjx.oa.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SpringServlet extends HttpServlet {
	
	private static WebApplicationContext ctx;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		 ServletContext servletContext = this.getServletContext();     
		 ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);     
		
	}
	
	public static Object getBean(String beanName){
		return ctx.getBean(beanName);
	}
	
}
