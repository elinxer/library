package com.elinxer.cloud.library.server.assist.domain.dao;

import cn.hutool.db.PageResult;
import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.vo.AbLineVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AblineDao extends BaseDao<Abline> {

    AbLineVo detail(Long id);

    PageResult<AbLineVo> page(
            @Param("start") long start,
            @Param("size") long size
    );

    int count();
}
