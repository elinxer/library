package com.elinxer.cloud.library.server.assist.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.QueryParam;
import com.elinxer.cloud.library.server.assist.domain.dao.AblineDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.vo.AbLineVo;
import com.elinxer.cloud.library.server.assist.service.IAblineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AblineServiceImpl extends BaseServiceImpl<AblineDao, Abline> implements IAblineService {

    @Autowired
    private AblineDao dao;

    @Override
    public AbLineVo detail(Long id) {
        return dao.detail(id);
    }

    @Override
    public PagerResult<AbLineVo> page(Map<String, Object> params) {
        int total = dao.count();

        IPage<Abline> page = this.buildPage(params);

        long start = page.getCurrent();
        long size = page.getSize();
        start = start * size - size;

        List<AbLineVo> res = dao.page(start, size);
        QueryParam query = new QueryParam(params);
        query.setPage((int) start);
        query.setLimit((int) size);
        return PagerResult.of(total, res, query);
    }

    @Override
    public Long insertLine(AbLineVo req) {
        Abline abline = JSON.parseObject(JSON.toJSONString(req), Abline.class);
        this.save(abline);
        return abline.getId();
    }
}
