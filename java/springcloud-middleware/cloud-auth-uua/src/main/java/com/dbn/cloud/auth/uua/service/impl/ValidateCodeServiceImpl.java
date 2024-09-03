package com.dbn.cloud.auth.uua.service.impl;


import com.dbn.cloud.auth.uua.service.ValidateCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Service
@SuppressWarnings("all")
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param deviceId  客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String deviceId, String imageCode) {

        String text = imageCode.toLowerCase().toString();

        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                // redis info
                connection.set(buildKey(deviceId).getBytes(), imageCode.getBytes());
                connection.expire(buildKey(deviceId).getBytes(), 60L * 5L);
                connection.close();
                return "";
            }
        });

    }

    /**
     * 获取验证码
     *
     * @param deviceId 前端唯一标识/手机号
     */
    @Override
    public String getCode(String deviceId) {
        String code = "";
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] temp = "".getBytes();
                temp = redisConnection.get(buildKey(deviceId).getBytes());
                redisConnection.close();
                return new String(temp);
            }
        });

        if (Objects.nonNull(obj)) {
            code = (String) obj;
        }
        return code;
    }

    /**
     * 删除验证码
     *
     * @param deviceId 前端唯一标识/手机号
     */
    @Override
    public void remove(String deviceId) {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                // redis info
                connection.del(buildKey(deviceId).getBytes());
                connection.close();
                return "";
            }
        });
    }

    /**
     * 验证验证码
     */
    @Override
    public void validate(HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new AuthenticationException("请在请求参数中携带deviceId参数") {
            };
        }
        String code = this.getCode(deviceId);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request, "validCode");
        } catch (ServletRequestBindingException e) {
            throw new AuthenticationException("获取验证码的值失败") {
            };
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new AuthenticationException("请填写验证码") {
            };
        }

        if (code == null) {
            throw new AuthenticationException("验证码不存在或已过期") {
            };
        }

        if (!StringUtils.equalsIgnoreCase(code, codeInRequest)) {
            throw new AuthenticationException("验证码不正确") {
            };
        }

        this.remove(deviceId);
    }

    private String buildKey(String deviceId) {
        return "sms:code:" + deviceId;
    }

    @Override
    public void validate(String deviceId, String validCode) {
        if (StringUtils.isBlank(deviceId)) {
            throw new AuthenticationException("请在请求参数中携带deviceId参数") {
            };
        }
        String code = this.getCode(deviceId);

        if (StringUtils.isBlank(validCode)) {
            throw new AuthenticationException("请填写验证码") {
            };
        }

        if (code == null) {
            throw new AuthenticationException("验证码不存在或已过期") {
            };
        }

        if (!StringUtils.equalsIgnoreCase(code, validCode)) {
            throw new AuthenticationException("验证码不正确") {
            };
        }

        this.remove(deviceId);
    }
}
