package com.dbn.cloud.auth.uua.json;

public interface JsonMapper {
    String write(Object input) throws Exception;

    <T> T read(String input, Class<T> type) throws Exception;
}
