package com.dbn.cloud.platform.security.constant;

/**
 * UaaConstant常用，后续统一到common
 *
 * @author elinx
 * @version 1.0.0
 */
public class UaaConstant {

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    public static final String CACHE_CLIENT_KEY = "oauth2:oauth_client_details";

    public static final String TOKEN_PARAM = "access_token";

    public static final String TOKEN_HEADER = "accessToken";

    public static final String AUTH = "auth";

    public static final String TOKEN = "token";

    public static final String AUTHORIZATION = "Authorization";

}
