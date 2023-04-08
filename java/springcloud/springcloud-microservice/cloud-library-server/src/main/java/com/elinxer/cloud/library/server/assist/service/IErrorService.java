package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.elinxer.cloud.library.server.assist.domain.entity.Error;

public interface IErrorService extends BaseService<Error> {

    void saveError(Error error);

}
