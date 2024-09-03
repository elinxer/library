package com.dbn.cloud.platform.web.crud.common;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.dbn.cloud.platform.web.crud.convert.BeanConvert;
import com.dbn.cloud.platform.web.crud.convert.BeanMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 主要处理Web层的Vo<-->Dto互转
 *
 * @author elinx
 * @since 1.0
 */
public class BeanConvertUtils<V, D> {
    private Class<D> currentDtoClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    private Class<V> currentVoClass() {
        return (Class<V>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }


    private BeanMapper<V, D> beanMapper = null;

    public void setBeanMapper(BeanMapper<V, D> beanMapper) {
        this.beanMapper = beanMapper;
    }

    public BeanMapper<V, D> getBeanMapper() {
        return beanMapper;
    }

    /**
     * Vo -->Dto
     */
    public D VoToDto(V v) {
        if (Objects.isNull(getBeanMapper())) {
            return com.dbn.cloud.platform.core.utils.BeanConvertUtils.sourceToTarget(v, currentDtoClass());
        } else {
            return getBeanMapper().toDto(v);
        }
    }

    /**
     * VoList -->DtoList
     */
    public List<D> VoListToDtoList(List<V> vList) {
        if (Objects.isNull(getBeanMapper())) {
            if (vList == null) return null;
            List<D> dList = new ArrayList<>();
            for (V v : vList) {
                dList.add(VoToDto(v));
            }
            return dList;
        } else {
            return getBeanMapper().toDtoList(vList);
        }
    }

    /**
     * Dto -->Vo
     */
    public V DtoToVo(D d) {
        if (Objects.isNull(getBeanMapper())) {
            return com.dbn.cloud.platform.core.utils.BeanConvertUtils.sourceToTarget(d, currentVoClass());
        } else {
            return getBeanMapper().toVo(d);
        }
    }

    /**
     * DtoList -->VoList
     */
    public List<V> DtoListToVoList(List<D> dList) {
        if (Objects.isNull(getBeanMapper())) {
            if (dList == null) return null;
            List<V> vList = new ArrayList<>();
            for (D d : dList) {
                vList.add(DtoToVo(d));
            }
            return vList;
        } else {
            return getBeanMapper().toVoList(dList);
        }
    }

}
