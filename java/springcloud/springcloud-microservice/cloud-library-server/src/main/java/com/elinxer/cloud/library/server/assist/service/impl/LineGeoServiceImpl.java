package com.elinxer.cloud.library.server.assist.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbn.cloud.platform.cache.redis.CacheService;
import com.dbn.cloud.platform.database.mybatis.impl.BaseServiceImpl;
import com.elinxer.cloud.library.server.assist.config.AssistCnfValues;
import com.elinxer.cloud.library.server.assist.domain.dao.AblineDao;
import com.elinxer.cloud.library.server.assist.domain.entity.Abline;
import com.elinxer.cloud.library.server.assist.domain.vo.GeoLineVo;
import com.elinxer.cloud.library.server.assist.domain.vo.SearchLineReqVo;
import com.elinxer.cloud.library.server.assist.service.ILineGeoService;
import com.elinxer.cloud.library.server.utils.GeoHashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LineGeoServiceImpl extends BaseServiceImpl<AblineDao, Abline> implements ILineGeoService {

    @Resource
    private CacheService cacheService;

    @Resource
    private AssistCnfValues cnfValues;

    private static final String lineCacheKey = "assist:line_points";

    @Override
    public String add(String location) {
        String name = GeoHashUtil.makeHash(location);
        String[] pointStr = location.split(",");
        Point point = new Point(Double.parseDouble(pointStr[0]), Double.parseDouble(pointStr[1]));
        cacheService.getSrt().opsForGeo().add(lineCacheKey, point, name);
        List<Point> pointList = cacheService.getSrt().opsForGeo().position(lineCacheKey, name);
        if (pointList == null) {
            return location;
        }
        String l = pointList.get(0).getX() + "," + pointList.get(0).getY();
        cacheService.getSrt().opsForGeo().remove(lineCacheKey, name);
        cacheService.getSrt().opsForGeo().add(lineCacheKey, pointList.get(0), GeoHashUtil.makeHash(l));
        return l;
    }

    @Override
    public List<GeoLineVo> search(SearchLineReqVo req) {

        Double distance = req.getDistance();
        String[] pointStr = req.getLocation().split(",");

        if (distance == null) {
            distance = cnfValues.getGeoDistance();
        }

        log.info("=====>{}", distance);

        Point point = new Point(Double.parseDouble(pointStr[0]), Double.parseDouble(pointStr[1]));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance().sortAscending();

        GeoResults<RedisGeoCommands.GeoLocation<String>> list = cacheService.getSrt().opsForGeo()
                .radius(lineCacheKey, new Circle(point.getX(), point.getY(), distance), args);


        if (list == null || list.getContent().size() == 0) {
            return null;
        }

        List<String> geoHashList = new ArrayList<>();
        Map<String, Double> disMap = new HashMap<>();
        list.forEach(item -> {
            String geoHash = GeoHashUtil.makeHash(item.getContent().getPoint().getX(), item.getContent().getPoint().getY());
            geoHashList.add(geoHash);
            disMap.put(geoHash, item.getDistance().getValue());
            log.info("===> name: {} => dis: {}, hash: {}", item.getContent().getName(), item.getDistance(), geoHash);
        });
        if (geoHashList.size() == 0) {
            return null;
        }
        QueryWrapper<Abline> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("geo_hash", geoHashList);
        List<Abline> lineVos = this.list(queryWrapper);

        if (lineVos == null || lineVos.size() == 0) {
            return null;
        }
        List<GeoLineVo> geoLineVos = new ArrayList<>();

        lineVos.forEach(item -> {
            GeoLineVo geoLineVo = GeoLineVo.builder()
                    .name(item.getName())
                    .desc(item.getDesc())
                    .params(item.getParams())
                    .point(item.getPoint())
                    .width(item.getWidth())
                    .createTime(item.getCreateTime())
                    .build();
            geoLineVo.setDistance(disMap.get(item.getGeoHash()));
            geoLineVos.add(geoLineVo);
        });

        return geoLineVos;
    }

}
