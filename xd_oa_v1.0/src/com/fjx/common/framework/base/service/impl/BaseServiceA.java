package com.fjx.common.framework.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjx.common.framework.base.dao.impl.BaseDao;
import com.fjx.common.framework.base.service.IBaseServiceA;


@Service
@Transactional
public class BaseServiceA<T> extends BaseDao<T> implements IBaseServiceA<T> {
}
