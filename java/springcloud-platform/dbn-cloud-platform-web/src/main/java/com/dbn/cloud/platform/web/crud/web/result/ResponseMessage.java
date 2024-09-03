package com.dbn.cloud.platform.web.crud.web.result;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 响应消息,controller中处理后，返回此对象，响应请求结果给客户端
 *
 * @author elinx
 * @since 1.0
 */
@ApiModel(description = "响应结果")
public class ResponseMessage<T> implements Serializable {
    private static final long serialVersionUID = 8992436576262574064L;

    protected String message;

    protected T result;

    protected int status;

    private Long timestamp;


    private String code;

    @ApiModelProperty("调用结果消息")
    public String getMessage() {
        return message;
    }

    @ApiModelProperty(value = "状态码", required = true)
    public int getStatus() {
        return status;
    }

    @ApiModelProperty("成功时响应数据")
    public T getResult() {
        return result;
    }

    @ApiModelProperty(value = "时间戳", required = true, dataType = "Long")
    public Long getTimestamp() {
        return timestamp;
    }

    @ApiModelProperty(value = "业务代码")
    public String getCode() {
        return code;
    }

    public static <T> ResponseMessage<T> error(String message) {
        return error(500, message);
    }

    public static <T> ResponseMessage<T> error(int status, String message) {
        ResponseMessage<T> msg = new ResponseMessage<>();
        msg.message = message;
        msg.status(status);
        return msg.putTimeStamp();
    }

    public static <T> ResponseMessage<T> ok() {
        return ok(null);
    }

    private ResponseMessage<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public static <T> ResponseMessage<T> ok(T result) {
        ResponseMessage responseMessage = new ResponseMessage<T>();
        responseMessage.result(result);
        responseMessage.putTimeStamp();
        responseMessage.status(200);
        responseMessage.setMessage("success");
        return responseMessage;
    }

    public ResponseMessage<T> result(T result) {
        this.result = result;
        return this;
    }


    public ResponseMessage<T> code(String code) {
        this.code = code;
        return this;
    }


    public ResponseMessage() {

    }


    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }

    public ResponseMessage<T> status(int status) {
        this.status = status;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}