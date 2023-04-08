package com.elinxer.cloud.library.server.assist.domain.dao;

import cn.hutool.db.PageResult;

import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.assist.domain.entity.OperationLog;
import com.elinxer.cloud.library.server.assist.domain.vo.OperationLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogDao extends BaseDao<OperationLog> {

    OperationLogVo detail(Long id);

    PageResult<OperationLogVo> page(
            @Param("start") long start,
            @Param("size") long size
    );

    int count();

    List<OperationLogVo> list();
}
