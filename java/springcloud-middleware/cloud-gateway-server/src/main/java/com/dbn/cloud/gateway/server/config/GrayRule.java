package com.dbn.cloud.gateway.server.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.dbn.cloud.platform.common.constant.ContextConstant;
import com.dbn.cloud.platform.common.utils.ContextUtils;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 自定义Rule配置
 */
public class GrayRule extends RoundRobinRule {

    @Override
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        String version = ContextUtils.getGrayVersion();
        System.out.println("version: " + version);
        List<Server> targetList = null;
        List<Server> upList = lb.getReachableServers();

        if (StrUtil.isNotEmpty(version)) {
            targetList = upList.stream().filter(
                    server -> version.equals(
                            ((NacosServer) server).getMetadata().get(ContextConstant.GRAY_VERSION)
                    )
            ).collect(Collectors.toList());
        }
        if (CollUtil.isEmpty(targetList)) {
            targetList = upList.stream().filter(
                    server -> {
                        String metadataVersion = ((NacosServer) server).getMetadata().get(ContextConstant.GRAY_VERSION);
                        return StrUtil.isEmpty(metadataVersion);
                    }
            ).collect(Collectors.toList());
        }
        if (CollUtil.isNotEmpty(targetList)) {
            return getServer(targetList);
        }
        return super.choose(lb, key);
    }

    private Server getServer(List<Server> upList) {
        int nextInt = RandomUtil.randomInt(upList.size());
        return upList.get(nextInt);
    }
}

