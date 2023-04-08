package com.elinxer.cloud.library.server.assist.service;


import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.entity.OperationLog;
import com.elinxer.cloud.library.server.assist.domain.vo.OperationLogVo;

import java.util.List;
import java.util.Map;

public interface IOperationLogService extends BaseService<OperationLog> {

    OperationLogVo detail(Long id);

    PagerResult<OperationLogVo> page(Map<String, Object> params);

    List<OperationLogVo> list();

}
