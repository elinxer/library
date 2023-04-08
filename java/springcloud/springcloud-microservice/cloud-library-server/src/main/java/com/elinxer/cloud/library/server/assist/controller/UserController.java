package com.elinxer.cloud.library.server.assist.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.security.entity.LoginAssistUser;
import com.dbn.cloud.platform.security.utils.SysUserUtils;
import com.dbn.cloud.platform.sms.handler.SmsDispatcher;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.assist.domain.entity.User;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;
import com.elinxer.cloud.library.server.assist.domain.vo.user.UserBindDeviceVo;
import com.elinxer.cloud.library.server.assist.domain.vo.user.UserInfoVo;
import com.elinxer.cloud.library.server.assist.domain.vo.user.UserLoginVo;
import com.elinxer.cloud.library.server.assist.domain.vo.user.UserRegVo;
import com.elinxer.cloud.library.server.assist.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author elinx
 */
@Slf4j
@RequestMapping("/v1/assist/user")
@RestController
public class UserController {

    @Autowired
    CacheService cacheService;

    @Resource
    private IUserService iUserService;

    @Resource
    SmsDispatcher smsDispatcher;


    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息（账号）")
    public ResponseMessage<UserInfoVo> getUserInfo(String username) {
        if (username == null) {
            return ResponseMessage.error("用户名为空");
        }
        Object userInfo = iUserService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).last("limit 1"));
        UserInfoVo userInfoVo = BeanConvertUtils.sourceToTarget(userInfo, UserInfoVo.class);
        if (userInfo == null) {
            return ResponseMessage.error("用户不存在");
        }
        return ResponseMessage.ok(userInfoVo);
    }

    @GetMapping("/getUserInfoByPhone")
    @ApiOperation(value = "获取用户信息（手机号）")
    public ResponseMessage<UserInfoVo> getUserInfoByPhone(String phone) {
        if (phone == null) {
            return ResponseMessage.error("用户名为空");
        }
        UserInfoVo userInfo = iUserService.getUserInfoByPhone(phone);
        if (userInfo == null) {
            return ResponseMessage.error("用户不存在");
        }
        return ResponseMessage.ok(userInfo);
    }

    @PostMapping("/registerPhone")
    @ApiOperation(value = "手机号注册")
    public ResponseMessage<UserInfoVo> registerPhone(@RequestBody UserRegVo regVo) {
        if (regVo.getPhone() == null) {
            return ResponseMessage.error("手机号为空");
        }

        if (!smsDispatcher.checkSmsCode(regVo.getPhone(), regVo.getSmsCode())) {
            return ResponseMessage.error("验证码错误");
        }

        UserInfoVo userInfo = iUserService.getUserInfoByPhone(regVo.getPhone());

        // 注册账号
        if (userInfo == null) {
            User user = new User();
            user.setUsername(regVo.getPhone());
            user.setPhone(regVo.getPhone());
            user.setRealName(regVo.getRealName());
            user.setStatus(1);
            iUserService.saveUser(user);
            userInfo = iUserService.getUserInfoByPhone(regVo.getPhone());
        }

        // 更新绑定用户设备序列号
        if (userInfo != null && regVo.getDeviceNo() != null) {
            // TODO 检查设备是否存在
            if (iUserService.checkUserBindDevice(userInfo.getId(), regVo.getDeviceNo())) {
                //throw new AppException("已绑定，不可重复操作.");
            }
            // 绑定用户设备
            iUserService.bindUserDevice(userInfo, regVo.getDeviceNo());
        }

        return ResponseMessage.ok(userInfo);
    }

    @PostMapping("/bindDevice")
    @ApiOperation(value = "绑定设备")
    public ResponseMessage<Boolean> bindDevice(@RequestBody UserBindDeviceVo reqVo) {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }

        Long userId = SysUserUtils.getLoginAssistUser().getId();
        UserInfoVo userInfo = iUserService.getUserInfo(userId);

        if (userInfo == null) {
            throw new AppException("用户不存在.");
        }

        if (reqVo.getDeviceNo() == null) {
            throw new AppException("找不到设备.");
        }

        if (iUserService.checkUserBindDevice(userId, reqVo.getDeviceNo())) {
            throw new AppException("已绑定，不可重复操作.");
        }

        // TODO 检查设备是否存在
        // 绑定用户设备
        // 更新绑定用户设备序列号
        boolean result = iUserService.bindUserDevice(userInfo, reqVo.getDeviceNo());

        return ResponseMessage.ok(result);
    }

    @PostMapping("/loginPhone")
    @ApiOperation(value = "手机号登录")
    public ResponseMessage<UserInfoVo> loginPhone(@RequestBody UserLoginVo reqVo) {
        if (reqVo.getPhone() == null) {
            return ResponseMessage.error("手机号为空");
        }

        if (!smsDispatcher.checkSmsCode(reqVo.getPhone(), reqVo.getSmsCode())) {
            return ResponseMessage.error("验证码错误");
        }

        UserInfoVo userInfo = iUserService.getUserInfoByPhone(reqVo.getPhone());
        if (userInfo == null) {
            return ResponseMessage.error("用户不存在.");
        }
        return ResponseMessage.ok(userInfo);
    }

    @GetMapping("/sendSmsCode")
    @ApiOperation(value = "发送验证码")
    public ResponseMessage<String> sendSmsCode(String phone) {
        if (phone == null) {
            return ResponseMessage.error("手机号错误");
        }
        return ResponseMessage.ok(smsDispatcher.sendSmsCode(phone));
    }

    @GetMapping("/checkSmsCode")
    @ApiOperation(value = "checkSmsCode")
    public ResponseMessage<?> checkSmsCode(String phone, String smsCode) {
        return ResponseMessage.ok(smsDispatcher.checkSmsCode(phone, smsCode));
    }

    @GetMapping("/devices")
    @ApiOperation(value = "用户设备列表")
    public ResponseMessage<List<DeviceUserVo>> devices() {

        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }

        Long userId = SysUserUtils.getLoginAssistUser().getId();
        List<DeviceUserVo> list = iUserService.getUserDevices(userId);

        return ResponseMessage.ok(list);
    }

    @GetMapping("/userDetail")
    @ApiOperation(value = "用户详情")
    public ResponseMessage<?> userDetail() {
        if (SysUserUtils.getLoginAssistUser() == null) {
            throw new AppException("请先登录");
        }
        Long userId = SysUserUtils.getLoginAssistUser().getId();
        return ResponseMessage.ok(iUserService.getUserInfo(userId));
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户信息")
    public ResponseMessage<?> updateUser(@RequestBody UserInfoVo userInfoVo) {
        LoginAssistUser loginAssistUser = SysUserUtils.getLoginAssistUser();

        if (loginAssistUser == null) {
            throw new AppException("请先登录");
        }
        Long userId = loginAssistUser.getId();
        userInfoVo.setId(userId);
        return ResponseMessage.ok(iUserService.updateUserInfo(userInfoVo));
    }

}
