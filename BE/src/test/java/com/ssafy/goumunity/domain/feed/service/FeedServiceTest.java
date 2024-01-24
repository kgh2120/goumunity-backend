package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Test
    @Transactional
    void findOneByFeedId() {
        /*
            2024.01.24 15:38 통과
         */

        // null 테스트
        assertNotNull(feedService.findOneByFeedId(Long.valueOf(1)));

        // 예외처리 테스트
        assertThrows(ResourceNotFoundException.class, () -> feedService.findOneByFeedId(Long.valueOf(4)));

        // 내부 데이터 테스트
        assertEquals(feedService.findOneByFeedId(Long.valueOf(1)).getContent(), "이게 내용이다");
    }

    @Test
    @Transactional
    void findAllByUserId() {
        /*
            2024.01.24 15:41 통과
         */

        // null체크
        assertNotNull(feedService.findAllByUserId(Long.valueOf(1)));
        assertNotNull(feedService.findAllByUserId(Long.valueOf(0)));

        // size체크
        assertEquals(feedService.findAllByUserId(Long.valueOf(1)).size(), 3);
        assertEquals(feedService.findAllByUserId(Long.valueOf(5)).size(), 0);

        // 내부 데이터 체크
        assertEquals(feedService.findAllByUserId(Long.valueOf(1)).get(0).getContent(), "이게 내용이다");
    }
}