package com.elinxer.cloud.library.server.assist.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dbn.cloud.platform.core.utils.BeanConvertUtils;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.assist.domain.DeviceStatus;
import com.elinxer.cloud.library.server.assist.domain.dao.UserDao;
import com.elinxer.cloud.library.server.assist.domain.dao.UserDeviceDao;
import com.elinxer.cloud.library.server.assist.domain.entity.User;
import com.elinxer.cloud.library.server.assist.domain.entity.UserDevice;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;
import com.elinxer.cloud.library.server.assist.domain.vo.user.UserInfoVo;
import com.elinxer.cloud.library.server.assist.service.IDeviceStatusService;
import com.elinxer.cloud.library.server.assist.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements IUserService {

    @Resource
    UserDeviceDao userDeviceDao;

    @Resource
    UserDao userDao;

    @Resource
    IDeviceStatusService iDeviceStatusService;

    @Override
    public UserInfoVo getUserInfoByUsername(String username) {
        Object userInfo = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).last("limit 1"));
        return BeanConvertUtils.sourceToTarget(userInfo, UserInfoVo.class);
    }

    @Override
    public UserInfoVo getUserInfoByPhone(String phone) {
        Object userInfo = this.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone).last("limit 1"));
        return BeanConvertUtils.sourceToTarget(userInfo, UserInfoVo.class);
    }

    @Override
    public UserInfoVo getUserInfo(Long userId) {
        Object userInfo = this.getOne(new LambdaQueryWrapper<User>().eq(User::getId, userId).last("limit 1"));
        return BeanConvertUtils.sourceToTarget(userInfo, UserInfoVo.class);
    }

    @Override
    public Boolean updateUserInfo(UserInfoVo userInfoVo) {

        User user = new User();

        user.setId(userInfoVo.getId());
        user.setRealName(userInfoVo.getRealName());
        //user.setPhone(userInfoVo.getPhone());
        user.setAddress(userInfoVo.getAddress());
        user.setAvatarUrl(userInfoVo.getAvatarUrl());

        userDao.update(user, new LambdaQueryWrapper<User>().eq(User::getId, userInfoVo.getId()));

        return true;
    }

    @Override
    public boolean saveUser(User user) {
        return this.save(user);
    }

    @Override
    public Boolean checkUserBindDevice(Long userId, String deviceNo) {
        UserDevice userDevice = userDeviceDao.selectOne(new LambdaQueryWrapper<UserDevice>().eq(UserDevice::getUserId, userId)
                .eq(UserDevice::getDeviceNo, deviceNo)
                .last("limit 1"));
        return userDevice != null;
    }

    @Override
    public boolean bindUserDevice(UserInfoVo userInfoVo, String deviceNo) {

        UserDevice userDevice = userDeviceDao.selectOne(new LambdaQueryWrapper<UserDevice>().eq(UserDevice::getUserId, userInfoVo.getId())
                .eq(UserDevice::getDeviceNo, deviceNo)
                .last("limit 1"));

        if (userDevice != null) {
            //throw new AppException("已绑定，不可重复操作.");
        }

        // TODO 检查是否作绑定判重
        // ...

        if (userDevice == null) {
            UserDevice userDevice1 = new UserDevice();
            userDevice1.setUserId(userInfoVo.getId());
            userDevice1.setUsername(userInfoVo.getUsername());
            userDevice1.setPhone(userInfoVo.getPhone());
            userDevice1.setDeviceNo(deviceNo);
            userDevice1.setRealName(userInfoVo.getRealName());
            userDevice1.setStatus(1);
            userDevice1.setActiveAt(DateUtil.now());
            userDeviceDao.insert(userDevice1);
        }

        return true;
    }

    @Override
    public List<DeviceUserVo> getUserDevices(Long userId) {
        List<DeviceUserVo> deviceUserVos = userDeviceDao.getUserDevices(userId);

        for (DeviceUserVo deviceUserVo : deviceUserVos) {
            DeviceStatus deviceStatus = iDeviceStatusService.getStatus(deviceUserVo.getSerialNo());
            deviceUserVo.setState(deviceStatus != null ? deviceStatus.getStatus().getValue() : 0);
            deviceUserVo.setOnlineDate(deviceStatus != null ?
                    DateUtil.format(DateUtil.date(deviceStatus.getTimestamp()), "yyyy-MM-dd HH:mm:ss") : "");
            deviceUserVo.setTaskState(0);
        }

        return deviceUserVos;
    }

}
