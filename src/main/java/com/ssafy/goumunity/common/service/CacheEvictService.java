package com.ssafy.goumunity.common.service;

import com.ssafy.goumunity.common.config.cache.CacheEvictMessage;

public interface CacheEvictService {

    void publishCacheEvictMessage(CacheEvictMessage cacheEvictMessage);

    void evictCache(CacheEvictMessage cacheEvictmessage);
}
