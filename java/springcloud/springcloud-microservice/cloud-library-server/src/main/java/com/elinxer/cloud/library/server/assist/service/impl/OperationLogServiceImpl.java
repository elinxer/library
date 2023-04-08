package com.elinxer.cloud.library.server.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.QueryParam;
import com.elinxer.cloud.library.server.assist.domain.dao.OperationLogDao;
import com.elinxer.cloud.library.server.assist.domain.entity.OperationLog;
import com.elinxer.cloud.library.server.assist.domain.vo.OperationLogVo;
import com.elinxer.cloud.library.server.assist.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogDao, OperationLog> implements IOperationLogService {

    @Autowired
    private OperationLogDao dao;

    @Override
    public OperationLogVo detail(Long id) {
        return dao.detail(id);
    }

    @Override
    public PagerResult<OperationLogVo> page(Map<String, Object> params) {
        int total = dao.count();

        IPage<OperationLog> page = this.buildPage(params);

        long start = page.getCurrent();
        long size = page.getSize();
        start = start * size - size;

        List<OperationLogVo> res = dao.page(start, size);
        QueryParam query = new QueryParam(params);
        query.setPage((int) start);
        query.setLimit((int) size);
        return PagerResult.of(total, res, query);
    }

    @Override
    public List<OperationLogVo> list() {
        return null;
    }
}
