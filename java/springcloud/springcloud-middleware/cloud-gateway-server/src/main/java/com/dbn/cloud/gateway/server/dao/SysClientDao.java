package com.dbn.cloud.gateway.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 类说明 查询应用
 */
@Mapper
@SuppressWarnings("all")
public interface SysClientDao {

    @Select("select id id , client_id clientId, client_secret clientSecret, client_secret_str clientSecretStr  , resource_ids resourceIds ,scope,authorized_grant_types  authorizedGrantTypes ,access_token_validity accessTokenValidity ,refresh_token_validity  refreshTokenValidity ,web_server_redirect_uri webServerRedirectUri , additional_information additionalInformation ,authorities,if_limit ifLimit , limit_count limitCount  ,autoapprove,status from oauth_client_details t where t.client_id = #{clientId} and status =1")
    Map getClient(String clientId);

}
