package com.dbn.cloud.platform.web.crud.dict;

import java.util.List;

/**
 * 通用枚举的接口
 *
 * @author elinx
 * @since 1.0
 */
public interface ItemDefine extends EnumDict<String> {
    String getText();

    String getValue();

    String getComments();

    int getOrdinal();

    @Override
    default int ordinal() {
        return getOrdinal();
    }

    List<ItemDefine> getChildren();

}
