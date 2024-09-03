package com.dbn.cloud.platform.exception.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * feign client 避免熔断异常处理
 *
 * @author elinx
 */
public class HystrixException extends HystrixBadRequestException {

    private static final long serialVersionUID = -2437160791033393978L;

    public HystrixException(String msg) {
        super(msg);
    }

    public HystrixException(Exception e) {
        this(e.getMessage());
    }
}
