package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.feed.service.post.FeedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedRepositoryImplTest {

    @Autowired
    private FeedJpaRepository feedJpaRepository;

    @Test
    @Transactional
    void findOneByFeedId() {
        /*
            test query

            insert into feed(feed_id, region_id, user_id, content, type, price, after_price, profit)
            values  (1, 1, 1, '이게 내용이다', 1, 20000, 15000, 5000),
                    (2, 1, 1, '이게 내용이다 2222', 1, 30000, 10000, 20000),
                    (3, 1, 1, '이것도 내용이다 33', 1, 5000, 4000, 1000);

            2024.01.24 15:06 통과
         */
        
        // null 테스트
        assertNotNull(feedJpaRepository.findOneByFeedId(Long.valueOf(1)));
        assertNotNull(feedJpaRepository.findOneByFeedId(Long.valueOf(4)));
        
        // empty 테스트
        assertFalse(feedJpaRepository.findOneByFeedId(Long.valueOf(1)).isEmpty());
        assertTrue(feedJpaRepository.findOneByFeedId(Long.valueOf(4)).isEmpty());
        
        // 내부 데이터 테스트
        assertEquals(feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getContent(), "이게 내용이다");

        System.out.println("결과 : " + feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getRegionEntity().getRegionId());
        System.out.println("결과 : " + feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getRegionEntity().getSi());
        System.out.println("결과 : " + feedJpaRepository.findOneByFeedId(Long.valueOf(1)).get().getRegionEntity().getGungu());
    }

    @Test
    void findAllByUserId() {
        /*
            test query

            insert into feed(feed_id, region_id, user_id, content, type, price, after_price, profit)
            values  (1, 1, 1, '이게 내용이다', 1, 20000, 15000, 5000),
                    (2, 1, 1, '이게 내용이다 2222', 1, 30000, 10000, 20000),
                    (3, 1, 1, '이것도 내용이다 33', 1, 5000, 4000, 1000);

            2024.01.24 15:08 통과
         */
        
        // null체크
        assertNotNull(feedJpaRepository.findAllByUserId(Long.valueOf(1)));
        assertNotNull(feedJpaRepository.findAllByUserId(Long.valueOf(4)));
        
        // list길이체크
        assertEquals(feedJpaRepository.findAllByUserId(Long.valueOf(1)).size(), 3);
        assertEquals(feedJpaRepository.findAllByUserId(Long.valueOf(4)).size(), 0);
        
        // 내부 데이터 체크
        assertEquals(feedJpaRepository.findAllByUserId(Long.valueOf(1)).get(0).getContent(), "이게 내용이다");
    }
}