package com.fjx.oa.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring工具类
 * @author fengjx
 * 
 */
public class SpringUtil {

	private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("spring-applicationContext.xml");

	public static ApplicationContext getApplicationContext() {
		return APPLICATION_CONTEXT;
	}

	public static Object getBean(String beanName) {
		return APPLICATION_CONTEXT.getBean(beanName);
	}

}
