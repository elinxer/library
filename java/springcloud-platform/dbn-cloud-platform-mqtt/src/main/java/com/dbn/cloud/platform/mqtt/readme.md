## MQTT使用说明

引入指令包，使用mqtt功能配置

如没有找到相关类型，则启动应用加入扫描：
```
@ComponentScan({"com.dbn.cloud.platform.mqtt", "xxxxx"})
```


项目必须加入此文件进行初始化:

```
package com.dbn.cloud.vns.server.common;


import com.dbn.cloud.platform.mqtt.MqttClientWrapper;
import com.dbn.cloud.platform.mqtt.config.MqttClientConfigInit;
import com.dbn.cloud.platform.mqtt.config.MqttClientProperties;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttClientWrapper;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttListenerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * mqtt核心启动类
 *
 * @author elinx
 */
@Configuration
@EnableConfigurationProperties(value = MqttClientProperties.class)
public class MqttClientInitHandle extends MqttClientConfigInit {

    @Slf4j
    public static class MqttClientNcuWrapper extends MqttClientWrapper {
        public MqttClientNcuWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
            super(mqttClient, listenerWrappers);
        }
    }

    @Bean
    IMqttClientWrapper mqttClientWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
        return new MqttClientNcuWrapper(mqttClient, listenerWrappers);
    }

//    @Bean
//    MqttSubscriptionInitialization initialization(IMqttClientWrapper mqttClientWrapper, MqttClientProperties properties) {
//        return new MqttSubscriptionInitialization(properties, mqttClientWrapper);
//    }

}

```

### 添加配置


```
# IoT配置
mqtt:
  client-id : server-dev-xxx # 根据业务改
  clean-session : true
  connect-timeout : 120
  keep-alive-interval : 60
  isSSL: true
  endpoint : xxxxxxx
  port: 8883
  root-ca-path: /data/app/crt/aws/AmazonRootCA1.pem
  cert-path :  /data/app/crt/aws/5772d8174b-certificate.pem.crt
  key-path :  /data/app/crt/aws//5772d8174b-private.pem
  init-topics : $aws/events/presence/disconnected/#,$aws/events/presence/connected/#
  #init-topics : $SYS/brokers/+/clients/+/connected,$SYS/brokers/+/clients/+/disconnected
```

### 添加启动程序配置文件

```
@Configuration
@EnableConfigurationProperties(value = MqttClientProperties.class)
public class MqttClientConfig extends MqttClientConfigInit {

    @Slf4j
    public static class MqttClientNcuWrapper extends MqttClientWrapper {
        public MqttClientNcuWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
            super(mqttClient, listenerWrappers);
        }
    }

    @Bean
    IMqttClientWrapper mqttClientWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
        return new MqttClientNcuWrapper(mqttClient, listenerWrappers);
    }

}
```


### 发送指令


```
@Autowired
private ApplicationContext applicationContext;

IMqttClientWrapper iMqttClientWrapper = applicationContext.getBean(IMqttClientWrapper.class);
iMqttClientWrapper.publish(topic, bytes);
```

### 订阅&监听消息

```
@Slf4j
@Component
public class MachineListener implements IMqttListenerWrapper {

    @Override
    public void onMessage(String topic, byte[] payload) {
        // 监听消息
    }
    
    @Override
    public void onSystemMessage(String topic, byte[] payload) {
        // 系统消息
    }
}
```


