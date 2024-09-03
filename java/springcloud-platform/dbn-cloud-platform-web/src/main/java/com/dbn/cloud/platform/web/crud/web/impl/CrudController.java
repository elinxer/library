package com.dbn.cloud.platform.web.crud.web.impl;

import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.web.crud.convert.BeanMapper;
import com.dbn.cloud.platform.web.crud.service.IServiceDto;
import com.dbn.cloud.platform.web.crud.web.ICrudController;
import com.dbn.cloud.platform.web.crud.web.impl.BaseController;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.dbn.cloud.platform.validation.validator.group.CreateGroup;
import com.dbn.cloud.platform.validation.validator.group.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.dbn.cloud.platform.web.crud.web.result.ResponseMessage.ok;

/**
 * 通用CRUD控制基类
 *
 * @author elinx
 * @since 1.0
 */
public class CrudController<V, D> extends BaseController implements ICrudController<V, D> {

    /**
     * 子类需要装配Service类
     *
     * @return 子类Service
     */
    public IServiceDto<D> getService() {
        return null;
    }

    /**
     * 根据需要注册Bean转化器
     */
    public BeanMapper<V, D> getBeanMapper() {
        return null;
    }

    /**
     * Vo -->Dto
     */
    public D VoToDto(V v) {
        if (Objects.isNull(getBeanMapper())) {
            return BeanConvertUtils.sourceToTarget(v, currentDtoClass());
        } else {
            return getBeanMapper().toDto(v);
        }
    }

    /**
     * VoList -->DtoList
     */
    public List<D> VoListToDtoList(List<V> vList) {
        if (Objects.isNull(getBeanMapper())) {
            if (vList == null) return null;
            List<D> dList = new ArrayList<>();
            for (V v : vList) {
                dList.add(VoToDto(v));
            }
            return dList;
        } else {
            return getBeanMapper().toDtoList(vList);
        }
    }

    /**
     * Dto -->Vo
     */
    public V DtoToVo(D d) {
        if (Objects.isNull(getBeanMapper())) {
            return BeanConvertUtils.sourceToTarget(d, currentVoClass());
        } else {
            return getBeanMapper().toVo(d);
        }
    }

    /**
     * DtoList -->VoList
     */
    public List<V> DtoListToVoList(List<D> dList) {
        if (Objects.isNull(getBeanMapper())) {
            if (dList == null) return null;
            List<V> vList = new ArrayList<>();
            for (D d : dList) {
                vList.add(DtoToVo(d));
            }
            return vList;
        } else {
            return getBeanMapper().toVoList(dList);
        }
    }

    /**
     * 新增操作
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增")
    public ResponseMessage add(@RequestBody V data) {
        tryValidate(data, CreateGroup.class);
        D dto = VoToDto(data);
        getService().saveDto(dto);
        return ok();
    }

    /**
     * 修改操作
     */
    @PutMapping
    @ApiOperation(value = "修改数据")
    public ResponseMessage update(@RequestBody V data) {
        tryValidate(data, UpdateGroup.class);
        D dto = VoToDto(data);
        getService().updateDto(dto);
        return ok();
    }

    /**
     * 修改数据
     */
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "修改数据")
    public ResponseMessage updateByPrimaryKey(@PathVariable Long id, @RequestBody V data) {
        tryValidate(data, UpdateGroup.class);
        D dto = VoToDto(data);
        getService().updateByIdForDto(id, dto);
        return ResponseMessage.ok();
    }

    /**
     * 新增或者修改
     */
    @PatchMapping
    @ApiOperation(value = "新增或者修改")
    public ResponseMessage saveOrUpdate(@RequestBody V data) {
        D dto = VoToDto(data);
        getService().saveOrUpdateDto(dto);
        return ResponseMessage.ok();
    }

    /**
     * 根据id删除数据
     */
    @DeleteMapping(path = "/{id:.+}")
    @ApiOperation("删除数据")
    public ResponseMessage deleteByPrimaryKey(@PathVariable Serializable id) {
        getService().deleteById(id);
        return ResponseMessage.ok();
    }

    /**
     * 根据id批量删除数据
     */
    @DeleteMapping
    public ResponseMessage deleteBatch(@RequestBody Serializable[] ids) {
        getService().deleteBatch(ids);
        return ResponseMessage.ok();
    }

    /**
     * 根据动态条件分页查询
     */
    @GetMapping
    @ApiOperation(value = "根据动态条件查询", responseReference = "get")
    public ResponseMessage<PagerResult<V>> list(@RequestParam Map param) {
        PagerResult<D> pager = getService().listPage(param);
        List<V> voList = DtoListToVoList(pager.getData());
        PagerResult<V> pagerVo = new PagerResult<>();
        pagerVo.setTotal(pager.getTotal());
        pagerVo.setData(voList);
        pagerVo.setPageIndex(pager.getPageIndex());
        pagerVo.setPageSize(pager.getPageSize());
        return ok(pagerVo);
    }

    /**
     * 根据动态条件查询
     */
    @GetMapping("/no-paging")
    @ApiOperation(value = "不分页动态查询", responseReference = "get")
    public ResponseMessage<List<V>> listNoPaging(Map param) {
        return ok(DtoListToVoList(getService().list(param)));
    }

    /**
     * 根据主键查询
     */
    @GetMapping(path = "/{id:.+}")
    @ApiOperation("根据主键查询")
    public ResponseMessage<V> getByPrimaryKey(@PathVariable Serializable id) {
        D dto = getService().get(id);
        return ok(DtoToVo(dto));
    }

}
