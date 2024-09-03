package com.dbn.cloud.platform.web.crud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbn.cloud.platform.core.id.IDGenerator;
import com.dbn.cloud.platform.core.utils.RandomUtils;
import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.dbn.cloud.platform.web.crud.entity.BaseTreeEntity;
import com.dbn.cloud.platform.web.crud.service.IServiceTreeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通用树表CRUD基类 （待完善)
 *
 * @author elinx
 * @since 1.0
 */
public class BaseTreeService<M extends BaseDao<T>, T extends BaseTreeEntity> extends ServiceImpl<M, T> implements IServiceTreeDto<T> {
    @Override
    @Transactional(readOnly = true)
    public List<T> selectParentNode(Serializable childId) {
        T old = this.getById(childId);
        if (null == old) {
            return new ArrayList<>();
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.likeRight("path", old.getPath());
        return this.list(qw);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> selectAllChildNode(Serializable parentId) {
        T old = this.getById(parentId);
        if (null == old) {
            return new ArrayList<>();
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.likeRight("path", old.getPath());
        return this.list(qw);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> selectChildNode(Serializable parentId) {
        // 根据 parentId = parentId
        return null;
    }

    /**
     * 更新path,level,parentId
     *
     * @param entity
     */
    protected void applyPath(T entity) {
        if (Objects.isNull(entity.getParentId())) {
            entity.setParentId(null);
            entity.setLevel(0);
            entity.setPath(RandomUtils.randomChar(4));
            return;
        }
        if (!StringUtils.isEmpty(entity.getPath())) {
            return;
        }
        BaseTreeEntity parent = super.getById(entity.getParentId());
        if (null == parent) {
            entity.setParentId(null);
            entity.setPath(RandomUtils.randomChar(4));
            entity.setLevel(0);
        } else {
            entity.setPath(parent.getPath() + "-" + RandomUtils.randomChar(4));
            entity.setLevel(entity.getPath().split("[-]").length);
        }
    }

    protected Long saveOrUpdateForSingle(T entity) {
        java.lang.Long id = entity.getId();
        if (id <= 0 || this.getById(id) == null) {
            if (id <= 0) {
                entity.setId(IDGenerator.SNOW_FLAKE.generate());
            }
            applyPath(entity);
            //old this.insert(entity);
        }
        this.saveOrUpdate(entity);
        //old  updateByPk(entity);
        return id;
    }

    @Override
    public Serializable insert(T entity) {
        if (Objects.isNull(entity.getId()) || (entity.getId() <= 0)) {
            entity.setId(IDGenerator.SNOW_FLAKE.generate());
        }

        applyPath(entity);
        List<T> childrenList = new ArrayList<>();
        BaseTreeEntity.expandTree2List(entity, childrenList, IDGenerator.SNOW_FLAKE);
        childrenList.forEach(this::saveOrUpdateForSingle);
        return entity.getId();
    }

    @Override
    public List<Serializable> insertBatch(Collection<T> data) {
        return data.stream()
                .map(this::insert)
                .collect(Collectors.toList());
    }

    @Override
    public int updateBatch(Collection<T> data) {
        return data.stream()
                .mapToInt(this::updateByPk)
                .sum();
    }

    @Override
    public int updateByPk(T entity) {
        List<T> childrenList = new ArrayList<>();
        BaseTreeEntity.expandTree2List(entity, childrenList, IDGenerator.SNOW_FLAKE);
        childrenList.forEach(this::saveOrUpdateForSingle);
        return childrenList.size() + 1;
    }

    @Override
    public T deleteByPk(Serializable id) {
        T old = this.getById(id);
        if (StringUtils.isEmpty(old.getPath())) {
            this.removeById(id);
        } else {
            QueryWrapper<T> qw = new QueryWrapper<>();
            qw.likeRight("path", old.getPath());
            this.remove(qw);
        }
        return old;
    }
}
