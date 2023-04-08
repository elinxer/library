package com.elinxer.cloud.library.server.assist.domain.enums;


/**
 * @author elinx
 */
public enum DeviceStatusEnum {

    OFFLINE(0),
    ONLINE(1);

    DeviceStatusEnum() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    private Integer value;

    DeviceStatusEnum(Integer value) {
        this.value = value;
    }


}
