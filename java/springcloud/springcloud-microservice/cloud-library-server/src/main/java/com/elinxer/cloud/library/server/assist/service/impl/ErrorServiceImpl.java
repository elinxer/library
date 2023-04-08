package com.elinxer.cloud.library.server.assist.service.impl;

import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.assist.domain.dao.ErrorDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Error;
import com.elinxer.cloud.library.server.assist.service.IErrorService;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceImpl extends BaseServiceImpl<ErrorDao, Error> implements IErrorService {


    @Override
    public void saveError(Error error) {
        this.save(error);
    }

}
