package com.elinxer.cloud.library.server.wechat.domain.dao;

import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.wechat.domain.entity.WechatUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatUserDao extends BaseDao<WechatUser> {


}
