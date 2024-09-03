package com.dbn.cloud.gateway.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 查询应用绑定的资源权限
 */
@Mapper
@SuppressWarnings("all")
public interface SysServiceDao {


    @Select("select p.id,p.parent_id parentId , p.name, p.path, p.sort, p.create_time createTime , p.update_time updateTime,p.is_service isService from sys_service p inner join sys_client_service rp on p.id = rp.service_id where rp.client_id = #{clientId} order by p.sort")
    List<Map> listByClientId(Long clientId);


}
