package com.elinxer.cloud.library.server.assist.domain.dao;

import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Task;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TaskDao extends BaseDao<Task> {


}
