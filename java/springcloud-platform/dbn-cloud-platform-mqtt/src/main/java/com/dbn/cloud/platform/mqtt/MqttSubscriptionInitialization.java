package com.dbn.cloud.platform.mqtt;


import com.dbn.cloud.platform.mqtt.config.MqttClientProperties;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Slf4j
public class MqttSubscriptionInitialization implements CommandLineRunner {

    private IMqttClientWrapper mqttClientWrapper;

    private MqttClientProperties properties;

    public MqttSubscriptionInitialization(MqttClientProperties properties, IMqttClientWrapper mqttClientWrapper) {
        this.properties = properties;
        this.mqttClientWrapper = mqttClientWrapper;
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    public void init() {
        List<String> initTopic = properties.getInitTopics();
        log.info("initTopic={}", initTopic);
        if (initTopic == null || initTopic.size() <= 0) {
            log.info("initTopic is null");
            return;
        }
        initTopic.forEach(topic -> {
            mqttClientWrapper.subscribe(topic);
        });
    }
}