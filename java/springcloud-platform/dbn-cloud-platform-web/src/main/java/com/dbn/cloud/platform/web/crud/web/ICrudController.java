package com.dbn.cloud.platform.web.crud.web;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.web.crud.convert.BeanMapper;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dbn.cloud.platform.web.crud.web.result.ResponseMessage.ok;

/**
 * 通用增删改查控制类
 *
 * @author elinx
 * @date 2021-08-30
 * <p>
 * 后续考虑拆分多个Controller接口，查询,更新分开，更加细粒度控制
 */
public interface ICrudController<V, D> extends IBaseController<V, D> {

    default Class<D> currentDtoClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    default Class<V> currentVoClass() {
        return (Class<V>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }


    default D VoToDto(V v) {
        return BeanConvertUtils.sourceToTarget(v, currentDtoClass());
    }

    default List<D> VoListToDtoList(List<V> vList) {
        if (vList == null) return null;
        List<D> dList = new ArrayList<>();
        for (V v : vList) {
            dList.add(VoToDto(v));
        }
        return dList;
    }

    default V DtoToVo(D d) {
        return BeanConvertUtils.sourceToTarget(d, currentVoClass());
    }

    default List<V> DtoListToVoList(List<D> dList) {
        if (dList == null) return null;

        List<V> vList = new ArrayList<>();
        for (D d : dList) {
            vList.add(DtoToVo(d));
        }
        return vList;
    }

    /**
     * 新增操作
     */
    ResponseMessage add(@RequestBody V data);

    /**
     * 修改操作
     */
    ResponseMessage update(@RequestBody V data);

    /**
     * 修改数据
     */
    ResponseMessage updateByPrimaryKey(@PathVariable Long id, @RequestBody V data);

    /**
     * 新增或者修改
     */
    ResponseMessage saveOrUpdate(@RequestBody V data);

    /**
     * 根据id删除数据
     */
    ResponseMessage deleteByPrimaryKey(@PathVariable Serializable id);

    /**
     * 根据id批量删除数据
     */
    ResponseMessage deleteBatch(@RequestBody Serializable[] ids);

    /**
     * 根据动态条件分页查询
     */
    ResponseMessage<PagerResult<V>> list(@RequestParam Map param);

    /**
     * 根据动态条件查询
     */
    ResponseMessage<List<V>> listNoPaging(Map param);


    ResponseMessage<V> getByPrimaryKey(@PathVariable Serializable id);

}
