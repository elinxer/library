package com.dbn.cloud.platform.exception.entity;

import java.text.MessageFormat;

/**
 * 异常码定义基类
 *
 * @author elinx
 * @date 2021-08-17
 */
public class ErrorCode implements IErrorCode {
    /**
     * 错误编码
     */
    private final String code;
    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造器
     *
     * @param code    错误编码
     * @param message 错误消息
     */
    public ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误编码
     *
     * @return 获取错误编码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取格式化错误消息
     *
     * @return 格式化错误消息
     * @params 格式化数组
     */
    @Override
    public String getMessage(Object... params) {
        if (null == params || 0 == params.length) {
            return message;
        }
        String _message = message;
        try {
            _message = MessageFormat.format(message, params);
        } catch (Exception e) {
        }
        return _message;
    }

    public static void main(String[] args) {
        //String message = MessageFormat.format("name={0}", "huh-------x");
        //System.out.println(message);


    }
}
