package com.dbn.cloud.platform.sms.handler;


import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.sms.aliyun.SmsAliyunDispatcher;
import com.dbn.cloud.platform.sms.config.SmsConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Service
public class SmsDispatcher implements ISmsDispatcher {

    @Resource
    SmsAliyunDispatcher smsAliyunDispatcher;

    @Resource
    CacheService cacheService;

    /**
     * 发送验证码
     *
     * @return Integer
     */
    @Override
    public String sendSmsCode(String phone) {
        if (phone == null || "".equals(phone)) {
            throw new AppException("PHONE ERROR.");
        }
        String smsCode = cacheService.getCache(SmsConstants.SMS_CODE_PREFIX + phone, (String key) -> {
            return smsAliyunDispatcher.sendSmsCode(phone);
        }, 60, TimeUnit.SECONDS);
        return smsCode;
    }

    @Override
    public boolean checkSmsCode(String phone, String smsCode) {

        if (phone == null || smsCode == null) {
            throw new AppException("参数错误");
        }

        String smsCodeCache = cacheService.getCache(SmsConstants.SMS_CODE_PREFIX + phone);

        if (smsCode.equals(smsCodeCache)) {
            cacheService.deleteCache(SmsConstants.SMS_CODE_PREFIX + phone);
        }

        return smsCode.equals(smsCodeCache);
    }


}
