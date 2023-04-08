package com.elinxer.cloud.library.server.assist.service;

import com.dbn.cloud.platform.database.mybatis.impl.BaseService;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.vo.GeoLineVo;
import com.elinxer.cloud.library.server.assist.domain.vo.SearchLineReqVo;

import java.util.List;

public interface ILineGeoService extends BaseService<Abline> {

    public String add(String location);

    public List<GeoLineVo> search(SearchLineReqVo req);

}
