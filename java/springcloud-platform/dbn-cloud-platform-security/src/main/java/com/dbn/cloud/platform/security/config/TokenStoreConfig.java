package com.dbn.cloud.platform.security.config;


import com.dbn.cloud.platform.security.token.RedisTemplateTokenStore;
import com.dbn.cloud.platform.security.token.ResJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Assert;

/**
 * TokenStore token存储
 *
 * @author elinx
 * @version 1.0.0
 */
@Configuration
public class TokenStoreConfig {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "security.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    public RedisTemplateTokenStore redisTokenStore(RedisConnectionFactory connectionFactory) {
        Assert.state(connectionFactory != null, "connectionFactory must be provided");
        RedisTemplateTokenStore redisTemplateStore = new RedisTemplateTokenStore(connectionFactory);
        return redisTemplateStore;
    }


	/*@Bean
	@Primary
	@ConditionalOnProperty(prefix="security.oauth2.token.store",name="type" ,havingValue="redis" ,matchIfMissing=true)
	public RedisTokenStore redisTokenStore(RedisConnectionFactory connectionFactory){
		Assert.state(connectionFactory != null, "connectionFactory must be provided");
		RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory)  ;
		return redisTokenStore ;
	} */

    //使用jwt替换原有的uuid生成token方式
    @Configuration
    @ConditionalOnProperty(prefix = "security.oauth2.token.store", name = "type", havingValue = "jwt", matchIfMissing = false)
    public static class JWTTokenConfig {
        @Bean
        public JwtTokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new ResJwtAccessTokenConverter();
            accessTokenConverter.setSigningKey("dbn");
            return accessTokenConverter;
        }
    }

}
