package com.elinxer.cloud.library.server.common;


import com.dbn.cloud.platform.mqtt.MqttClientConnect;
import com.dbn.cloud.platform.mqtt.MqttClientWrapper;
import com.dbn.cloud.platform.mqtt.MqttSubscriptionInitialization;
import com.dbn.cloud.platform.mqtt.config.MqttClientProperties;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttClientWrapper;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttListenerWrapper;
import com.elinxer.cloud.library.server.assist.service.IMqttService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.List;

/**
 * mqtt核心启动类
 *
 * @author elinx
 */
@Slf4j
//@Configuration
//@EnableConfigurationProperties(value = MqttClientProperties.class)
public class MqttClientInitHandle {

    @Resource
    Environment environment;

    @Resource
    IMqttService iMqttService;

    @Slf4j
    public static class MqttClientNcuWrapper extends MqttClientWrapper {
        public MqttClientNcuWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
            super(mqttClient, listenerWrappers);
        }
    }

    @Bean
    public MqttClient mqttClient(MqttClientProperties properties) throws Exception {

        String mqttClientId = environment.getProperty("mqtt-client-id");
        String mqttSecretKey = environment.getProperty("mqtt-secret-key");

        log.info("Environment: {}, {}", mqttClientId, mqttSecretKey);

        if (mqttClientId != null && mqttSecretKey != null) {
            properties.setClientId(mqttClientId);
            properties.setUserName(mqttClientId);
            properties.setPassWord(mqttSecretKey);
        }

        AppEnvParamManager.getInstance().setMqttClientId(properties.getClientId());
        AppEnvParamManager.getInstance().setMqttSecretKey(properties.getPassWord());

        // 注册客户端
        iMqttService.registerClient(properties.getClientId());

        return MqttClientConnect.createMqttClient(properties);
    }

    @Bean
    IMqttClientWrapper mqttClientWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
        return new MqttClientNcuWrapper(mqttClient, listenerWrappers);
    }

    @Bean
    MqttSubscriptionInitialization initialization(IMqttClientWrapper mqttClientWrapper, MqttClientProperties properties) {
        return new MqttSubscriptionInitialization(properties, mqttClientWrapper);
    }

}
