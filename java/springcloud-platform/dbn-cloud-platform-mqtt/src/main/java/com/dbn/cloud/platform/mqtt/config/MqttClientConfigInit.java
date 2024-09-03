package com.dbn.cloud.platform.mqtt.config;


import com.dbn.cloud.platform.mqtt.MqttClientConnect;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.context.annotation.Bean;

/**
 * mqtt核心启动类
 *
 * @author elinx
 */
public class MqttClientConfigInit {

    @Bean
    public MqttClient mqttClient(MqttClientProperties properties) throws Exception {
        return MqttClientConnect.createMqttClient(properties);
    }

//    // 订阅系统消息时开启
//    @Bean
//    MqttSubscriptionInitialization initialization(IMqttClientWrapper mqttClientWrapper, MqttClientProperties properties) {
//        return new MqttSubscriptionInitialization(properties, mqttClientWrapper);
//    }

}
