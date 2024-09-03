package com.dbn.cloud.platform.web.crud.entity;

import java.util.Date;

/**
 * 带有createTime，CreateUserId字段的接口
 *
 * @author elinx
 * @date 2021-08-20
 */
public interface RecordCreationEntity extends Entity {

    public Date getCreateTime();

    public void setCreateTime(Date createTime);

//    public Long getCreateUserId();
//
//    public void setCreateUserId(Long createUserId);

    default void setCreateTimeNow() {
        setCreateTime(new Date());
    }
}
