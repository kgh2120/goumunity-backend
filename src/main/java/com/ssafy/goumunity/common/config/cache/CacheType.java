package com.ssafy.goumunity.common.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {
    FEED_RECOMMENDS("recommends"),
    FEED_PAGE_NUMBER("pagenumber"),
    MAX_PAGE_NUMBER("maxpage"),
    REGION("region", 86400),
    REGION_ALL("regions", 86400),
    HASHTAG("hashtag", 3600)
    ;

    private String name;
    private Integer expireAfterWrite;
    private Integer maximumSize;

    CacheType(String name) {
        this.name = name;
        this.expireAfterWrite = ConstConfig.DEFAULT_TTL_SEC;
        this.maximumSize = ConstConfig.DEFAULT_MAX_SIZE;
    }

    CacheType(String name, int ttl) {
        this.name = name;
        this.expireAfterWrite = ttl;
        this.maximumSize = ConstConfig.DEFAULT_MAX_SIZE;
    }

    static class ConstConfig {
        static final Integer DEFAULT_TTL_SEC = 600;
        static final Integer DEFAULT_MAX_SIZE = 10240;
    }
}
