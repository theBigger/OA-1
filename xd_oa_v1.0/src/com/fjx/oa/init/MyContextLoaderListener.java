package com.fjx.oa.init;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;


/**
 * 重写spring监听器
 * @author fengjx-win7
 *
 */
public class MyContextLoaderListener extends ContextLoaderListener {
	
	
    /** 
     * @description 重写ContextLoaderListener的contextInitialized方法 
     */  
	@Override  
    public void contextInitialized(ServletContextEvent event) {  
        //执行spring自己的监听
		super.contextInitialized(event);
        //加入自己的逻辑
    } 
	
}
