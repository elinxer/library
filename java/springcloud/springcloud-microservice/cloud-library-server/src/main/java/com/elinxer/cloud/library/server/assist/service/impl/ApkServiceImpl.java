package com.elinxer.cloud.library.server.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.QueryParam;
import com.elinxer.cloud.library.server.assist.domain.dao.ApkDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Apk;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkUpgradeVo;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkVo;
import com.elinxer.cloud.library.server.assist.service.IApkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApkServiceImpl extends BaseServiceImpl<ApkDao, Apk> implements IApkService {

    @Autowired
    private ApkDao dao;

    @Override
    public ApkVo detail(Long id) {
        return dao.detail(id);
    }

    @Override
    public List<ApkVo> upgradeList(ApkUpgradeVo req) {

        req.setApkVersionStr("%#" + req.getApkVersion() + "#%");
        req.setSerialNo("%#" + req.getSerialNo() + "#%");

        return dao.selectUpgradeList(req);
    }

    @Override
    public PagerResult<ApkVo> page(Map<String, Object> params) {
        int total = dao.count();

        IPage<Apk> page = this.buildPage(params);

        long start = page.getCurrent();
        long size = page.getSize();
        start = start * size - size;

        List<ApkVo> res = dao.page(start, size);
        QueryParam query = new QueryParam(params);
        query.setPage((int) start);
        query.setLimit((int) size);
        return PagerResult.of(total, res, query);
    }
}
