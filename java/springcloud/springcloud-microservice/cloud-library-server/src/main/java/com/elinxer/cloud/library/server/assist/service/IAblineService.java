package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.vo.AbLineVo;

import java.util.Map;

public interface IAblineService extends BaseService<Abline> {

    AbLineVo detail(Long id);

    PagerResult<AbLineVo> page(Map<String, Object> params);

    Long insertLine(AbLineVo req);

}
