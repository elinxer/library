package com.dbn.cloud.platform.core.web;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 响应消息,controller中处理后，返回此对象，响应请求结果给客户端
 *
 * @author elinx
 * @since 1.0
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 8992436576262574064L;

    /**
     * 相应消息
     */
    protected String message;

    /**
     * 相应数据
     */
    protected T data;

    protected int status;

    private Long timestamp;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static <T> Response<T> error(String message) {
        return error(500, message);
    }

    public static <T> Response<T> error(int status, String message) {
        Response<T> msg = new Response<>();
        msg.message = message;
        msg.status(status);
        return msg.putTimeStamp();
    }

    public static <T> Response<T> ok() {
        return ok(null);
    }

    private Response<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public static <T> Response<T> ok(T result) {
        Response response = new Response<T>();
        response.data(result);
        response.putTimeStamp();
        response.status(200);
        response.setMessage("success");
        return response;
    }

    public Response<T> data(T data) {
        this.data = data;
        return this;
    }

    public Response() {

    }

    @Override
    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }

    public Response<T> status(int status) {
        this.status = status;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T result) {
        this.data = result;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
