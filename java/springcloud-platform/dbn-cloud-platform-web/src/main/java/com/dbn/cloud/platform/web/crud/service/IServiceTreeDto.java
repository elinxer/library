package com.dbn.cloud.platform.web.crud.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 通用树表CRUD基类
 *
 * @author elinx
 * @since 1.0
 */
public interface IServiceTreeDto<T> {
    /**
     * 查询所有父节点
     *
     * @param childId 子节点id
     * @return 父节点集合
     */
    List<T> selectParentNode(Serializable childId);

    /**
     * 根据父节点id获取子节点数据
     *
     * @param parentId 父节点ID
     * @return 子节点数据
     */
    List<T> selectChildNode(Serializable parentId);

    /**
     * 根据父节点id,获取所有子节点的数据,包含字节点的字节点
     *
     * @param parentId 父节点ID
     * @return 所有子节点的数据
     */
    List<T> selectAllChildNode(Serializable parentId);

    /**
     * 批量修改数据,如果集合中的数据不存在,则将会进行新增
     *
     * @param data 数据集合
     * @return 修改的数量
     */
    int updateBatch(Collection<T> data);

    /**
     * 批量添加数据
     *
     * @param data 数据集合
     * @return 被添加数据集合的主键
     */
    List<Serializable> insertBatch(Collection<T> data);

    /**
     * 添加数据
     *
     * @param entity 数据实体
     * @return 被添加数据的主键
     */
    Serializable insert(T entity);

    /**
     * 更新数据
     *
     * @param entity 数据实体
     * @return 影响笔数
     */
    public int updateByPk(T entity);

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 被删除实体
     */
    public T deleteByPk(Serializable id);
}
