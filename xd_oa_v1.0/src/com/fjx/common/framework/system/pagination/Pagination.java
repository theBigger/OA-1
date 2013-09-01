package com.fjx.common.framework.system.pagination;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * @author fengjianxin
 * @param <T>
 */
public class Pagination<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2427109554550773701L;
	
	private List<T> datas = new ArrayList<T>();
	
	private int total;			//中
	private int pageNo;			//当前页
	private int pageSize;		//每页显示数据条数
	
	
	
	public Pagination(List<T> _datas, int _total) {
		this.datas = _datas;
		this.total = _total;
	}


	public List<T> getDatas() {
		return datas;
	}


	public void setDatas(List<T> datas) {
		this.datas = datas;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
