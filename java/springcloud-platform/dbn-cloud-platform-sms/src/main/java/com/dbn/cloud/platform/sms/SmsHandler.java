package com.dbn.cloud.platform.sms;


import com.dbn.cloud.platform.sms.handler.SmsDispatcher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@ComponentScan(basePackages = "com.dbn.cloud.platform.sms")
public class SmsHandler {

    @Resource
    SmsDispatcher smsDispatcher;

    SmsHandler() {
    }

    public SmsDispatcher getSmsDispatcher() {
        return smsDispatcher;
    }

}
