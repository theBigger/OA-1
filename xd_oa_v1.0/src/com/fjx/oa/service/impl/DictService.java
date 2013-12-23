package com.fjx.oa.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fjx.common.framework.base.service.impl.BaseAbstractService;
import com.fjx.oa.models.Dict;
import com.fjx.oa.service.IDictiService;


@Service
public class DictService extends BaseAbstractService<Dict> implements IDictiService {
	
	
	//将数据字典缓存
	private Map<String, List<Dict>> dicts = new HashMap<String, List<Dict>>();
	
	@Override
	public Map<String, List<Dict>> loadDict() throws Exception {
		String dict_flag_hql = "select distinct d.dict_flag from Dict d";
		List<String> dict_flags = findListByHql(dict_flag_hql);
		Map<String, List<Dict>> dicts = null;
		if(null != dict_flags && dict_flags.size()>0){
			dicts = new HashMap<String, List<Dict>>();
			for(String flag : dict_flags){
				String hql = "from Dict d where d.dict_flag = ?";
				List<Dict> dictInfo = findListByHql(hql, flag);
				dicts.put(flag, dictInfo);
			}
		}
		return dicts;
	}

	@Override
	public List<Dict> getDictByFlag(String dict_flag) throws Exception {
		List<Dict> dictInfo = dicts.get("dict_flag");
		if(null != dictInfo){	//如果缓存存在，则直接返回
			return dictInfo;
		}
		String hql = "from Dict d where d.dict_flag = ?";
		dictInfo = findListByHql(hql, dict_flag);
		dicts.put(dict_flag, dictInfo);
		return dictInfo;
	}


}
