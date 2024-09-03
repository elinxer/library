package com.dbn.cloud.platform.web.crud.entity;

import java.io.Serializable;

/**
 * 统一带有主键实体
 *
 * @author elinx
 * @date 2021-08-20
 */
public interface GenericEntity<PK extends Serializable> extends Entity {
    PK getId();

    void setId(PK id);
}
