package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.assist.domain.entity.Apk;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkUpgradeVo;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkVo;

import java.util.List;
import java.util.Map;

public interface IApkService extends BaseService<Apk> {

    ApkVo detail(Long id);

    List<ApkVo> upgradeList(ApkUpgradeVo req);

    PagerResult<ApkVo> page(Map<String, Object> params);

}
