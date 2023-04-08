package com.elinxer.cloud.library.server.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.domain.dto.CategoryDto;
import com.elinxer.cloud.library.server.domain.entity.Category;

public interface ICategoryService extends BaseService<Category> {

    PagerResult<CategoryDto> listPage(String type, int currentPage, int pageSize);

}
