package com.ssafy.goumunity.domain.feed.controller;

import com.ssafy.goumunity.domain.feed.service.FeedDeleteService;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.event.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedEventHandler {
    private final FeedDeleteService feedDeleteService;
    private final FeedService feedService;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        long start = System.currentTimeMillis();
        // feedService.clearUserFeed(event.getUserId());
        feedDeleteService.clearUsersFeed(event.getUserId());
        log.info("Feed-UserDeletedEvent - cost {}ms", System.currentTimeMillis() - start);
    }
}
