package com.fjx.common.framework.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.dao.impl.BaseDao;
import com.fjx.common.framework.base.service.IBaseAbstractService;


@Service
@Transactional
public class BaseAbstractService<T> extends BaseDao<T> implements IBaseAbstractService<T> {
}
