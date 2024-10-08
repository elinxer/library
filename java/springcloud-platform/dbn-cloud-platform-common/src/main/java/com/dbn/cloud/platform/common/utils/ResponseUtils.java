package com.dbn.cloud.platform.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author elinx
 * @since 1.0
 * <p>
 * 通用输出响应工具类
 */
@Slf4j
public class ResponseUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发送文本。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderJson(HttpServletResponse response, Object obj) {
        try {
            render(response, "application/json;charset=UTF-8", objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void renderJsonError(HttpServletResponse response, Object obj, int httpStatus) {
        try {
            response.setStatus(httpStatus);
            render(response, "application/json;charset=UTF-8", objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发送xml。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 发送内容。使用UTF-8编码。
     *
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try (PrintWriter out = response.getWriter()) {
            out.write(text);
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
