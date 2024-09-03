
package com.dbn.cloud.platform.validation.exception;


import com.dbn.cloud.platform.exception.FrameWorkException;
import com.dbn.cloud.platform.exception.entity.IErrorCode;
import com.dbn.cloud.platform.validation.validate.ValidateResults;

import java.util.List;

/**
 * 有效性检查异常类
 *
 * @author elinx
 * @version 1.0.0
 */
public class ValidationException extends FrameWorkException {
    private ValidateResults results;

    public ValidationException(IErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public ValidationException(Throwable cause, IErrorCode errorCode, Object... params) {
        super(cause, errorCode, params);
    }

    public ValidationException(IErrorCode errorCode, ValidateResults results) {
        super(errorCode);
        this.results = results;
    }

    public List<ValidateResults.Result> getResults() {
        if (results == null) {
            return new java.util.ArrayList<>();
        }
        return results.getResults();
    }
}
