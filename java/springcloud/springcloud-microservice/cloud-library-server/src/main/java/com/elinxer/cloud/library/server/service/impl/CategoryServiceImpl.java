package com.elinxer.cloud.library.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.elinxer.cloud.library.server.domain.dto.CategoryDto;
import com.elinxer.cloud.library.server.domain.entity.Category;
import com.elinxer.cloud.library.server.domain.mapper.CategoryMapper;
import com.elinxer.cloud.library.server.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    ICategoryService iCategoryService;

    @Resource
    CategoryMapper categoryMapper;


    @Override
    public PagerResult<CategoryDto> listPage(String type, int currentPage, int pageSize) {
        IPage<CategoryDto> iPage = new Page<>(currentPage, pageSize);

        IPage result = this.page(iPage, new LambdaQueryWrapper<Category>()
                .eq(Category::getIsDeleted, 0)
                .orderByDesc(Category::getId));

        return PagerResult.of(iPage, result.getRecords());
    }
}
