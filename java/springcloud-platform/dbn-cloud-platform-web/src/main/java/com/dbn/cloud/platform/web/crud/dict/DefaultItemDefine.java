package com.dbn.cloud.platform.web.crud.dict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author elinx
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultItemDefine implements ItemDefine {
    private String text;
    private String value;
    private String comments;
    private int ordinal;
    private List<ItemDefine> children;
}
