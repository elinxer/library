package com.dbn.cloud.gateway.server.token;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.dbn.cloud.platform.common.constant.ContextConstant;
import com.dbn.cloud.platform.common.constant.RedisCacheConst;
import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.security.config.PermitUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置Authorize
 */
@Component
@EnableConfigurationProperties(PermitUrlProperties.class)
@SuppressWarnings("all")
public class AuthorizeConfigManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${security.oauth2.apiEnable:true}")
    private boolean apiEnable;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
                                             AuthorizationContext authorizationContext) {
        // API接口校验控制开关
        if (!apiEnable) {
            return Mono.just(new AuthorizationDecision(true));
        }
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        if (request.getMethod() == HttpMethod.OPTIONS) { // 预检请求放行
            return Mono.just(new AuthorizationDecision(true));
        }
        PathMatcher pathMatcher = new AntPathMatcher();
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        //String restfulPath = method + ":" + path; // RESTFul接口权限设计
        String restfulPath = path; // 接口权限设计

        // 如果token以"bearer "为前缀，到此方法里说明JWT有效即已认证，其他前缀的token则拦截
        String bearerToken = request.getHeaders().getFirst(ContextConstant.BASIC_HEADER_KEY);
        if (StrUtil.isNotBlank(bearerToken) && !StrUtil.startWithIgnoreCase(bearerToken, ContextConstant.BEARER_HEADER_PREFIX)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        /**
         * 缓存取 [URL权限-角色集合] 规则数据
         * urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
         */
        // 根据请求路径判断有访问权限的角色列表
        String token = StringUtils.substringAfter(bearerToken, OAuth2AccessToken.BEARER_TYPE + " ");
        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色
        boolean requireCheck = false; // 是否需要鉴权，默认未设置拦截规则不需鉴权

        // ADMIN AUTH
        if (redisTemplate.hasKey(RedisCacheConst.TENANT_TOKEN_PRE + token)) {
            Map<String, Object> saasUrlPermRolesRules = redisTemplate.opsForHash().entries(RedisCacheConst.SAAS_URL_PERM_ROLES_KEY);
            for (Map.Entry<String, Object> permRoles : saasUrlPermRolesRules.entrySet()) {
                String perm = permRoles.getKey();
                if (pathMatcher.match(perm, restfulPath)) {
                    List<String> roles = Convert.toList(String.class, permRoles.getValue());
                    authorizedRoles.addAll(Convert.toList(String.class, roles));
                    if (requireCheck == false) {
                        requireCheck = true;
                    }
                }
            }
        }

        if (requireCheck == false) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断TOKEN中携带的用户角色是否有权限访问
        Mono<AuthorizationDecision> authorizationDecisionMono = authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRoles.contains(authority))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));

        return authorizationDecisionMono;
    }

}
