package com.fscut.courier.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lxw
 */
@Component
public class RedisConfig {
    //@Bean
    //public CacheManager cacheManager(RedisTemplate redisTemplate) {
    //    RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
    //    rcm.setDefaultExpiration(60);//秒
    //    return rcm;
    //}
}
