package com.elinxer.cloud.library.server.assist.events;

import com.elinxer.cloud.library.server.assist.domain.event.DeviceSummaryEventParam;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;


/**
 * 设备故障处理
 *
 * @author elinx
 */
@Getter
public class DeviceSummaryEvent extends ApplicationEvent implements Serializable {

    private static final long serialVersionUID = -2769922522847738397L;

    private DeviceSummaryEventParam eventParam;


    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DeviceSummaryEvent(Object source, DeviceSummaryEventParam param) {
        super(source);
        this.eventParam = param;
    }

}
