package com.dbn.cloud.auth.server.service;


import com.dbn.cloud.auth.server.dto.SysClientDto;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface SysClientService {

    void saveOrUpdateClient(SysClientDto clientDto);

    void delete(Long id);

    void updateEnabled(Map<String, Object> params);

    SysClientDto getById(Long id);

    List<SysClientDto> findList(Map<String, Object> params);


}
