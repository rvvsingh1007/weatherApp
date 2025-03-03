package com.example.weatherApp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${weather.cache.ttl.minutes:30}")
    private int CACHE_EXPIRY;
    @Value("${weather.cache.max.size:100}")
    private int MAX_SIZE;
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("weatherCache");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(CACHE_EXPIRY, TimeUnit.MINUTES) // Set TTL of 30 minutes
                .maximumSize(MAX_SIZE)  // Limit cache size (optional)
        );
        return cacheManager;
    }
}
