package com.dbn.cloud.platform.mqtt;

import com.dbn.cloud.platform.mqtt.config.MqttConstant;
import com.dbn.cloud.platform.mqtt.process.EmqxProcess;
import com.dbn.cloud.platform.mqtt.wrapper.IMessageCallBack;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttClientWrapper;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttListenerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author elinx
 */
@Slf4j
public class MqttClientWrapper implements IMqttClientWrapper {

    private MqttClient mqttClient;

    private List<IMqttListenerWrapper> listenerWrappers;

    private ThreadPoolExecutor processExecutor;

    private AtomicInteger threadIndex;

    public MqttClientWrapper(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
        this.initExecutorClient(mqttClient, listenerWrappers);
    }

    /**
     * 初始化订阅客户端设置
     *
     * @param mqttClient       MqttClient
     * @param listenerWrappers List<IMqttListenerWrapper>
     */
    public void initExecutorClient(MqttClient mqttClient, List<IMqttListenerWrapper> listenerWrappers) {
        this.listenerWrappers = listenerWrappers;
        this.mqttClient = mqttClient;
        int coreSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        int maxSize = coreSize * 2;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(2000);
        threadIndex = new AtomicInteger(0);
        processExecutor = new ThreadPoolExecutor(coreSize, maxSize, 2, TimeUnit.MINUTES, blockingQueue, r -> {
            Thread thread = new Thread(r);
            thread.setName("subscribe-process-thread-" + threadIndex.incrementAndGet());
            return thread;
        });
    }

    /**
     * 订阅消息转发处理中心
     *
     * @param topic   消息主题
     * @param message MQTT消息
     */
    private void process(String topic, MqttMessage message) {
        if (listenerWrappers == null || listenerWrappers.size() <= 0) {
            log.warn("<<<<<<<<<<subscribe listener size is null={}", topic);
            return;
        }
        listenerWrappers.forEach(listener -> {
            byte[] payload = message.getPayload();
            if (topic.startsWith(MqttConstant.DOLLAR_MARK)) {
                // $ 符号开始主题
                if (topic.startsWith(MqttConstant.PAHO_SYSTEM_TOPIC_PREFIX)) {
                    payload = EmqxProcess.processEmqxSys(payload);
                    listener.onSystemMessage(topic, payload);
                } else if (topic.startsWith(MqttConstant.AWS_CONNECTION_DIS_PREFIX)) {
                    listener.onSystemMessage(topic, payload);
                }
            } else {
                listener.onMessage(topic, payload);
            }
        });
    }

    @Override
    public void subscribe(String topic) {
        try {
            log.info("<<<<<<<<<<subscribe.topic={}", topic);
            mqttClient.subscribe(topic, (pubTopic, mqttMessage) ->
                    processExecutor.execute(() -> process(pubTopic, mqttMessage)));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(String topic, IMessageCallBack iMessageCallBack) {
        try {
            log.info("<<<<<<<<<<subscribe.topic={}", topic);
            mqttClient.subscribe(topic, (pubTopic, mqttMessage) ->
                    processExecutor.execute(() -> iMessageCallBack.process(pubTopic, mqttMessage.getPayload()))
            );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe(String topic) {
        try {
            log.info("<<<<<<<<<<unsubscribe.topic={}", topic);
            mqttClient.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(String topic, byte[] payload) {
        try {
            log.info("<<<<<<<<<<publish.topic={},size={}", topic, payload.length);
            mqttClient.publish(topic, payload, 1, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
