package com.dbn.cloud.platform.sms.handler;


public interface ISmsDispatcher {


    String sendSmsCode(String phone);

    boolean checkSmsCode(String phone, String smsCode);

}
