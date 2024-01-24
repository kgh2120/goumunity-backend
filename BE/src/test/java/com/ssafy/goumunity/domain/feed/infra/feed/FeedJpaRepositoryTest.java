package com.ssafy.goumunity.domain.feed.infra.feed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedJpaRepositoryTest {

    @Autowired
    private FeedJpaRepository feedJpaRepository;

    @Test
    void findOneByFeedId() {
        /*
            test query

            insert into feed(feed_id, region_id, user_id, content, type, price, after_price, profit)
            values  (1, 1, 1, '이게 내용이다', 1, 20000, 15000, 5000),
                    (2, 1, 1, '이게 내용이다 2222', 1, 30000, 10000, 20000),
                    (3, 1, 1, '이것도 내용이다 33', 1, 5000, 4000, 1000);
            
            2024.01.24 14:51 통과
         */

        // null 확인
        assertNotNull(feedJpaRepository.findOneByFeedId(Long.valueOf(1)));

        // 존재하지 않는 데이터 empty 확인
        assertTrue(feedJpaRepository.findOneByFeedId(Long.valueOf(4)).isEmpty());

        // 존재하는 데이터 empty 확인
        assertFalse(feedJpaRepository.findOneByFeedId(Long.valueOf(2)).isEmpty());

        // 내부 데이터 확인
        assertEquals(feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getFeedId(), 1);
        assertEquals(feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getAfterPrice(), 15000);
        assertEquals(feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getContent(), "이게 내용이다");
    }

    @Test
    void findAllByUserId() {
        /*
            test query

            insert into feed(feed_id, region_id, user_id, content, type, price, after_price, profit)
            values  (1, 1, 1, '이게 내용이다', 1, 20000, 15000, 5000),
                    (2, 1, 1, '이게 내용이다 2222', 1, 30000, 10000, 20000),
                    (3, 1, 1, '이것도 내용이다 33', 1, 5000, 4000, 1000);

            2024.01.24 14:58 통과
         */

        // null 체크
        assertNotNull(feedJpaRepository.findAllByUserId(Long.valueOf(1)));
        assertNotNull(feedJpaRepository.findAllByUserId(Long.valueOf(2)));

        // 길이체크
        assertEquals(feedJpaRepository.findAllByUserId(Long.valueOf(1)).size(), 3);
        assertEquals(feedJpaRepository.findAllByUserId(Long.valueOf(2)).size(), 0);

        // 내부 데이터 체크
        assertEquals(feedJpaRepository.findAllByUserId(Long.valueOf(1)).get(0).getContent(), "이게 내용이다");
    }
}