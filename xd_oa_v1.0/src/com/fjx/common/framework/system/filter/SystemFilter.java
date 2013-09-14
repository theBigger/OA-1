package com.fjx.common.framework.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fjx.common.framework.system.pagination.PaginationContext;

public class SystemFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(SystemFilter.class);
	
	public static final String PAGE_SIZE_NAME = "pSize";
	
	private String reg = "";
	private String page_url = "";
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain china) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		logger.debug("访问地址："+uri);
		
		//如果请求的url与".*oa|.*\\.jsp"	禁止直接访问oa文件夹下的jsp文件
		//val = ".*oa|.*\\.jsp";
		if (uri.matches(reg)) {
			if(!uri.equals("/index.jsp")){
				logger.warn("禁止访问oa下的jsp文件");
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
		}else if(uri.matches(page_url)){		//分页请求
			logger.debug("接受到的分页参数 -- page:" + request.getParameter("page")+";  rows: " + request.getParameter("rows"));
			
			PaginationContext.setOffset(getOffset(request));
			PaginationContext.setPagesize(getPageSize(request));
			try{
				china.doFilter(req, resp);
			}
			finally{
				PaginationContext.removeAll();
			}
			return;
		}
		china.doFilter(req, resp);
		
	}

	private int getOffset(HttpServletRequest request) {
		int offset = 0;
		int pageNow = 0;
		
		String pageNowStr = request.getParameter("page");
		if (pageNowStr != null && !pageNowStr.equals("")){
			pageNow = Integer.valueOf(pageNowStr);
			//offset = (pageNow - 1)*getPageSize(request);
			offset = pageNow - 1;
			
		}
		logger.debug("offset： "+offset);
		return offset;
	}

	private int getPageSize(HttpServletRequest request) {
		int pagesize = 10;
		//首先判断request中是否有pagesize参数，如果有这个参数，证明客户端正在请求改变每页显示的行数
		String psvalue = request.getParameter("rows");
		if(psvalue != null && !psvalue.trim().equals("")){
			Integer ps = 0;
			try {
				ps = Integer.parseInt(psvalue);
			} catch (Exception e) {
			}
			if(ps != 0){
				request.getSession().setAttribute(PAGE_SIZE_NAME, ps);
			}
		}
		
		//判断当前session中是否有pagesize的值ֵ
		Integer ps = (Integer)request.getSession().getAttribute(PAGE_SIZE_NAME);
		if(ps == null){
			request.getSession().setAttribute(PAGE_SIZE_NAME, pagesize);
		} else {
			pagesize = ps;
		}
		logger.debug("pagesize： "+pagesize);
		return pagesize;
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		reg = config.getInitParameter("reg");
		page_url = config.getInitParameter("page_url");
		
	}
	
}
