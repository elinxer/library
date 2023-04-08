package com.elinxer.cloud.library.server.assist.listener;


import cn.hutool.core.date.DateUtil;
import com.elinxer.cloud.library.server.assist.domain.entity.Error;
import com.elinxer.cloud.library.server.assist.domain.entity.ErrorCode;
import com.elinxer.cloud.library.server.assist.domain.event.DeviceErrorEventParam;
import com.elinxer.cloud.library.server.assist.events.DeviceErrorEvent;
import com.elinxer.cloud.library.server.assist.service.IErrorCodeService;
import com.elinxer.cloud.library.server.assist.service.IErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author elinx
 */
@Slf4j
@Component
public class EventDeviceErrorListener {


    private static Map<Integer, Long> errors = new HashMap<>();
    private static Map<Integer, ErrorCode> errorInfoMap = new HashMap<>();

    private static Long timestamp = 0L;

    @Resource
    IErrorService iErrorService;

    @Resource
    IErrorCodeService iErrorCodeService;

    /**
     * 设备故障处理事件
     *
     * @param event MachineErrorEvent
     */
    @EventListener
    public void machineStatusLineEvent(DeviceErrorEvent event) {
        DeviceErrorEventParam param = event.getDeviceErrorEventParam();
        if (param != null && param.getDeviceId() != null) {
            eventCenter(param);
        }
    }

    public void eventCenter(DeviceErrorEventParam eventParam) {
        try {

            if (timestamp <= 1) {
                timestamp = System.currentTimeMillis();
            }
            if ((System.currentTimeMillis() - timestamp) >= 60000) {
                errorInfoMap.clear();
            }

            Error error = new Error();

            eventParam.getErrors().forEach(errorCode -> {
                ErrorCode errorCodeInfo = null;

                if (errorInfoMap.get(errorCode) == null) {
                    errorCodeInfo = iErrorCodeService.detailByCode(String.valueOf(errorCode));
                    if (errorCodeInfo != null) {
                        errorInfoMap.put(errorCode, errorCodeInfo);
                    }
                } else {
                    errorCodeInfo = errorInfoMap.get(errorCode);
                }

                if (errorCodeInfo == null) {
                    errorCodeInfo = new ErrorCode();
                    errorCodeInfo.setMessage("not message error.");
                    errorCodeInfo.setLevel(1);
                    errorCodeInfo.setModule("SYS");
                }

                error.setMessage(errorCodeInfo.getMessage());
                error.setLevel(errorCodeInfo.getLevel());
                error.setModule(errorCodeInfo.getModule());

                error.setErrorAt(DateUtil.now());
                error.setDeviceNo(eventParam.getDeviceId());
                error.setErrCode(String.valueOf(errorCode));

                if (errors.get(errorCode) == null) {
                    errors.put(errorCode, System.currentTimeMillis());
                    iErrorService.saveError(error);
                }
                if (errors.get(errorCode) != null && (System.currentTimeMillis() - errors.get(errorCode)) >= 30000) {
                    errors.put(errorCode, System.currentTimeMillis());
                    iErrorService.saveError(error);
                }
            });
        } catch (Exception e) {
            log.error("===machineCenter.error={}", (Object) e.getStackTrace());
        }
    }

}
