package com.dbn.cloud.gateway.server.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.dbn.cloud.gateway.server.dao.SysClientDao;
import com.dbn.cloud.gateway.server.dao.SysServiceDao;
import com.dbn.cloud.gateway.server.service.SysClientService;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import com.dbn.cloud.platform.security.entity.SysClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 查询应用绑定的资源权限
 */
@Slf4j
@SuppressWarnings("all")
@Service("sysClientService")
public class SysClientServiceImpl implements SysClientService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysClientDao sysClientDao;
    @Autowired
    private SysServiceDao sysServiceDao;

    public Map getClient(String clientId) {
        // 先从redis获取
        Map client = null;
        String value = (String) redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).get(clientId);
        // 没有从数据库获取
        if (StringUtils.isBlank(value)) {
            client = cacheAndGetClient(clientId);
        } else {
            client = JSONObject.parseObject(value, Map.class);
        }
        return client;
    }

    private Map cacheAndGetClient(String clientId) {
        // 从数据库读取
        Map client = null;
        client = sysClientDao.getClient(clientId);
        if (client != null) {
            //todo old SysClient sysClient = BeanUtil.toBean(client, SysClient.class);
            SysClient sysClient = BeanUtils.mapToBean(client, SysClient.class);

            // 写入redis缓存
            redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(clientId,
                    JSONObject.toJSONString(sysClient.map()));
            log.info("缓存clientId:{},{}", clientId, sysClient);
        }
        return client;
    }

    @Cacheable(value = "service", key = "#clientId")
    public List<Map> listByClientId(Long clientId) {

        return sysServiceDao.listByClientId(clientId);
    }


}
