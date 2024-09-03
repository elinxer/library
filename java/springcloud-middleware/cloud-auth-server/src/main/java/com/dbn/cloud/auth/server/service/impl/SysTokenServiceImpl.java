package com.dbn.cloud.auth.server.service.impl;

import com.dbn.cloud.auth.server.service.AuthConstant;
import com.dbn.cloud.auth.server.service.SysTokenService;
import com.dbn.cloud.auth.uua.service.RedisClientDetailsService;
import com.dbn.cloud.auth.uua.service.ValidateCodeService;
import com.dbn.cloud.platform.common.utils.SpringUtils;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import com.dbn.cloud.platform.security.token.SmsCodeAuthenticationToken;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Service
public class SysTokenServiceImpl implements SysTokenService {

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenStore tokenStore;

    @Value(value = "${security.decode.key:2858310341595137}")
    private String decodeKey;
    @Value(value = "${security.decode.vi:2890256383840258}")
    private String decodeVi;

    @Autowired
    private ValidateCodeService validateCodeService;


    @Override
    public void preCheckClient(String clientId, String clientSecret) {
        if (clientId == null || "".equals(clientId)) {
            throw new UnapprovedClientAuthenticationException("请求参数中无clientId信息");
        }
        if (clientSecret == null || "".equals(clientSecret)) {
            throw new UnapprovedClientAuthenticationException("请求参数中无clientSecret信息");
        }
    }

    @Override
    public OAuth2AccessToken getClientTokenInfo(String clientId, String clientSecret) {
        OAuth2AccessToken oauth2AccessToken = null;
        this.preCheckClient(clientId, clientSecret);
        ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        Map<String, String> map = new HashMap<>();
        map.put("client_secret", clientSecret);
        map.put("client_id", clientId);
        map.put("grant_type", "client_credentials");
        TokenRequest tokenRequest = new TokenRequest(map, clientId, clientDetails.getScope(), "client_credentials");
        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(redisClientDetailsService);
        ClientCredentialsTokenGranter clientCredentialsTokenGranter = new ClientCredentialsTokenGranter(
                authorizationServerTokenServices, redisClientDetailsService, requestFactory);
        clientCredentialsTokenGranter.setAllowRefresh(true);
        oauth2AccessToken = clientCredentialsTokenGranter.grant("client_credentials", tokenRequest);
        return oauth2AccessToken;
    }

    @Override
    public synchronized OAuth2AccessToken getUserTokenInfo(String clientId, String clientSecret, String username, String password) {
        OAuth2AccessToken oauth2AccessToken = null;
        try {
            this.preCheckClient(clientId, clientSecret);
            ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);
            if (clientDetails == null) {
                throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
            } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
            }

            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(1), clientId, clientDetails.getScope(),
                    "customer");

            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oauth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            oauth2AccessToken = authorizationServerTokenServices.createAccessToken(oauth2Authentication);
            oauth2Authentication.setAuthenticated(true);
            return oauth2AccessToken;
        } catch (BadCredentialsException badCredentialsException) {
            throw badCredentialsException;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkSmsCode(String deviceId, String validCode) {

        deviceId = StringUtils.substringAfter(deviceId, AuthConstant.LOGIN_ASSIST_MOBILE_PRE);

        if (StringUtils.isBlank(deviceId)) {
            throw new InvalidGrantException("用户输入deviceId");
        }
        if (StringUtils.isBlank(validCode)) {
            throw new InvalidGrantException("用户没有输入validCode");
        }
        // 得到生成的验证码
        String code = "";
        try {
            code = validateCodeService.getCode(deviceId);
            if (!validCode.equals(code)) {
                throw new InvalidGrantException("验证码不正确");
            } else {
                // 移除验证码
                validateCodeService.remove(deviceId);
            }
            return true;
        } catch (Exception e) {
            throw new InvalidGrantException("验证码不存在");
        }
    }

    @Override
    public OAuth2AccessToken getMobileTokenInfo(String clientId, String clientSecret, String deviceId, String validCode) {
        OAuth2AccessToken oauth2AccessToken;

        this.preCheckClient(clientId, clientSecret);

        ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }

        if (!checkSmsCode(deviceId, validCode)) {
            throw new AppException("验证码错误.");
        }

        // 选择鉴权模式 mobile
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "mobile");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(deviceId);

        AuthenticationManager authenticationManager = SpringUtils.getBean(AuthenticationManager.class);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        OAuth2Authentication oauth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        AuthorizationServerTokenServices authorizationServerTokenServices = SpringUtils
                .getBean("defaultAuthorizationServerTokenServices", AuthorizationServerTokenServices.class);

        oauth2AccessToken = authorizationServerTokenServices.createAccessToken(oauth2Authentication);
        oauth2Authentication.setAuthenticated(true);

        return oauth2AccessToken;
    }

    @Override
    public PagerResult<Map<String, String>> getTokenList(Map<String, Object> params) {
        List<Map<String, String>> list = new ArrayList<>();
        // 根据分页参数获取对应数据
        List<String> keys = findKeysForPage("access:" + "*",
                MapUtils.getInteger(params, "page", 1),
                MapUtils.getInteger(params, "limit", 10));

        for (Object key : keys.toArray()) {
            OAuth2AccessToken token = (OAuth2AccessToken) redisTemplate.opsForValue().get(key);
            HashMap<String, String> map = new HashMap<String, String>();


            if (token != null) {
                map.put("token_type", token.getTokenType());
                map.put("token_value", token.getValue());
                map.put("expires_in", token.getExpiresIn() + "");
            }
            OAuth2Authentication oAuth2Auth = tokenStore.readAuthentication(token);
            Authentication authentication = oAuth2Auth.getUserAuthentication();

            map.put("client_id", oAuth2Auth.getOAuth2Request().getClientId());
            map.put("grant_type", oAuth2Auth.getOAuth2Request().getGrantType());

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

                if (authenticationToken.getPrincipal() instanceof LoginAppUser) {
                    LoginAppUser user = (LoginAppUser) authenticationToken.getPrincipal();
                    map.put("user_id", user.getId() + "");
                    map.put("user_name", user.getUsername() + "");
                    map.put("user_head_imgurl", user.getHeadImgUrl() + "");
                }

            } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
                // 刷新token方式
                PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
                if (authenticationToken.getPrincipal() instanceof LoginAppUser) {
                    LoginAppUser user = (LoginAppUser) authenticationToken.getPrincipal();
                    map.put("user_id", user.getId() + "");
                    map.put("user_name", user.getUsername() + "");
                    map.put("user_head_imgurl", user.getHeadImgUrl() + "");
                }

            }
            list.add(map);
        }
        return new PagerResult<Map<String, String>>(keys.size(), list);
    }

    // 支持单机 集群模式替换keys *的危险操作
    public List<String> findKeysForPage(String patternKey, int pageNum, int pageSize) {

        Object execute = redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

                Set<String> binaryKeys = new HashSet<>();

                Cursor<byte[]> cursor = connection
                        .scan(new ScanOptions.ScanOptionsBuilder().match(patternKey).count(1000).build());
                int tmpIndex = 0;
                int startIndex = (pageNum - 1) * pageSize;
                int end = pageNum * pageSize;
                while (cursor.hasNext()) {
                    if (tmpIndex >= startIndex && tmpIndex < end) {
                        binaryKeys.add(new String(cursor.next()));
                        tmpIndex++;
                        continue;
                    }

                    // 获取到满足条件的数据后,就可以退出了
                    if (tmpIndex >= end) {
                        break;
                    }

                    tmpIndex++;
                    cursor.next();
                }
                connection.close();
                return binaryKeys;
            }
        });
        List<String> result = new ArrayList<String>(pageSize);
        if (execute instanceof List) {
            Optional.ofNullable(result).orElse(Lists.newArrayList("")).addAll((List) execute);
        }
        return result;
    }

    @Override
    public OAuth2AccessToken getRefreshTokenInfo(String access_token) {
        // 拿到当前用户信息
        OAuth2AccessToken oAuth2AccessToken = null;
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (user != null) {
            if (user instanceof OAuth2Authentication) {
                Authentication athentication = (Authentication) user;
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) athentication.getDetails();
            }
        }
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);
        OAuth2Authentication auth = (OAuth2Authentication) user;

        if (auth != null) {
            ClientDetails clientDetails = redisClientDetailsService
                    .loadClientByClientId(auth.getOAuth2Request().getClientId());
            OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(redisClientDetailsService);
            RefreshTokenGranter refreshTokenGranter = new RefreshTokenGranter(authorizationServerTokenServices,
                    redisClientDetailsService, requestFactory);
            Map<String, String> map = new HashMap<>();
            map.put("grant_type", "refresh_token");
            map.put("refresh_token", accessToken.getRefreshToken().getValue());
            TokenRequest tokenRequest = new TokenRequest(map, auth.getOAuth2Request().getClientId(),
                    auth.getOAuth2Request().getScope(), "refresh_token");
            oAuth2AccessToken = refreshTokenGranter.grant("refresh_token", tokenRequest);
            tokenStore.removeAccessToken(accessToken);
        }
        return oAuth2AccessToken;
    }

    @Override
    public synchronized void removeToken(String access_token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);
        if (accessToken != null) {
            // 移除access_token
            tokenStore.removeAccessToken(accessToken);
            // 移除refresh_token
            if (accessToken.getRefreshToken() != null) {
                tokenStore.removeRefreshToken(accessToken.getRefreshToken());
            }
        }
    }

    @Override
    public OAuth2AccessToken getTokenInfo(String access_token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);

        return accessToken;
    }

    @Override
    public String desEncrypt(String data) {
        return desEncrypt(data, decodeKey, decodeVi);
    }

    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果
     */
    public static String desEncrypt(String data, String key, String iv) {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
