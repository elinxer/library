package com.elinxer.cloud.library.server.assist.domain.dao;

import cn.hutool.db.PageResult;
import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Apk;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkUpgradeVo;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApkDao extends BaseDao<Apk> {

    ApkVo detail(Long id);

    PageResult<ApkVo> page(@Param("start") long start, @Param("size") long size);

    int count();

    List<ApkVo> selectUpgradeList(ApkUpgradeVo req);

}
