package com.dbn.cloud.auth.server.service.impl;


import com.dbn.cloud.auth.server.dao.SysClientServiceDao;
import com.dbn.cloud.auth.server.dao.SysServiceDao;
import com.dbn.cloud.auth.server.service.SysServiceService;
import com.dbn.cloud.platform.security.entity.SysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@Transactional
public class SysServiceServiceImpl implements SysServiceService {


    @Autowired
    private SysServiceDao sysServiceDao;

    @Autowired
    private SysClientServiceDao sysClientServiceDao;


    /**
     * 添加服务
     */
    @Override
    public void save(SysService service) {
        service.setCreateTime(new Date());
        service.setUpdateTime(new Date());
        sysServiceDao.save(service);
    }

    /**
     * 更新服务
     */
    @Override
    public void update(SysService service) {
        service.setUpdateTime(new Date());
        service.setUpdateTime(new Date());
        sysServiceDao.updateByPrimaryKey(service);
    }

    /**
     * 删除服务
     */
    @Override
    public void delete(Long id) {
        SysService sysService = sysServiceDao.findById(id);
        sysServiceDao.deleteByParentId(sysService.getId());
        sysServiceDao.delete(id);
    }

    /**
     * 客户端分配服务
     */
    @Override
    public void setMenuToClient(Long clientId, Set<Long> serviceIds) {
        sysClientServiceDao.delete(clientId, null);
        if (!CollectionUtils.isEmpty(serviceIds)) {
            serviceIds.forEach(serviceId -> {
                sysClientServiceDao.save(clientId, serviceId);
            });
        }
    }

    /**
     * 客户端服务列表
     */
    @Override
    public List<SysService> findByClient(Set<Long> clientIds) {
        return sysClientServiceDao.findServicesBySlientIds(clientIds);
    }

    /**
     * 服务列表
     */
    @Override
    public List<SysService> findAll() {
        return sysServiceDao.findAll();
    }

    /**
     * ID获取服务
     */
    @Override
    public SysService findById(Long id) {
        return sysServiceDao.findById(id);
    }

    /**
     * 角色ID获取服务
     */
    @Override
    public Set<Long> findServiceIdsByClientId(Long clientId) {
        return sysClientServiceDao.findServiceIdsByClientId(clientId);
    }

    /**
     * 一级服务
     */
    @Override
    public List<SysService> findOnes() {
        return sysServiceDao.findOnes();
    }

}
