package com.ssafy.goumunity.domain.feed.infra.feedimg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedImgJpaRepositoryTest {

    @Autowired
    private FeedImgJpaRepository feedImgJpaRepository;

    @Nested
    class 피드사진테스트_JPA {
        @Test
        @DisplayName("피드사진_NOTNULL검사_성공")
        void 피드사진id별_피드사진단건조회_NULL검사() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:26 통과
         */

            // null 확인
            assertNotNull(feedImgJpaRepository.findOneByFeedImgId(Long.valueOf(1)));
        }

        @Test
        @DisplayName("피드사진_EMPTY검사_성공")
        void 피드사진id별_피드사진단건조회_EMPTY확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:26 통과
         */

            // empty 확인
            assertFalse(feedImgJpaRepository.findOneByFeedImgId(Long.valueOf(1)).isEmpty());
            assertTrue(feedImgJpaRepository.findOneByFeedImgId(Long.valueOf(4)).isEmpty());
        }

        @Test
        @DisplayName("피드사진_내용확인_성공")
        void 피드사진id별_피드사진단건조회_내용확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:26 통과
         */
            // 내용확인
            assertEquals(feedImgJpaRepository.findOneByFeedImgId(Long.valueOf(1)).get().getFeedImgId(), 1);
            assertEquals(feedImgJpaRepository.findOneByFeedImgId(Long.valueOf(3)).get().getImgSrc(), "222222");
            assertEquals(feedImgJpaRepository.findOneByFeedImgId(Long.valueOf(2)).get().getSequence(), 2);
        }

        @Test
        @DisplayName("피드사진_전체_NOTNULL검사_성공")
        void 피드별_피드사진전체조회_NULL확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:29 통과
         */

            // null 확인
            assertNotNull(feedImgJpaRepository.findAllByFeedId(Long.valueOf(2)));
            assertNotNull(feedImgJpaRepository.findAllByFeedId(Long.valueOf(4)));

            // size 확인
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(1)).size(), 2);
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(4)).size(), 0);

            // 내부 데이터 확인
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(1)).get(0).getImgSrc(), "111111");
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(1)).get(1).getSequence(), 2);
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(2)).get(0).getFeedImgId(), 3);
        }

        @Test
        @DisplayName("피드사진_전체_SIZE확인_성공")
        void 피드별_피드사진전체조회_SIZE확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:29 통과
         */

            // size 확인
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(1)).size(), 2);
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(4)).size(), 0);
        }

        @Test
        @DisplayName("피드사진_전체_내부데이터확인_성공")
        void 피드별_피드사진전체조회_내부데이터확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:29 통과
         */

            // 내부 데이터 확인
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(1)).get(0).getImgSrc(), "111111");
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(1)).get(1).getSequence(), 2);
            assertEquals(feedImgJpaRepository.findAllByFeedId(Long.valueOf(2)).get(0).getFeedImgId(), 3);
        }
    }
}