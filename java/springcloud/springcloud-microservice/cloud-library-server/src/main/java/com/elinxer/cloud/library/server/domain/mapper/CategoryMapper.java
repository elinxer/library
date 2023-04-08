package com.elinxer.cloud.library.server.domain.mapper;

import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper extends BaseDao<Category> {


}
