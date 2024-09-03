package com.dbn.cloud.platform.web.crud.web.result;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author elinx
 * @date 2021-09-02
 */
@Getter
@Setter
public class QueryParam extends LinkedHashMap<String, Object> {

    private int page;

    private int limit;

    public QueryParam(Map<String, Object> params) {
        this.putAll(params);
    }
}
