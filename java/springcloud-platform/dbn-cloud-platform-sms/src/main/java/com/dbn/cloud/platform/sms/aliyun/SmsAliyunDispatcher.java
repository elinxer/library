package com.dbn.cloud.platform.sms.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.sms.config.SmsConfigProperties;
import com.dbn.cloud.platform.sms.handler.ISmsDriver;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
@EnableConfigurationProperties(SmsConfigProperties.class)
public class SmsAliyunDispatcher implements ISmsDriver {

    @Resource
    SmsConfigProperties smsConfigProperties;


    /**
     * 发送验证码
     *
     * @return Integer
     */
    @Override
    public String sendSmsCode(String phone) {

        log.info("smsConfigPropertiesxxxx=" + smsConfigProperties);

        if (smsConfigProperties == null) {
            throw new AppException("sms configs null");
        }

        DefaultProfile profile = DefaultProfile.
                getProfile(smsConfigProperties.getRegionId(), smsConfigProperties.getAccessKeyId(), smsConfigProperties.getSecret());

        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();

        request.setPhoneNumbers(phone);

        request.setSignName(smsConfigProperties.getSignName());
        request.setTemplateCode(smsConfigProperties.getTemplateCode());

        String code = getRandomCode();

        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return code;
    }

    /**
     * 获取验证码随机数 6位
     *
     * @return String
     */
    public static String getRandomCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }


    public static void main(String[] args) {


        while (true) {
            try {
                String code = getRandomCode();
                System.out.println(code);

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
