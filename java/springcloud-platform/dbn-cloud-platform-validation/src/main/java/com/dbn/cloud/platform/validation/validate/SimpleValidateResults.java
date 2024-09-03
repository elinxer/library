package com.dbn.cloud.platform.validation.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * 有效性检查结果
 *
 * @author elinx
 * @version 1.0.0
 */
public class SimpleValidateResults implements ValidateResults {

    private static final long serialVersionUID = -3355828475840578917L;
    private List<ValidateResults.Result> results = new ArrayList<>();

    public SimpleValidateResults addResult(String field, String message) {
        results.add(new Result(field, message));
        return this;
    }

    public SimpleValidateResults addResult(ValidateResults.Result result) {
        results.add(result);
        return this;
    }

    @Override
    public boolean isSuccess() {
        return results == null || results.isEmpty();
    }

    @Override
    public List<ValidateResults.Result> getResults() {
        return results;
    }

    class Result implements ValidateResults.Result {
        private static final long serialVersionUID = -4717219071013488363L;
        private String field;
        private String message;

        public Result(String field, String message) {
            this.field = field;
            this.message = message;
        }

        @Override
        public String getField() {
            return field;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"field\":\"" + field + '\"' +
                    ", \"message:\"" + message + '\"' +
                    '}';
        }
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return "success";
        }
        return results.toString();
    }
}
