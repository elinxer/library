package com.dbn.cloud.platform.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.io.Serializable;


/**
 * 客户端应用信息
 *
 * @author elinx
 * @date 2021-08-10
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaultClientDetails extends BaseClientDetails implements Serializable {
    private static final long serialVersionUID = -4996423520248249518L;

    private long id;
    //限流标识  
    private long ifLimit;
    //限流次数
    private long limitCount;


    public DefaultClientDetails(String clientId, String resourceIds, String scopes,
                                String grantTypes, String authorities, String redirectUris) {
        super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
    }


}
