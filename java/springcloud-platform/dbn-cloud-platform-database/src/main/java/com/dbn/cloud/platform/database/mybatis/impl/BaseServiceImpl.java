package com.dbn.cloud.platform.database.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl implements BaseService<T> {


    @Override
    public Boolean saveForEntity(T entity, Class entityClass) {
        return this.save(BeanConvertUtils.sourceToTarget(entity, entityClass));
    }

    protected IPage<T> buildPage(Long page, Long size, Long total) {
        Page<T> iPage = new Page<>(page, size);
        iPage.setTotal(total);
        return iPage;
    }

    protected IPage<T> buildPage(Long page, Long size) {
        return new Page<>(page, size);
    }

    protected IPage<T> buildPage(Integer page, Integer size) {
        return new Page<>(page, size);
    }

    protected IPage<T> buildPage(Map<String, Object> params) {
        long curPage = 1L;
        long limit = 10L;
        long total = 0L;
        if (params.get("page") != null) {
            curPage = Long.parseLong(params.get("page").toString());
        }

        if (params.get("limit") != null) {
            limit = Long.parseLong(params.get("limit").toString());
        }

        Page<T> page = new Page<>(curPage, limit);

        if (params.get("total") != null) {
            total = Long.parseLong(params.get("total").toString());
            page.setTotal(total);
        }

        return page;
    }

    protected IPage toTargetPage(Long page, Long size, Class tClass) {
        int total = this.count();
        Page<T> params = new Page<>(page, size);
        params.setTotal(total);
        IPage iPage = this.page(params);
        List<Object> list = new ArrayList<>();
        if (iPage.getTotal() <= 0) {
            return new Page(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        }
        iPage.getRecords().forEach(item -> {
            list.add(JSON.parseObject(JSON.toJSONString(item), tClass));
        });
        iPage.setRecords(list);
        Page iPageNew = new Page(iPage.getCurrent(), iPage.getSize(), iPage.getTotal());
        iPageNew.setRecords(list);
        return iPageNew;
    }


}
