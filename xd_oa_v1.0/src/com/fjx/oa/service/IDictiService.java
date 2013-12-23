package com.fjx.oa.service;

import java.util.List;
import java.util.Map;

import com.fjx.common.framework.base.service.IBaseAbstractService;
import com.fjx.oa.models.Dict;


/**
 * 数据字典
 * @author fengjx-win7
 *
 */
public interface IDictiService extends IBaseAbstractService<Dict> {
	
	public Map<String,List<Dict>> loadDict() throws Exception;
	
	public List<Dict> getDictByFlag(String dict_flag) throws Exception;
	
}
