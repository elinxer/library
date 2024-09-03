package com.dbn.cloud.platform.web.crud.web.result;

import lombok.Data;
import org.codehaus.jackson.map.Serializers;

/**
 * 带有分页
 *
 * @author elinx
 * @date 2021-09-02
 */
@Data
public class BasePageVo implements BaseVo {
    /**
     * 当前页码
     */
    public int page = Integer.getInteger("page.index", 0);
    /**
     * 当前页大小
     */
    public int limit = Integer.getInteger("page.size", 10);


}
