package com.sarpio.shop.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PRODUCT_RESPONSE_CACHE_MANAGER = "productResponseCacheManager";
    public static final String PRODUCT_RESPONSE_CACHE_NAME = "productResponseCache";

    public static final String CUSTOMER_RESPONSE_CACHE_MANAGER = "customerResponseCacheManager";
    public static final String CUSTOMER_RESPONSE_CACHE_NAME = "customerResponseCache";


    @Bean(PRODUCT_RESPONSE_CACHE_MANAGER)
    public CacheManager productCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Collections.singleton(PRODUCT_RESPONSE_CACHE_NAME));
        return cacheManager;
    }

    @Primary
    @Bean(CUSTOMER_RESPONSE_CACHE_MANAGER)
    public CacheManager customerCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Collections.singleton(CUSTOMER_RESPONSE_CACHE_NAME));
        return cacheManager;
    }
}