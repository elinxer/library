package com.dbn.cloud.platform.web.crud.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.web.crud.common.Condition;
import com.dbn.cloud.platform.web.crud.common.CrudConst;
import com.dbn.cloud.platform.web.crud.convert.BeanConvert;
import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.dbn.cloud.platform.web.crud.service.IServiceDto;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.QueryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 通用CRUD基类 （可以同时处理DTO/Entity两种类型的业务方法)
 *
 * @author elinx
 * @since 1.0
 * 1.数据校验功能
 * 2.DTO与DMO转化逻辑，通用DTO处理逻辑
 * 3.重复性判断
 */
public class BaseService<M extends BaseDao<T>, D, T> extends ServiceImpl<M, T> implements IServiceDto<D> {

    protected Validator validator;

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), 2);
    }

    @Autowired(required = false)
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    protected Class<D> currentDtoClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    public BeanConvert<D, T> getBeanConvert() {
        return null;
    }

    protected IPage<T> getPage(Map<String, Object> params) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(CrudConst.PAGE) != null) {
            curPage = Long.parseLong(params.get(CrudConst.PAGE).toString());
        }
        if (params.get(CrudConst.LIMIT) != null) {
            limit = Long.parseLong(params.get(CrudConst.LIMIT).toString());
        }
        //分页对象
        Page<T> page = new Page<>(curPage, limit);
        return page;
    }

    public T DtoToEntity(D dto) {
        return BeanConvertUtils.sourceToTarget(dto, currentModelClass());
    }

    public D EntityToDto(T entity) {
        return BeanConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    public boolean updateDto(D dto) {

        T entity = BeanConvertUtils.sourceToTarget(dto, currentModelClass());
        return updateById(entity);
    }

    @Override
    public boolean updateByIdForDto(Serializable id, D dto) {
        T entity = BeanConvertUtils.sourceToTarget(dto, currentModelClass());
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return this.update(entity, queryWrapper);
    }

    public int saveDto(D dto) {
        T entity = BeanConvertUtils.sourceToTarget(dto, currentModelClass());

        int result = getBaseMapper().insert(entity);
        BeanUtils.copyProperties(entity, dto);
        return result;
    }

    public boolean saveOrUpdateDto(D dto) {
        T entity = DtoToEntity(dto);
        return this.saveOrUpdate(entity);
    }

    public D get(Serializable id) {
        T entity = getBaseMapper().selectById(id);
        return BeanConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    public boolean deleteById(Serializable id) {
        return super.removeById(id);
    }

    public boolean deleteBatch(Serializable[] ids) {
        List idList = Arrays.asList(ids);
        return super.removeByIds(idList);
    }

    /**
     * 需要定制化查询条件的接口，必须重载这个方法
     *
     * @param params 条件map
     * @return 组装条件
     */
    public QueryWrapper<T> getWrapper(Map<String, Object> params) {
        return null;
    }

    ;

    public List<D> list(Map<String, Object> params) {
        List<T> entityList = getBaseMapper().selectList(getWrapper(params));
        return BeanConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    public PagerResult<D> listPage(Map<String, Object> params) {
        IPage<T> page = getBaseMapper().selectPage(
                getPage(params),
                getWrapper(params)
        );
        List<D> resultList = BeanConvertUtils.sourceToTarget(page.getRecords(), currentDtoClass());

        QueryParam queryParam = new QueryParam(params);
        queryParam.setPage((int) page.getCurrent());
        queryParam.setLimit((int) page.getSize());
        return PagerResult.of((int) page.getTotal(), resultList, queryParam);

    }


    public <E> List<E> listByCondition(Map<String, Object> condition) {
        QueryWrapper qw = Condition.getQueryWrapper(condition, this.getEntityClass());
        List<E> entityList = getBaseMapper().selectList(qw);
        return entityList;
    }

    public <E> IPage<E> listPageByCondition(Map<String, Object> condition) {
        QueryWrapper qw = Condition.getQueryWrapper(condition, this.getEntityClass());
        List<E> entityList = getBaseMapper().selectList(qw);
        IPage<E> page = getBaseMapper().selectPage(
                getPage(condition),
                qw
        );
        return page;
    }


}
