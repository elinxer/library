

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
import java.util.ArrayList;
import java.util.List;

@Alias("jsonArrayHandler")
@MappedTypes({List.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.CLOB})
@Slf4j
public class JsonArrayHandler extends BaseTypeHandler<List<Object>> {

    private List<Object> parseArray(String json) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return JSON.parseArray(json);
    }

    @Override
    public List<Object> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseArray(rs.getString(columnIndex));
    }

    @Override
    public List<Object> getResult(ResultSet rs, String columnName) throws SQLException {
        return parseArray(rs.getString(columnName));
    }

    @Override
    public List<Object> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseArray(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter, SerializerFeature.WriteClassName));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, "[]");
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public List<Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new ArrayList<>();
    }
}
