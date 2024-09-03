package com.dbn.cloud.platform.web.crud.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 * 公用实体基类(id,createUserId,createTime,updateUserId,updateTime)
 *
 * @author elinx
 * @date 2021-08-20
 */
public class BaseEntity implements GenericEntity<Long>, RecordUpdaterEntity, RecordCreationEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

//    @TableField(value = "create_userId", fill = FieldFill.INSERT)
//    private Long createUserId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

//    @TableField(value = "update_userId", fill = FieldFill.INSERT_UPDATE)
//    private Long updateUserId;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    @Override
//    public Long getCreateUserId() {
//        return this.createUserId;
//    }
//
//    @Override
//    public void setCreateUserId(Long createUserId) {
//        this.createUserId = createUserId;
//    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

//    @Override
//    public Long getUpdateUserId() {
//        return updateUserId;
//    }
//
//    @Override
//    public void setUpdateUserId(Long updateUserId) {
//        this.updateUserId = updateUserId;
//    }

}
