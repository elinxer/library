package com.dbn.cloud.auth.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dbn.cloud.auth.server.dao.SysClientDao;
import com.dbn.cloud.auth.server.dao.SysClientServiceDao;
import com.dbn.cloud.auth.server.dto.SysClientDto;
import com.dbn.cloud.auth.server.service.SysClientService;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import com.dbn.cloud.platform.security.entity.SysClient;
import com.dbn.cloud.platform.web.crud.service.impl.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class SysClientServiceImpl extends BaseService<SysClientDao, SysClientDto, SysClient> implements SysClientService {

    @Autowired
    private SysClientDao sysClientDao;

    @Autowired
    private SysClientServiceDao sysClientServiceDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Override
    public void saveOrUpdateClient(SysClientDto sysClient) {
        sysClient.setClientSecret(passwordEncoder.encode(sysClient.getClientSecretStr()));
        SysClient sysClientEntity = DtoToEntity(sysClient);

        if (sysClient.getId() != null) {// 修改
            sysClientDao.updateByPrimaryKey(sysClientEntity);
        } else {// 新增
            SysClient r = sysClientDao.getClient(sysClient.getClientId());
            if (r != null) {
                throw new IllegalArgumentException(sysClient.getClientId() + "已存在");
            }
            sysClientDao.save(sysClientEntity);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysClient client = sysClientDao.getById(id);
        sysClientDao.delete(id);
        sysClientServiceDao.delete(id, null);
        redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).delete(client.map().getClientId());
    }

    @Override
    public SysClientDto getById(Long id) {
        return this.EntityToDto(sysClientDao.getById(id));
    }

    @Override
    public List<SysClientDto> findList(Map<String, Object> params) {
        List<SysClient> sysClientList = sysClientDao.findList(params);
        List<SysClientDto> sysClientDtoList = sysClientList.stream().map(
                (item) -> {
                    return EntityToDto(item);
                }
        ).collect(Collectors.toList());
        return sysClientDtoList;
    }

    @Override
    public void updateEnabled(Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        Boolean enabled = MapUtils.getBoolean(params, "status");
        SysClient client = sysClientDao.getById(id);
        if (client == null) {
            throw new IllegalArgumentException("应用不存在");
        }
        client.setStatus(enabled);

        int i = sysClientDao.updateByPrimaryKey(client);
        ClientDetails clientDetails = client.map();
        if (enabled) {
            redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(client.getClientId(), JSONObject.toJSONString(clientDetails));
        } else {
            redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).delete(client.getClientId());
        }
    }
}
