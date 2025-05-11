package com.ssafy.goumunity.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.cache.CacheEvictMessage;
import com.ssafy.goumunity.common.exception.InternalServerCaughtException;
import com.ssafy.goumunity.common.service.CacheEvictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@RequiredArgsConstructor
public class CacheEventHandler implements MessageListener {

    private final RedisTemplate<String,Object> redisTemplate;
    private final CacheEvictService cacheEvictService;


    @EventListener
    public void handleCacheEvictEvent(CacheEvictMessage cacheEvictMessage) {
        cacheEvictService.publishCacheEvictMessage(cacheEvictMessage);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
        try {
            CacheEvictMessage cacheEvictmessage = new ObjectMapper().readValue(body, CacheEvictMessage.class);
            cacheEvictService.evictCache(cacheEvictmessage);
        } catch (JsonProcessingException e) {
            throw new InternalServerCaughtException(e, this);
        }

    }
}
