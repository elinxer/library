package com.dbn.cloud.platform.web.crud.web.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.dbn.cloud.platform.common.utils.DateUtils;
import com.dbn.cloud.platform.web.crud.web.IBaseController;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.dbn.cloud.platform.validation.constant.ValidationErrorEnum;
import com.dbn.cloud.platform.validation.exception.ValidationException;
import com.dbn.cloud.platform.validation.validate.SimpleValidateResults;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 控制器基类
 * <p>
 * 1. 用户相关，校验，字典，参数，多语言等
 * 2. CRUD操作
 *
 * @author elinx
 * @date 2021-08-24
 */
public class BaseController {

    protected Validator validator;

    @Autowired(required = false)
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> T tryValidate(T bean, Class... group) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean, group);
        if (!violations.isEmpty()) {
            SimpleValidateResults results = new SimpleValidateResults();
            for (ConstraintViolation<T> violation : violations) {
                results.addResult(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ValidationException(ValidationErrorEnum.PLAT_VALID_0001, results);
        }
        return bean;
    }


    /**
     * Excel导出
     *
     * @param response  response
     * @param fileName  文件名
     * @param sheetName sheetName
     * @param list      数据List
     * @param pojoClass 对象Class
     */
    public static void exportExcel(HttpServletResponse response, String fileName, String sheetName, List<?> list,
                                   Class<?> pojoClass) throws IOException {
        if (StringUtils.isBlank(fileName)) {
            //当前日期
            fileName = DateUtils.format(new Date());
        }

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), pojoClass).sheet(sheetName).doWrite(list);
    }

    /**
     * https://blog.csdn.net/yssa1125001/article/details/107303026
     *
     * @param response
     * @param name
     * @param location
     * @param downLoadPath
     */
    public static void download(HttpServletResponse response, String name, String location, String downLoadPath) {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + "location" + ".xlsx");
    }


    /*
     * Excel导入
     *
     * @param simpleFile 上传文件
     * @param request    请求
     * @param response   响应
     * @return 是否导入成功
     * @throws Exception 异常
     */
    public ResponseMessage<Boolean> importExcel(@RequestParam(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        ImportParams params = new ImportParams();

        params.setTitleRows(StrUtil.isEmpty(request.getParameter("titleRows")) ? 0 : Convert.toInt(request.getParameter("titleRows")));
        params.setHeadRows(StrUtil.isEmpty(request.getParameter("headRows")) ? 1 : Convert.toInt(request.getParameter("headRows")));
        List<Map<String, String>> list = ExcelImportUtil.importExcel(simpleFile.getInputStream(), Map.class, params);

        if (list != null && !list.isEmpty()) {
            return handlerImport(list);
        }
        return ResponseMessage.error("导入Excel无有效数据！");
    }

    /**
     * Excel导入需要重载
     *
     * @param list 集合
     * @return 是否成功
     */
    public ResponseMessage<Boolean> handlerImport(List<Map<String, String>> list) {
        return ResponseMessage.ok();
    }

    /**
     * 子类增强ExportParams
     */
    public void enhanceExportParams(ExportParams ep) {

    }

    public ExportParams getExportParams(Map<String, Object> params) {
        Object title = params.get("title");
        Object type = params.getOrDefault("type", ExcelType.XSSF.name());
        Object sheetName = params.getOrDefault("sheetName", "SheetName");

        ExcelType excelType = ExcelType.XSSF.name().equals(type) ? ExcelType.XSSF : ExcelType.HSSF;
        ExportParams ep = new ExportParams(title == null ? null : String.valueOf(title), sheetName.toString(), excelType);
        enhanceExportParams(ep);
        return ep;
    }

    public List findExportList(Map<String, Object> params) {
        return null;
    }


    public Class<?> getExcelClass() {
        return null;
    }

    /**
     * 预览Excel
     *
     * @param params 预览参数
     * @return 预览html
     */
    public ResponseMessage<String> preview(@RequestBody @Validated Map<String, Object> params) {
        ExportParams exportParams = getExportParams(params);
        List<?> list = findExportList(params);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, getExcelClass(), list);
        return ResponseMessage.ok(ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(workbook)));
    }


}
