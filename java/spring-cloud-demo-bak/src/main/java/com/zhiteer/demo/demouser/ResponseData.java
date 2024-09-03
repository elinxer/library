package com.zhiteer.demo.demouser;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {

    private String msg = "";

    private Number errorCode = 0;

    private Map<String, Object> data = new HashMap<>();

    private Object result = new Object();

    public Map<String, Object> getData() {
        return data;
    }

    public Number getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setErrorCode(Number errorCode) {
        this.errorCode = errorCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * @Description 定义无参数的构造方法
     **/
    public ResponseData() {

    }

    /**
     * @param data
     * @param msg
     * @param errorCode
     */
    public ResponseData(Map<String, Object> data, String msg, Number errorCode) {
        this.data = data;
        this.msg = msg;
        this.errorCode = errorCode;
    }


    public void ResponseObject(Object data) {
        this.result = data;
    }

}
