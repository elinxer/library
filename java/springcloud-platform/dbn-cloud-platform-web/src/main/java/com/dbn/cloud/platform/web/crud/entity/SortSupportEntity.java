package com.dbn.cloud.platform.web.crud.entity;

/**
 * 排序实体
 *
 * @author elinx
 * @date 2021-12-27
 */
public interface SortSupportEntity extends Comparable<SortSupportEntity>, Entity {
    Long getSortIndex();

    void setSortIndex(Long sortIndex);

    @Override
    default int compareTo(SortSupportEntity support) {
        if (support == null) {
            return -1;
        }

        return Long.compare(getSortIndex() == null ? 0 : getSortIndex(),
                support.getSortIndex() == null ? 0 : support.getSortIndex());
    }
}
