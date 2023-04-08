package com.elinxer.cloud.library.server.assist.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author elinx
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DeviceSummaryEventParam implements Serializable {

    private static final long serialVersionUID = 3661537544962967925L;


    /**
     * 设备编码
     */
    private String deviceId;

    /**
     * 事件产生时间
     */
    private Long timestamp;

    /**
     * 消息生成时间
     */
    private Long generateTime;


}
