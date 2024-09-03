package com.dbn.cloud.auth.server.controller;

import com.dbn.cloud.auth.server.dto.LoginMobileDto;
import com.dbn.cloud.auth.server.dto.LoginUserDto;
import com.dbn.cloud.auth.server.service.AuthConstant;
import com.dbn.cloud.auth.server.service.SysTokenService;
import com.dbn.cloud.platform.cache.redis.serializer.RedisObjectSerializer;
import com.dbn.cloud.platform.common.constant.RedisCacheConst;
import com.dbn.cloud.platform.common.utils.SpringUtils;
import com.dbn.cloud.platform.security.entity.LoginAppUser;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OAuth API
 */
@Slf4j
@RestController
@Api(tags = "OAuth API")
@SuppressWarnings("all")
public class OAuth2Controller {

    @Autowired
    private SysTokenService sysTokenService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "辅助驾驶手机号登录")
    @PostMapping("/oauth/assist/mobile/token")
    public ResponseMessage assistMobileToken(@RequestBody LoginMobileDto dto) {

        if (StringUtils.isEmpty(dto.getMobile()) || StringUtils.isEmpty(dto.getSmsCode())) {
            return ResponseMessage.error("账号/验证码不能为空！");
        }

        if (!isPhone(dto.getMobile())) {
            return ResponseMessage.error("手机号错误.");
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        try {

            String clientId = request.getHeader("client-id");

            clientId = "assist-mobile";
            String clientSecret = "assist-mobile";

            OAuth2AccessToken oAuth2AccessToken = sysTokenService.getMobileTokenInfo(clientId, clientSecret,
                    AuthConstant.LOGIN_ASSIST_MOBILE_PRE + dto.getMobile(), dto.getSmsCode());

            //setTenantId(oAuth2AccessToken.getValue());

            return ResponseMessage.ok(oAuth2AccessToken);
        } catch (BadCredentialsException e) {
            return ResponseMessage.error("账号未注册或密码错误！").code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @ApiOperation(value = "辅助驾驶用户登录")
    @PostMapping("/oauth/assist/user/token")
    public ResponseMessage assistUserToken(@RequestBody LoginUserDto loginUserDto) {

        if (StringUtils.isEmpty(loginUserDto.getUsername()) || StringUtils.isEmpty(loginUserDto.getPassword())) {
            return ResponseMessage.error("账号/密码不能为空！");
        }

        String desEncryptPwd = loginUserDto.getPassword();

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        try {

            String clientId = request.getHeader("client-id");

            clientId = "assist-wechat";
            String clientSecret = "assist-wechat";

            OAuth2AccessToken oAuth2AccessToken = sysTokenService.getUserTokenInfo(clientId, clientSecret,
                    AuthConstant.LOGIN_ASSIST_PRE + loginUserDto.getUsername(), desEncryptPwd);

            //setTenantId(oAuth2AccessToken.getValue());

            return ResponseMessage.ok(oAuth2AccessToken);
        } catch (BadCredentialsException e) {
            return ResponseMessage.error("账号未注册或密码错误！").code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @ApiOperation(value = "管理系统用户登录")
    @PostMapping("/oauth/sys/user/token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client-id", value = "应用标识", paramType = "header", dataType = "String", required = true),
            //@ApiImplicitParam(name = "client_secret", value = "应用密钥", paramType = "header", dataType = "String", required = true)
    })
    public ResponseMessage sysUserToken(@RequestBody LoginUserDto loginUserDto) {

        if (StringUtils.isEmpty(loginUserDto.getUsername()) || StringUtils.isEmpty(loginUserDto.getPassword())) {
            return ResponseMessage.error("账号/密码不能为空！");
        }

        String desEncryptPwd = loginUserDto.getPassword();

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        try {

            String clientId = request.getHeader("client-id");

            clientId = "app";
            //查找表检查秘钥
            String clientSecret = "app";

            OAuth2AccessToken oAuth2AccessToken = sysTokenService.getUserTokenInfo(clientId, clientSecret,
                    AuthConstant.LOGIN_SAAS_PRE + loginUserDto.getUsername(), desEncryptPwd);
            setTenantId(oAuth2AccessToken.getValue());
            return ResponseMessage.ok(oAuth2AccessToken);
        } catch (BadCredentialsException e) {
            return ResponseMessage.error("账号未注册或密码错误！").code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @ApiOperation(value = "云平台登录")
    @PostMapping("/oauth/user/token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client-id", value = "应用标识", paramType = "header", dataType = "String", required = true),
//            @ApiImplicitParam(name = "client_secret", value = "应用密钥", paramType = "header", dataType = "String", required = true)
    })
    public ResponseMessage getUserTokenInfo(@RequestBody LoginUserDto loginUserDto) {

        if (StringUtils.isEmpty(loginUserDto.getUsername()) || StringUtils.isEmpty(loginUserDto.getPassword())) {
            return ResponseMessage.error("账号/密码不能为空！");
        }

        if (!isPhone(loginUserDto.getUsername())) {
            //return ResponseMessage.error("请使用手机号登录！");
        }

//        String desEncryptPwd = sysTokenService.desEncrypt(loginUserDto.getPassword());
//        if (StringUtils.isEmpty(desEncryptPwd)) {
//            return ResponseMessage.error("密码需要加密！");
//        }
//        if (!isValidPassword(desEncryptPwd)) {
//            return ResponseMessage.error("密码必须为6~10位包括大小写字母、数字、特殊字符#@!~%^&*");
//        }

        String desEncryptPwd = loginUserDto.getPassword();

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        try {
            String clientId = request.getHeader("client-id");

            //String clientSecret = request.getHeader("client_secret");

            //查找表检查秘钥
            String clientSecret = "app";

            OAuth2AccessToken oAuth2AccessToken = sysTokenService.getUserTokenInfo(clientId, clientSecret,
                    AuthConstant.LOGIN_SAAS_PRE + loginUserDto.getUsername(), desEncryptPwd);
            setTenantId(oAuth2AccessToken.getValue());
            return ResponseMessage.ok(oAuth2AccessToken);
        } catch (BadCredentialsException e) {
            return ResponseMessage.error("账号未注册或密码错误！").code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @ApiOperation(value = "运维管理平台登录")
    @PostMapping("/oauth/sysmanager/token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "应用标识", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "client_secret", value = "应用密钥", paramType = "header", dataType = "String", required = true)})
    public ResponseMessage getWebUserTokenInfo(@RequestBody LoginUserDto loginUserDto) {
        if (null == loginUserDto || StringUtils.isEmpty(loginUserDto.getUsername())
                || StringUtils.isEmpty(loginUserDto.getPassword())) {
            return ResponseMessage.error("账号、密码不能为空！");
        }
        if (!isValidUserName(loginUserDto.getUsername())) {
            return ResponseMessage.error("账号必须为字母数字且长度3~10位！");
        }
        String desEncryptPwd = sysTokenService.desEncrypt(loginUserDto.getPassword());
        if (StringUtils.isEmpty(desEncryptPwd)) {
            return ResponseMessage.error("密码需要加密！");
        }
        if (!isValidPassword(desEncryptPwd)) {
            return ResponseMessage.error("密码必须为6~10位包括大小写字母、数字、特殊字符#@!~%^&*");
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        try {
            String clientId = request.getHeader("client_id");
            String clientSecret = request.getHeader("client_secret");
            OAuth2AccessToken oAuth2AccessToken = sysTokenService.getUserTokenInfo(clientId, clientSecret,
                    AuthConstant.LOGIN_SYSTEM_PRE + loginUserDto.getUsername(), desEncryptPwd);
            return ResponseMessage.ok(oAuth2AccessToken);
        } catch (BadCredentialsException e) {
            return ResponseMessage.error("账号未注册或密码错误！").code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @ApiOperation(value = "access_token刷新token")
    @PostMapping(value = "/oauth/refresh/token", params = "access_token")
    @ApiParam(required = true, name = "access_token", value = "access_token")
    public ResponseMessage refreshTokenInfo(String access_token) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        try {
            OAuth2AccessToken oAuth2AccessToken = sysTokenService.getRefreshTokenInfo(access_token);
            return ResponseMessage.ok(oAuth2AccessToken);
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage()).code(HttpStatus.UNAUTHORIZED.value() + "")
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private void setTenantId(String token) {
        RedisTemplate stringRedisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate", RedisTemplate.class);
        RedisTemplate redisTemplateTmp = new RedisTemplate();
        redisTemplateTmp.setConnectionFactory(stringRedisTemplate.getConnectionFactory());
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplateTmp.setKeySerializer(stringSerializer);
        redisTemplateTmp.setHashKeySerializer(stringSerializer);
        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        redisTemplateTmp.setValueSerializer(redisObjectSerializer);
        redisTemplateTmp.setHashValueSerializer(redisObjectSerializer);
        redisTemplateTmp.afterPropertiesSet();

        LoginAppUser loginAppUser = (LoginAppUser) redisTemplateTmp.opsForValue().get("oauth2:token:" + token);

        if (loginAppUser != null) {
            redisTemplate.opsForValue().set(RedisCacheConst.TENANT_TOKEN_PRE + token, loginAppUser.getId());
        }
    }

    /**
     * 移除access_token和refresh_token
     */
    @ApiOperation(value = "注销")
    @PostMapping(value = "/oauth/remove/token", params = "access_token")
    public ResponseMessage removeToken(String access_token) {
        sysTokenService.removeToken(access_token);
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "获取token信息")
    @PostMapping(value = "/oauth/get/token", params = "access_token")
    public ResponseMessage getTokenInfo(String access_token) {
        return ResponseMessage.ok(sysTokenService.getTokenInfo(access_token));
    }

    /**
     * 正则判断手机号
     *
     * @param phone
     * @return
     */
    public boolean isPhone(String phone) {
        String regex = "^1(3|4|5|6|7|8|9)[0-9]{9}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 密码是否符合规范
     *
     * @param pwd
     * @return
     */
    public boolean isValidPassword(String pwd) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#@!~%^&*]).{6,10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        boolean isMatch = m.matches();
        return isMatch;
    }

    /**
     * 账号是否符合规范
     *
     * @param pwd
     * @return
     */
    public boolean isValidUserName(String userName) {
        String regex = "^[a-zA-Z0-9]{3,10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userName);
        boolean isMatch = m.matches();
        return isMatch;
    }

    public static boolean isValidUserName2(String userName) {
        String regex = "^[a-zA-Z0-9]{3,10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userName);
        boolean isMatch = m.matches();
        return isMatch;
    }

}
