package com.fjx.oa.vo;

import java.util.List;

import com.fjx.common.framework.system.pagination.Pagination;


/**
 * easy ui 分页实体
 * @author feng
 *
 */
public class EasyUIPagination<T> {
	
	private int total;
	private List<T> rows;
	
	public EasyUIPagination(Pagination<T> pagination){
		this.rows = pagination.getDatas();
		this.total = pagination.getTotal();
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}



	public List<T> getRows() {
		return rows;
	}
	
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
