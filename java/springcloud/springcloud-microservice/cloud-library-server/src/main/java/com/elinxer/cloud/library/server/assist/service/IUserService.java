package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.elinxer.cloud.library.server.assist.domain.entity.User;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;
import com.elinxer.cloud.library.server.assist.domain.vo.user.UserInfoVo;

import java.util.List;

public interface IUserService extends BaseService<User> {

    UserInfoVo getUserInfoByUsername(String username);

    UserInfoVo getUserInfoByPhone(String phone);

    boolean saveUser(User user);

    boolean bindUserDevice(UserInfoVo userId, String deviceNo);

    List<DeviceUserVo> getUserDevices(Long userId);

    UserInfoVo getUserInfo(Long userId);

    Boolean updateUserInfo(UserInfoVo user);

    Boolean checkUserBindDevice(Long userId, String deviceNo);

}
