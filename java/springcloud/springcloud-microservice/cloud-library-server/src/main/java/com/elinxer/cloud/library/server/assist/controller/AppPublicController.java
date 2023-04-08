package com.elinxer.cloud.library.server.assist.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.assist.aop.DeviceSignature;
import com.elinxer.cloud.library.server.assist.aop.RequestLimit;
import com.elinxer.cloud.library.server.assist.domain.dao.BuglyDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.entity.Bugly;
import com.elinxer.cloud.library.server.assist.domain.entity.Task;
import com.elinxer.cloud.library.server.assist.domain.vo.AbLineVo;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkUpgradeRespVo;
import com.elinxer.cloud.library.server.assist.domain.vo.ApkUpgradeVo;
import com.elinxer.cloud.library.server.assist.domain.vo.SearchLineReqVo;
import com.elinxer.cloud.library.server.assist.domain.vo.bugly.BuglyVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceActiveParamsRespVo;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceActiveParamsVo;
import com.elinxer.cloud.library.server.assist.domain.vo.task.ReportTaskVo;
import com.elinxer.cloud.library.server.assist.service.IAblineService;
import com.elinxer.cloud.library.server.assist.service.IApkService;
import com.elinxer.cloud.library.server.assist.service.ILineGeoService;
import com.elinxer.cloud.library.server.assist.service.ITaskService;
import com.elinxer.cloud.library.server.utils.GeoHashUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author elinx
 */
@Slf4j
@RequestMapping("/v1/assist/app-public")
@RestController
public class AppPublicController {

    @Autowired
    CacheService cacheService;

    @Resource
    private IAblineService iAblineService;

    @Resource
    private IApkService iApkService;

    @Resource
    private ILineGeoService iLineGeoService;

    @Resource
    private BuglyDao buglyDao;

    @Resource
    ITaskService iTaskService;

    @PostMapping("/activeParams")
    @ApiOperation(value = "获取设备激活参数")
    public ResponseMessage<DeviceActiveParamsRespVo> activeParams(DeviceActiveParamsVo req) {
        // 获取设备信息
        // 获取设备参数
        // 获取入网参数
        return ResponseMessage.ok();
    }

    @RequestLimit(time = 3)
    @DeviceSignature
    @PostMapping("/reportTask")
    @ApiOperation(value = "作业上报")
    public ResponseMessage<Boolean> reportTask(ReportTaskVo req) {
        String startAt = req.getStartAt();
        if (startAt != null) {
            req.setStartAt(DateUtil.format(new Date(Long.parseLong(req.getStartAt())), "yyyy-MM-dd HH:mm:ss"));
        }
        Task task = BeanConvertUtils.sourceToTarget(req, Task.class);
        return ResponseMessage.ok(iTaskService.saveUserTask(task));
    }

    @RequestLimit
    @PostMapping("/reportLine")
    @ApiOperation(value = "AB线上报")
    public ResponseMessage<Boolean> reportLine(@RequestBody @Validated AbLineVo req) {

        String location = iLineGeoService.add(req.getLocation());

        String nHash = GeoHashUtil.makeHash(location);

        QueryWrapper<Abline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("geo_hash", nHash);
        queryWrapper.eq("deleted", 0);
        List<Abline> abList = iAblineService.list(queryWrapper);

        if (abList != null && abList.size() >= 1) {
            return ResponseMessage.error("数据重复");
        }

        req.setLocation(location);
        req.setGeoHash(nHash);

        iAblineService.insertLine(req);

        return ResponseMessage.ok(true);
    }

    @PostMapping("/lines")
    @ApiOperation(value = "获取推荐打点路线")
    public ResponseMessage<?> lines(@RequestBody @Validated SearchLineReqVo req) {
        return ResponseMessage.ok(iLineGeoService.search(req));
    }

    @PostMapping("/upgrade")
    @ApiOperation(value = "APK升级列表")
    public ResponseMessage<ApkUpgradeRespVo> upgrade(@RequestBody @Validated ApkUpgradeVo req) {

        ApkUpgradeRespVo respVo = ApkUpgradeRespVo.builder().upgradeList(iApkService.upgradeList(req)).build();
        respVo.setUpgrade(null);
        if (respVo.getUpgradeList() != null && respVo.getUpgradeList().size() >= 1) {
            respVo.setUpgrade(respVo.getUpgradeList().get(respVo.getUpgradeList().size() - 1));
        }

        return ResponseMessage.ok(respVo);
    }

    @PostMapping("/bugly-qrvx88")
    @ApiOperation(value = "bugly")
    public ResponseMessage<?> bugly(@RequestBody BuglyVo req) {

        log.info("bugly: {}", req.toString());

        buglyDao.insert(Bugly.builder()
                .eventType(req.getEventType())
                .jsonValue(JSON.toJSONString(req)).build());

        return ResponseMessage.ok();
    }

    public static final String LOGGER_NAME = "log_assist_basic_stat";

    private final static Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    @PostMapping("/test-logger")
    @ApiOperation(value = "testLogger")
    public ResponseMessage<?> testLogger(@RequestBody BuglyVo req) {
        if (req != null) {
            int i = 0;
            while (true) {
                try {
                    Map<String, Object> data = new HashMap<>();

                    data.put("time", System.currentTimeMillis());
                    data.put("app_name", "dbn-vns");
                    data.put("rate", new Random().nextInt(999));
                    data.put("index_name", "appstat-dbn-vns-test");

                    logger.info(JSON.toJSONString(data));

                    if (i >= 1000) {
                        return ResponseMessage.ok();
                    }
                    i++;
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseMessage.ok();
    }

}
