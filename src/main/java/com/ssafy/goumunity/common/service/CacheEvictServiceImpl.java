package com.ssafy.goumunity.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.cache.CacheEvictMessage;
import com.ssafy.goumunity.common.exception.InternalServerCaughtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
public class CacheEvictServiceImpl implements CacheEvictService {

    private final RedisTemplate<String,Object> redisTemplate;
    private final CacheManager cacheManager;

    @Override
    public void publishCacheEvictMessage(CacheEvictMessage cacheEvictMessage) {
        try {
            String json = new ObjectMapper().writeValueAsString(cacheEvictMessage);
            redisTemplate.convertAndSend("cache-sync", json);
        } catch (JsonProcessingException e) {
            throw new InternalServerCaughtException(e, this);
        }
    }

    @Override
    public void evictCache(CacheEvictMessage cacheEvictMessage) {
        Cache cache = cacheManager.getCache(cacheEvictMessage.getCacheName());
        if(cache == null)
            return;

        cache.evict(cacheEvictMessage.getKey());
    }
}
