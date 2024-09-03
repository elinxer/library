package com.dbn.cloud.platform.web.crud.entity;

import java.util.Date;

/**
 * 带有UpdateTime，UpdateUserId字段的接口
 *
 * @author elinx
 * @date 2021-08-20
 */
public interface RecordUpdaterEntity extends Entity {

    public Date getUpdateTime();

    public void setUpdateTime(Date updateTime);

//    public Long getUpdateUserId();
//
//    public void setUpdateUserId(Long updateUserId);

    default void setUpdateTimeNow() {
        setUpdateTime(new Date());
    }

}
