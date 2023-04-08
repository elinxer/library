package com.elinxer.cloud.library.server.assist.service.impl;

import com.alibaba.fastjson.JSON;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.cache.redis.lock.DistributedLock;
import com.dbn.cloud.platform.mqtt.config.MqttClientProperties;
import com.dbn.cloud.platform.mqtt.wrapper.IMqttClientWrapper;
import com.elinxer.cloud.library.server.assist.service.IMqttService;
import com.elinxer.cloud.library.server.common.AppEnvParamManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MqttServiceImpl implements IMqttService {

    private static final String DEVICE_SUBSCRIBE_CLIENT = "vns:device:subscribe:";

    private static final String DEVICE_REGISTER_SCHEDULE = "vns:device:register:schedule";

    private static final String DEVICE_SUBSCRIBE_CLIENT_LOCK = "vns:device:subscribe_lock:";

    private static String SERVICE_MQTT_CLIENT_ID;


    @Resource
    private ApplicationContext applicationContext;

    @Resource
    MqttClientProperties mqttClientProperties;

    @Resource
    CacheService cacheService;

    @Resource
    DistributedLock distributedLock;


    @Override
    public boolean subscribe(String clientId) {
        if (clientId == null || "".equals(clientId)) {
            return false;
        }

        String serviceClientId = AppEnvParamManager.getInstance().getMqttClientId();
        if (serviceClientId == null || "".equals(serviceClientId)) {
            return false;
        }

        try {
            boolean result = distributedLock.lock(DEVICE_SUBSCRIBE_CLIENT_LOCK + clientId, 1000L);
            if (!result) {
                // 已经被订阅
                log.error("MqttServiceImpl.subscribe result false lock: {}", clientId);
                distributedLock.releaseLock(DEVICE_SUBSCRIBE_CLIENT_LOCK + clientId);
                return false;
            }

            String resultClient = cacheService.getCache(DEVICE_SUBSCRIBE_CLIENT + clientId);

            if (resultClient != null && !"".equals(resultClient)) {
                log.error("MqttServiceImpl.subscribe result has subscribe by: {} => {}", resultClient, clientId);
                distributedLock.releaseLock(DEVICE_SUBSCRIBE_CLIENT_LOCK + clientId);
                return false;
            }

            cacheService.setCache(DEVICE_SUBSCRIBE_CLIENT + clientId, SERVICE_MQTT_CLIENT_ID, 86400 * 30);

            applicationContext.getBean(IMqttClientWrapper.class).subscribe("/vehicle/msg/screen/" + clientId);

            distributedLock.releaseLock(DEVICE_SUBSCRIBE_CLIENT_LOCK + clientId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unsubscribe(String clientId) {
        try {
            if (clientId == null || "".equals(clientId)) {
                return false;
            }

            distributedLock.releaseLock(DEVICE_SUBSCRIBE_CLIENT_LOCK + clientId);
            cacheService.deleteCache(DEVICE_SUBSCRIBE_CLIENT + clientId);

            applicationContext.getBean(IMqttClientWrapper.class).unsubscribe("/vehicle/msg/screen/" + clientId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注册客户端client ID
     *
     * @param clientId 客户端
     * @return boolean
     */
    @Override
    public boolean registerClient(String clientId) throws Exception {
        if (clientId == null || "".equals(clientId)) {
            return false;
        }
        boolean result = cacheService.getSrt().opsForHash().hasKey(DEVICE_REGISTER_SCHEDULE, clientId);
        if (result) {

            Object registerObj = cacheService.getSrt().opsForHash().get(DEVICE_REGISTER_SCHEDULE, clientId);
            Map<String, Object> objectMap = JSON.parseObject(String.valueOf(registerObj)).getInnerMap();

            Long timestamp = (Long) objectMap.get("timestamp");
            if (System.currentTimeMillis() - timestamp >= 3000) {
                // 过期重新注册设备监听客户端
                Map<String, Object> objectMap2 = new HashMap<>();
                objectMap2.put("timestamp", System.currentTimeMillis());
                cacheService.getSrt().opsForHash().put(DEVICE_REGISTER_SCHEDULE, clientId, JSON.toJSONString(objectMap2));
            } else {
                // 不能启动，mqtt项目必须，抛出启动失败异常
                throw new Exception("mqtt client has register: " + clientId);
            }
        } else {
            // 注册设备监听客户端
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("timestamp", System.currentTimeMillis());
            cacheService.getSrt().opsForHash().put(DEVICE_REGISTER_SCHEDULE, clientId, JSON.toJSONString(objectMap));
        }

        // 当前启动ID
        SERVICE_MQTT_CLIENT_ID = clientId;

        return true;
    }

//    @Scheduled(fixedDelay = 1000)
    public void registerClientLive() {
        try {
            if (Boolean.FALSE.equals(cacheService.getSrt().hasKey(DEVICE_REGISTER_SCHEDULE))) {
                log.error("registerClientLive key not exist error: {}", SERVICE_MQTT_CLIENT_ID);
                return;
            }
            Boolean result = cacheService.getSrt().opsForHash().hasKey(DEVICE_REGISTER_SCHEDULE, SERVICE_MQTT_CLIENT_ID);
            if (Boolean.TRUE.equals(result)) {
                // 更新设备监听客户端心跳
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("timestamp", System.currentTimeMillis());
                cacheService.getSrt().opsForHash().put(DEVICE_REGISTER_SCHEDULE, SERVICE_MQTT_CLIENT_ID, JSON.toJSONString(objectMap));
            } else {
                log.error("registerClientLive error: {}", SERVICE_MQTT_CLIENT_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO 设备事件监听
    // 不明白为什么跑着跑着就断开了设备事件主题监听，普通订阅不会，后面有时间可以查查
    // 这里保持3秒订阅一次吧，防止丢失事件通知
//    @Scheduled(fixedDelay = 3000)
    public void subscribeSystem() {
        try {
            List<String> initTopic = mqttClientProperties.getInitTopics();
            if (initTopic == null || initTopic.size() == 0) {
                log.info("subscribeSystem is null");
                return;
            }
            initTopic.forEach(topic -> {
                applicationContext.getBean(IMqttClientWrapper.class).subscribe(topic);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
