package com.dbn.cloud.platform.web.crud.web.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * TreeNode
 *
 * @author elinx
 */
@Data
public class TreeNode<T> implements Serializable {
    /**
     * id主键
     */
    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 子节点
     */
    private List<T> children;

}
