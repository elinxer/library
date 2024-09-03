package com.dbn.cloud.platform.web.crud.convert;

import java.util.List;

/**
 * 负责 Vo  <--> Dto 转化
 *
 * @param <V> 视图对象
 * @param <D> 数据传输对象
 * @author elinx
 * @date 2021-09-02
 */
public interface BeanMapper<V, D> {

    public D toDto(V vo);

    public V toVo(D dto);

    public List<D> toDtoList(List<V> voList);

    public List<V> toVoList(List<D> voList);
}
