package com.dbn.cloud.platform.mqtt;


import com.dbn.cloud.platform.mqtt.config.MqttClientProperties;
import com.dbn.cloud.platform.mqtt.config.MqttConstant;
import com.dbn.cloud.platform.mqtt.utils.SslUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.ssl.SSLSocketFactory;

/**
 * @author elinx
 */
@Slf4j
public class MqttClientConnect {

    /**
     * 创建mqtt客户端
     *
     * @param properties 参数
     * @return MqttClient
     */
    public static MqttClient createMqttClient(MqttClientProperties properties) throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();

        if (StringUtils.isNotBlank(properties.getUserName()) &&
                StringUtils.isNotBlank(properties.getPassWord())) {
            options.setUserName(properties.getUserName());
            options.setPassword(properties.getPassWord().toCharArray());
        }

        options.setCleanSession(properties.getCleanSession());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(properties.getConnectTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(properties.getKeepAliveInterval());
        options.setAutomaticReconnect(true);

        StringBuilder serverHostB = new StringBuilder();
        if (properties.getIsSSL()) {
            options.setHttpsHostnameVerificationEnabled(false);
            SSLSocketFactory sslSocketFactory = SslUtils.getSocketFactory(
                    MqttConstant.CA_KEY_PATH, MqttConstant.CER_KEY_PATH, MqttConstant.PRIVATE_KEY_PATH);
            options.setSocketFactory(sslSocketFactory);
            serverHostB.append(MqttConstant.SSL_PREFIX);
        } else {
            serverHostB.append(MqttConstant.TCP_PREFIX);
        }

        serverHostB.append(properties.getEndpoint()).append(":").append(properties.getPort());
        MqttClient client = new MqttClient(serverHostB.toString(), properties.getClientId(), new MemoryPersistence());

        // 不为空则设置密码
        if (properties.getPassWord() != null && !"".equals(properties.getPassWord())) {
            options.setPassword(properties.getPassWord().toCharArray());
        }

        client.connect(options);

        log.info("MQTT Init: clientId: {}, object:{}", options.getUserName(), client.toString());

        return client;
    }


}
