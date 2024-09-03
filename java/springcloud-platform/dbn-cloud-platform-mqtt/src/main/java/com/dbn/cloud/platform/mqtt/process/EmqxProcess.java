package com.dbn.cloud.platform.mqtt.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dbn.cloud.platform.mqtt.entity.ClientEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EmqxProcess {

    /**
     * EMQX 系统消息
     *
     * https://blog.csdn.net/lpwakaka/article/details/99821593
     * https://blog.csdn.net/houxian1103/article/details/113147963
     * https://docs.emqx.com/zh/enterprise/v4.4/advanced/system-topic.html#%E5%AE%A2%E6%88%B7%E7%AB%AF%E4%B8%8A%E4%B8%8B%E7%BA%BF%E4%BA%8B%E4%BB%B6
     *
     * @param payload         payload
     */
    public static byte[] processEmqxSys(byte[] payload) {
        String original = new String(payload);

        ClientEventMessage clientEventMessage = JSON.parseObject(payload, ClientEventMessage.class);
        //log.info("ClientEventMessage={}", JSON.toJSONString(clientEventMessage));

        if (StringUtils.isNotBlank(original)) {
            JSONObject bodyObject = JSONObject.parseObject(original);
            Long connectedTimestamp = bodyObject.getLong("connected_at");
            Long disconnectedTimestamp = bodyObject.getLong("disconnected_at");
            String clientId = bodyObject.getString("clientid");
            Map<String, Object> onlineOffLineEvent = new HashMap<>();
            onlineOffLineEvent.put("clientId", clientId);
            if (connectedTimestamp != null) {
                onlineOffLineEvent.put("eventType", "connected");
                onlineOffLineEvent.put("timestamp", connectedTimestamp);
            }
            if (disconnectedTimestamp != null) {
                onlineOffLineEvent.put("eventType", "disconnected");
                onlineOffLineEvent.put("timestamp", disconnectedTimestamp);
            }
            String body = JSONObject.toJSONString(onlineOffLineEvent);
            //log.info("body={}", body);
            payload = body.getBytes(StandardCharsets.UTF_8);
            return payload;
        }
        return null;
    }

}





