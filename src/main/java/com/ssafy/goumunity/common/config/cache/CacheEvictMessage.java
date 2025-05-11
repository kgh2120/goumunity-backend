package com.ssafy.goumunity.common.config.cache;

import com.ssafy.goumunity.common.event.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class CacheEvictMessage implements Event {

    private String cacheName;
    private Object key;

    public CacheEvictMessage(String cacheName, Object key) {
        this.cacheName = cacheName;
        this.key = key;
    }

    public static CacheEvictMessage of(String cacheName, Object key){
        return new CacheEvictMessage(cacheName, key);
    }
}
