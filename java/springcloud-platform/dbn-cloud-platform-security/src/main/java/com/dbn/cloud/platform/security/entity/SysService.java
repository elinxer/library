package com.dbn.cloud.platform.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务API实体
 *
 * @author elinx
 * @date 2021-08-10
 */
@Data
public class SysService implements Serializable {

    private static final long serialVersionUID = 749360940290141180L;
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private Integer isService;

}