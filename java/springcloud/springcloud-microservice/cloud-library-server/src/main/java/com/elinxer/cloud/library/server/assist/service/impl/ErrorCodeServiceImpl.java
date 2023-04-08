package com.elinxer.cloud.library.server.assist.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.assist.domain.dao.ErrorCodeDao;
import com.elinxer.cloud.library.server.assist.domain.entity.ErrorCode;
import com.elinxer.cloud.library.server.assist.service.IErrorCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ErrorCodeServiceImpl extends BaseServiceImpl<ErrorCodeDao, ErrorCode> implements IErrorCodeService {


    @Override
    public ErrorCode detail(Long id) {

        Object detail = this.getById(id);

        return BeanConvertUtils.sourceToTarget(detail, ErrorCode.class);
    }

    @Override
    public ErrorCode detailByCode(String errCode) {
        ErrorCode detail = (ErrorCode) this.getOne(new LambdaQueryWrapper<ErrorCode>().eq(ErrorCode::getErrCode, errCode).last("limit 1"));
        if (detail == null) {
            return null;
        }
        return BeanConvertUtils.sourceToTarget(detail, ErrorCode.class);
    }

}
