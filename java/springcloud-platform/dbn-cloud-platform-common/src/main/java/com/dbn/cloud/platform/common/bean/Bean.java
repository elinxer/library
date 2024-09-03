package com.dbn.cloud.platform.common.bean;

import java.io.Serializable;

/**
 * @author elinx
 */
public interface Bean extends Serializable {
    /**
     * 从指定的对象中复制属性到本对象
     *
     * @param from   要复制的对象
     * @param ignore 不复制的字段
     * @param <T>    对象类型
     * @return 原始对象
     */
    @SuppressWarnings("all")
    default <T extends Bean> T copyFrom(Object from, String... ignore) {
        return null;
    }

    /**
     * 将对象的属性复制到指定的对象中
     *
     * @param to     要复制到的对象
     * @param ignore 不复制的字段
     * @param <T>    对象类型
     * @return 复制后的对象
     */
    default <T> T copyTo(T to, String... ignore) {
        return null;
    }
}
