

package com.dbn.cloud.platform.web.crud.web.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dbn.cloud.platform.web.crud.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 *
 * @author elinx
 */
@ApiModel(description = "分页结果")
@Getter
@Setter
public class PagerResult<E> implements Entity {

    private static final long serialVersionUID = -6171751136953308027L;

    public static <E> PagerResult<E> empty() {
        return new PagerResult<>(0, new ArrayList<>());
    }

    public static <E> PagerResult<E> of(int total, List<E> list) {
        return new PagerResult<>(total, list);
    }

    public static <E> PagerResult<E> of(int total, List<E> list, QueryParam entity) {
        PagerResult pagerResult = new PagerResult<>(total, list);
        pagerResult.setPageIndex(entity.getPage());
        pagerResult.setPageSize(entity.getLimit());
        return pagerResult;
    }

    public static <E> PagerResult<E> of(long total, long pageIndex, long pageSize, List<E> list) {
        PagerResult<E> result = PagerResult.of((int) total, list);
        result.setPageIndex((int) pageIndex);
        result.setPageSize((int) pageSize);
        return result;
    }

    public static <E> PagerResult<E> of(IPage<E> iPage) {
        PagerResult<E> result = PagerResult.of((int) iPage.getTotal(), iPage.getRecords());
        result.setPageIndex((int) iPage.getCurrent());
        result.setPageSize((int) iPage.getSize());
        return result;
    }

    public static <E> PagerResult<E> of(IPage<E> iPage, List<E> list) {
        PagerResult<E> result = PagerResult.of((int) iPage.getTotal(), list);
        result.setPageIndex((int) iPage.getCurrent());
        result.setPageSize((int) iPage.getSize());
        return result;
    }

    @ApiModelProperty("当前页码")
    private int pageIndex;

    @ApiModelProperty("每页数据数量")
    private int pageSize;

    @ApiModelProperty("数据总数量")
    private int total;

    @ApiModelProperty("查询结果")
    private List<E> data;

    public PagerResult() {
    }

    public PagerResult(int total, List<E> data) {
        this.total = total;
        this.data = data;
    }
}