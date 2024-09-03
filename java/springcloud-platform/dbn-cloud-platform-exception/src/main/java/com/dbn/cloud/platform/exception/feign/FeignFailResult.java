package com.dbn.cloud.platform.exception.feign;

import lombok.Data;

/**
 * Feign回传结构
 *
 * @author elinx
 * @date 2021-08-17
 */
@Data
public class FeignFailResult {

    private int status;
    private String msg;
}
