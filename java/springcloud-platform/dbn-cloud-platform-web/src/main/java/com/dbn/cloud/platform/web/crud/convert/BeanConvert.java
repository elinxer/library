package com.dbn.cloud.platform.web.crud.convert;

import java.util.List;

/**
 * 负责 Dto  <--> Entity 转化
 *
 * @param <D> 数据传输对象
 * @param <T> 实体对象
 * @author elinx
 * @date 2021-09-02
 */
public interface BeanConvert<D, T> {

    public D toDto(T entity);

    public T toEntity(D dto);

    public List<D> toDtoList(List<T> entity);

    public List<T> toEntityList(List<D> dto);

}
