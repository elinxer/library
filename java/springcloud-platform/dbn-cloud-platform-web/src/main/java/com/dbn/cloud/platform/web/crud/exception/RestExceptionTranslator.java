package com.dbn.cloud.platform.web.crud.exception;

import com.alibaba.fastjson.JSONException;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.exception.FrameWorkException;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.dbn.cloud.platform.validation.validate.SimpleValidateResults;
import com.dbn.cloud.platform.validation.validate.ValidateResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 *
 * @author elinx
 * @date 2021-08-20
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionTranslator {

    /**
     * 应用异常
     *
     * @param exception 应用异常类
     * @return 回传异常信息
     */
    @ExceptionHandler(AppException.class)
    ResponseMessage handleException(AppException exception) {
        if (exception.getCause() != null) {
            log.error("{}:{}", exception.getMessage(), exception.getCode(), exception.getCause());
        }
        return ResponseMessage.error(400, exception.getMessage()).code(exception.getCode());
    }

    /**
     * 框架异常
     *
     * @param exception 框架异常
     * @return 回传异常信息
     */
    @ExceptionHandler(FrameWorkException.class)
    ResponseMessage handleException(FrameWorkException exception) {
        if (exception.getCause() != null) {
            log.error("{}:{}", exception.getMessage(), exception.getCode(), exception.getCause());
        }
        return ResponseMessage.error(400, exception.getMessage()).code(exception.getCode());
    }

    /**
     * Controller层校验异常
     *
     * @param e Controller层校验出发的异常
     * @return 回传调用方错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseMessage handleException(MethodArgumentNotValidException e) {
        SimpleValidateResults results = new SimpleValidateResults();
        e.getBindingResult().getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .forEach(fieldError -> results.addResult(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseMessage.error(400, results.getResults().isEmpty() ? e.getMessage() : results.getResults().get(0).getMessage()).result(results.getResults());
    }

    /**
     * service层校验异常
     *
     * @param e service层校验出发的异常
     * @return 回传调用方错误
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseMessage handleConstraintViolationException(ConstraintViolationException e) {
        SimpleValidateResults results = new SimpleValidateResults();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            results.addResult(violation.getPropertyPath().toString(), violation.getMessage());
        }
        List<ValidateResults.Result> errorResults = results.getResults();
        return ResponseMessage
                .error(400, errorResults.isEmpty() ? "" : errorResults.get(0).getMessage())
                .result(errorResults);
    }

    /**
     * 调用校验异常
     *
     * @param exception 自定义校验异常
     * @return 回传调用方错误
     */
    @ExceptionHandler(com.dbn.cloud.platform.validation.exception.ValidationException.class)
    ResponseMessage<List<ValidateResults.Result>> handleException(com.dbn.cloud.platform.validation.exception.ValidationException exception) {
        return ResponseMessage.<List<ValidateResults.Result>>error(400,
                        exception.getResults().isEmpty() ? exception.getMessage() : exception.getResults().get(0).getMessage())
                .result(exception.getResults());
    }


    @ExceptionHandler(JSONException.class)
    ResponseMessage handleException(JSONException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseMessage.error(400, "解析JSON失败");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseMessage handleException(IllegalArgumentException exception) {
        String msg = exception.getMessage();
        if (null == msg) {
            log.error(msg = "参数错误", exception);
        }
        return ResponseMessage.error(400, msg);
    }


    /**
     * 请求方式不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    ResponseMessage handleException(HttpRequestMethodNotSupportedException exception) {
        return ResponseMessage
                .error(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持的请求方式")
                .result(exception.getSupportedHttpMethods());

    }

    /**
     * 404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseMessage handleException(NoHandlerFoundException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("url", exception.getRequestURL());
        result.put("method", exception.getHttpMethod());
        return ResponseMessage.error(HttpStatus.NOT_FOUND.value(), "请求地址不存在.").result(result);
    }

    /**
     * ContentType不支持异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    ResponseMessage handleException(HttpMediaTypeNotSupportedException exception) {
        return ResponseMessage.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                        "不支持的请求类型:" + exception.getContentType().toString())
                .result(exception.getSupportedMediaTypes()
                        .stream()
                        .map(MediaType::toString)
                        .collect(Collectors.toList()));
    }

    /**
     * 请求方法的的参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseMessage handleException(MissingServletRequestParameterException exception) {
        return ResponseMessage
                .error(HttpStatus.BAD_REQUEST.value(), "参数[" + exception.getParameterName() + "]不能为空");
    }


    /**
     * 找不到具体异常的默认异常类
     *
     * @param exception
     * @return 回传调用方错误
     */
    @ExceptionHandler(BadCredentialsException.class)
    ResponseMessage handleException(BadCredentialsException exception) {
        String msg = Optional.ofNullable(exception.getMessage())
                .orElse("服务器内部错误");
        log.error(exception.getMessage(), exception);
        return ResponseMessage.error(500, "密码错误!!!");
    }

    /**
     * 找不到具体异常的默认异常类
     *
     * @param exception
     * @return 回传调用方错误
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseMessage handleException(RuntimeException exception) {
        String msg = Optional.ofNullable(exception.getMessage())
                .orElse("服务器内部错误");
        log.error(exception.getMessage(), exception);
        return ResponseMessage.error(500, msg);
    }

}

