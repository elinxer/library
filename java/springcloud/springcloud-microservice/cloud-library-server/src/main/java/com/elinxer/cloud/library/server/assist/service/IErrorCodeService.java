package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.elinxer.cloud.library.server.assist.domain.entity.ErrorCode;

public interface IErrorCodeService extends BaseService<ErrorCode> {


    ErrorCode detail(Long id);

    public ErrorCode detailByCode(String errCode);

}
