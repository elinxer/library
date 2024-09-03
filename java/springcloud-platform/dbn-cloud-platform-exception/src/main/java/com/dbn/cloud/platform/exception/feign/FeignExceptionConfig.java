package com.dbn.cloud.platform.exception.feign;


import com.dbn.cloud.platform.exception.FrameWorkException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static com.dbn.cloud.platform.exception.constant.ErrorEnum.PLAT_COMMON_0001;

/**
 * FeignExceptionConfig
 *
 * @author elinx
 * @date 2021-08-17
 */
@Slf4j
public class FeignExceptionConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    /**
     * 重新实现feign的异常处理，捕捉restful接口返回的json格式的异常信息
     */
    public class UserErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

            try {
                String json = Util.toString(response.body().asReader());
                // 非业务异常包装成自定义异常类ServiceException
                if (StringUtils.isNotEmpty(json)) {

                    if (json.contains("code")) {
                        FeignFailResult result = mapper.readValue(json, FeignFailResult.class);
                        result.setStatus(response.status());
                        // 业务异常包装成自定义异常类HytrixException
                        if (result.getStatus() != HttpStatus.OK.value()) {
                            exception = new HystrixException(result.getMsg());
                        } else {
                            exception = feign.FeignException.errorStatus(methodKey, response);
                        }
                    } else {
                        exception = new FrameWorkException(PLAT_COMMON_0001);
                    }
                } else {
                    exception = feign.FeignException.errorStatus(methodKey, response);
                }

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }


    }
}