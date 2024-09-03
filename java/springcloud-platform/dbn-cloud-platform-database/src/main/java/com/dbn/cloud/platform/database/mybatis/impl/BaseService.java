package com.dbn.cloud.platform.database.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService {

    Boolean saveForEntity(T entity, Class entityClass);

}
