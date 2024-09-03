

package com.dbn.cloud.platform.database.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.*;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Alias("jsonMapHandler")
@MappedTypes({Map.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.CLOB})
@Slf4j
public class JsonMapHandler extends BaseTypeHandler<Map<String, Object>> {
    private Map<String, Object> parseObject(String json) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return JSON.parseObject(json);
    }

    @Override
    public Map<String, Object> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseObject(rs.getString(columnIndex));
    }

    @Override
    public Map<String, Object> getResult(ResultSet rs, String columnName) throws SQLException {
        return parseObject(rs.getString(columnName));
    }

    @Override
    public Map<String, Object> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseObject(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter, SerializerFeature.WriteClassName));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, "{}");
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new HashMap<>();
    }
}
