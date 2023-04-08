package com.elinxer.cloud.library.server.wechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.wechat.domain.dao.WechatUserDao;
import com.elinxer.cloud.library.server.wechat.domain.entity.WechatUser;
import com.elinxer.cloud.library.server.wechat.service.IWechatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WechatUserService extends BaseServiceImpl<WechatUserDao, WechatUser> implements IWechatUserService {

    @Override
    public void saveUser(WechatUser user) {
        this.save(user);
    }

    @Override
    public WechatUser getUserByOpenId(String openid) {
        List<WechatUser> list = this.list(new LambdaQueryWrapper<WechatUser>().eq(WechatUser::getOpenId, openid).last("limit 1"));
        if (list != null && list.size() >= 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateUser(WechatUser user) {
        this.update(user, new LambdaQueryWrapper<WechatUser>().eq(WechatUser::getOpenId, user.getOpenId()));
    }

}
