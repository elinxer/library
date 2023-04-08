package com.elinxer.cloud.library.server.assist.domain.vo.bugly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TagEventContentVo {

    @JsonProperty("datas")
    private List<DatasDTO> datas;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("platformId")
    private Integer platformId;
    @JsonProperty("appName")
    private String appName;
    @JsonProperty("date")
    private String date;
    @JsonProperty("appUrl")
    private String appUrl;

    @NoArgsConstructor
    @Data
    public static class DatasDTO {
        @JsonProperty("tagId")
        private Integer tagId;
        @JsonProperty("tagName")
        private String tagName;
        @JsonProperty("crashCount")
        private Integer crashCount;
        @JsonProperty("crashUser")
        private Integer crashUser;
        @JsonProperty("type")
        private Integer type;
        @JsonProperty("url")
        private String url;
    }
}