package com.dbn.cloud.platform.common.constant;

/**
 * 上下文常量工具类
 *
 * @author elinx
 * @version 1.0
 */
public final class ContextConstant {
    private ContextConstant() {
    }

    /**
     * 封装的用户id
     */
    public static final String KEY_USER_ID = "userid";

    /**
     * 用户名称
     */
    public static final String KEY_NAME = "name";

    /**
     * 封装的 token 类型
     */
    public static final String KEY_TOKEN_TYPE = "token_type";

    /**
     * 封装的 用户账号
     */
    public static final String KEY_ACCOUNT = "account";

    /**
     * 封装的 客户端id
     */
    public static final String KEY_CLIENT_ID = "client_id";

    /**
     * token 签名
     * <p>
     * 签名密钥长度至少32位!!!
     */
    public static final String SIGN_KEY = "xxxx";

    /**
     * 租户ID TENANT_ID
     */
    public static final String KEY_TENANT_ID = "tenant_id";

    /**
     * 租户ID TENANT_ID
     */
    public static final String KEY_TENANT = "tenant";
    /**
     * 刷新 Token
     */
    public static final String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * User信息 认证请求头
     */
    public static final String BEARER_HEADER_KEY = "token";
    /**
     * User信息 认证请求头前缀
     */
    public static final String BEARER_HEADER_PREFIX = "Bearer ";
    /**
     * User信息 认证请求头前缀
     */
    public static final String BEARER_HEADER_PREFIX_EXT = "Bearer%20";

    /**
     * Client信息认证请求头
     */
    public static final String BASIC_HEADER_KEY = "Authorization";

    /**
     * Client信息认证请求头前缀
     */
    public static final String BASIC_HEADER_PREFIX = "Basic ";

    /**
     * Client信息认证请求头前缀
     */
    public static final String BASIC_HEADER_PREFIX_EXT = "Basic%20";

    public static final String USER_FLAG_SAAS = "SAAS";

    public static final String USER_FLAG_SYSTEM = "SYSTEM";

    /**
     * 是否boot项目
     */
    public static final String IS_BOOT = "boot";

    public static final String ADMIN_CODE = "ROOT";
    /**
     * 是否 内部调用项目
     */
    public static final String FEIGN = "x-feign";
    /**
     * 日志链路追踪id信息头
     */
    public static final String TRACE_ID_HEADER = "x-trace-header";
    /**
     * 日志链路追踪id日志标志
     */
    public static final String LOG_TRACE_ID = "trace";

    /**
     * 灰度发布版本号
     */
    public static final String GRAY_VERSION = "gray_version";
}
