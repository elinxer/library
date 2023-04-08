package com.elinxer.cloud.library.server.assist.aop;

import com.elinxer.cloud.library.server.assist.domain.entity.Device;
import com.elinxer.cloud.library.server.assist.service.IDeviceService;
import com.elinxer.cloud.library.server.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class DeviceSignatureAspect {

    @Resource
    IDeviceService iDeviceService;

    @Around(value = "@annotation(DeviceSignature)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        try {
            this.checkSignParam();
            return pjp.proceed();
        } catch (Throwable e) {
            log.error("SignatureAspect>>>>>>>>", e);
            throw e;
        }
    }

    private void checkSignParam() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String oldSign = request.getHeader("x-sign");
        if (StringUtils.isBlank(oldSign)) {
            //throw new RuntimeException("取消签名Header[x-sign]信息");
        }
        //获取parameters（对应@RequestParam）
        Map<String, String[]> params = new HashMap<>();

        if (request.getParameterMap() != null) {
            Map<String, String[]> params2 = request.getParameterMap();
            for (Map.Entry<String, String[]> stringEntry : params2.entrySet()) {
                params.put(stringEntry.getKey(), stringEntry.getValue());
            }
            //params = request.getParameterMap();
        }

        if (params == null) {
            throw new RuntimeException("签名参数为空.");
        }

        log.info(params.toString());

        if (params.get("deviceNo") == null || params.get("deviceNo")[0] == null) {
            throw new RuntimeException("签名验签参数为空.");
        }

        log.info("oldSign={}", oldSign);
        log.info("oldSign={}", params);

        if (oldSign == null || "".equals(oldSign)) {
            oldSign = params.get("x-sign")[0];
            params.remove("x-sign");
        }

        log.info("oldSign={}", oldSign);

        Device device = iDeviceService.getDeviceByNo(params.get("deviceNo")[0]);
        if (device == null) {
            throw new RuntimeException("设备验签失败.");
        }
        String newSign = SignUtil.signParams(params, device.getSecretKey());

        log.info("newSign={}", newSign);

        if (!newSign.equals(oldSign)) {
            throw new RuntimeException("签名不一致.");
        }
    }

    private void checkSign() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String oldSign = request.getHeader("x-sign");
        if (StringUtils.isBlank(oldSign)) {
            //throw new RuntimeException("取消签名Header[X-SIGN]信息");
        }
        request.getParameterMap();

        //获取body（对应@RequestBody）
        String body = null;
//        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
//        if (body == null) {
//            // TODO
//            throw new RuntimeException("仅支持post json");
//        }

        //获取parameters（对应@RequestParam） TODO
        Map<String, String[]> params = null;
        if (request.getParameterMap() != null) {
            params = request.getParameterMap();
        }

        //获取path variable（对应@PathVariable）TODO
        String[] paths = null;
        ServletWebRequest webRequest = new ServletWebRequest(request, null);
        Map<String, String> uriTemplateVars = (Map<String, String>) webRequest.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (uriTemplateVars != null) {
            //paths = uriTemplateVars.values().toArray(new String[]{});
        }
        try {
            String newSign = SignUtil.sign(body, params, paths);
            if (!newSign.equals(oldSign)) {
                throw new RuntimeException("签名不一致...");
            }
        } catch (Exception e) {
            throw new RuntimeException("验签出错...", e);
        }
    }
}
