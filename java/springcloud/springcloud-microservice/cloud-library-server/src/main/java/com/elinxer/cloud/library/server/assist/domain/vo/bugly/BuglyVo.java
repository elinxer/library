package com.elinxer.cloud.library.server.assist.domain.vo.bugly;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "BuglyVo")
public class BuglyVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("isEncrypt")
    private Integer isEncrypt;
    @JsonProperty("eventContent")
    private Object eventContent;
    @JsonProperty("signature")
    private String signature;

}
