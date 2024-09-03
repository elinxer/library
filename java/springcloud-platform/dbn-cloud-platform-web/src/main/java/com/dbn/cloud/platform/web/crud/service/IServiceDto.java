package com.dbn.cloud.platform.web.crud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 根据上层传递的dto对象进行业务处理基本类
 *
 * @author elinx
 * @date 2021-08-28
 * <p>
 * 1.dto<->Entity
 * 2.dto 校验，重复性等
 * 3.CRUD
 */
public interface IServiceDto<D> {
    /**
     * 更新操作
     */
    public boolean updateDto(D dto);

    /**
     * 按Id条件更新
     */
    public boolean updateByIdForDto(Serializable id, D dto);

    /**
     * 保存操作
     */
    public int saveDto(D dto);

    /**
     * 保存或更新
     */
    public boolean saveOrUpdateDto(D dto);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return dto dto对象
     */
    public D get(Serializable id);

    /**
     * 根据条件全部查询
     */
    public List<D> list(Map<String, Object> params);

    /**
     * 根据条件分页查询
     */
    public PagerResult<D> listPage(Map<String, Object> params);

    /**
     * 根据id删除
     */
    public boolean deleteById(Serializable id);

    /**
     * 根据ids列表删除
     */
    public boolean deleteBatch(Serializable[] ids);

    public <E> List<E> listByCondition(Map<String, Object> condition);

    public <E> IPage<E> listPageByCondition(Map<String, Object> condition);

}
