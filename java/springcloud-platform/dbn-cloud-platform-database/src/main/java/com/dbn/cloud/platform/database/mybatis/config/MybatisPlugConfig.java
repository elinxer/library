package com.dbn.cloud.platform.database.mybatis.config;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.dbn.cloud.platform.common.constant.RedisCacheConst;
import com.dbn.cloud.platform.core.utils.StringUtils;
import com.dbn.cloud.platform.database.mybatis.handler.FieldMetaObjectHandler;
import com.dbn.cloud.platform.database.mybatis.handler.HxTenantLineInnerInterceptor;
import com.dbn.cloud.platform.database.mybatis.properties.DatabaseProperties;
import com.dbn.cloud.platform.security.constant.UaaConstant;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * mybatis-plus配置
 *
 * @author elinx
 * @date 2021-08-16
 */
@Configuration
@Import(FieldMetaObjectHandler.class)
@EnableConfigurationProperties(DatabaseProperties.class)
public class MybatisPlugConfig {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    @Order(0)
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        if (databaseProperties.getTenantEnable()) {
            // COLUMN 模式 多租户插件
            HxTenantLineInnerInterceptor tli = new HxTenantLineInnerInterceptor();
            tli.setTenantLineHandler(new TenantLineHandler() {
                @Override
                public String getTenantIdColumn() {
                    return databaseProperties.getTenantIdColumn();
                }

                @Override
                public boolean ignoreTable(String tableName) {
                    return databaseProperties.getIgnoreTenantTables() != null && databaseProperties.getIgnoreTenantTables().contains(tableName);
                }

                @Override
                public Expression getTenantId() {
                    HttpServletRequest request = null;
                    // 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。
                    if (null != RequestContextHolder.getRequestAttributes()) {
                        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    }
                    String path = request.getContextPath();
                    String header = request.getHeader(UaaConstant.AUTHORIZATION);
                    if (null == header) {
                        return new LongValue(0);
                    }
                    String token = StringUtils.substringAfter(header, OAuth2AccessToken.BEARER_TYPE + " ");
                    String tenantId = "";
                    if (!"company_id".equals(databaseProperties.getTenantIdColumn())) {
                        tenantId = (String) redisTemplate.opsForValue().get(databaseProperties.getTenantIdColumn() + token);
                    } else {
                        tenantId = (String) redisTemplate.opsForValue().get(RedisCacheConst.TENANT_TOKEN_PRE + token);
                    }
                    if (StringUtils.isEmpty(tenantId)) {
                        throw new MybatisPlusException("租户ID为空，请联系管理员！");
                    }
                    return new LongValue(tenantId);
                }
            });
            interceptor.addInnerInterceptor(tli);
        }
        // 分页插件
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 单页分页条数限制
        paginationInterceptor.setMaxLimit(databaseProperties.getMaxLimit());
        // 数据库类型
        paginationInterceptor.setDbType(databaseProperties.getDbType());

        interceptor.addInnerInterceptor(paginationInterceptor);

        //防止全表更新与删除插件
        if (databaseProperties.getIsBlockAttack()) {
            interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        }
        // sql性能规范插件
        if (databaseProperties.getIsIllegalSql()) {
            interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        }
        return interceptor;
    }


}
