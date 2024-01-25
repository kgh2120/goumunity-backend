package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedImgServiceImplTest {

    @Autowired
    private FeedImgService feedImgService;

    @Nested
    class 피드사진_SERVICE {
        @Test
        @DisplayName("피드사진단건조회_NOTNULL확인_성공")
        void 피드사진_피드사진ID단건조회_NULL확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:57 통과
         */

            // null 확인
            assertNotNull(feedImgService.findOneByFeedImgId(Long.valueOf(1)));
        }

        @Test
        @DisplayName("피드사진단건조회_예외확인_성공")
        void 피드사진_피드사진ID단건조회_예외확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:57 통과
         */
            // 예외확인
            assertThrows(ResourceNotFoundException.class, () -> feedImgService.findOneByFeedImgId(Long.valueOf(4)));
        }

        @Test
        @DisplayName("피드사진단건조회_내부데이터확인_성공")
        void 피드사진_피드사진ID단건조회_내부데이터확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:57 통과
         */

            // 내부 데이터 확인
            assertEquals(feedImgService.findOneByFeedImgId(Long.valueOf(1)).getImgSrc(), "111111");
            assertEquals(feedImgService.findOneByFeedImgId(Long.valueOf(2)).getImgSrc(), "122222");
            assertEquals(feedImgService.findOneByFeedImgId(Long.valueOf(3)).getImgSrc(), "222222");
        }

        @Test
        @DisplayName("피드사진전체조회_NOTNULL확인_성공")
        void 피드사진_피드ID별전체조회_NULL확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:59 통과
         */

            // null 확인
            assertNotNull(feedImgService.findAllByFeedId(Long.valueOf(1)));
            assertNotNull(feedImgService.findAllByFeedId(Long.valueOf(4)));
        }

        @Test
        @DisplayName("피드사진전체조회_SIZE확인_성공")
        void 피드사진_피드ID별전체조회_SIZE확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:59 통과
         */

            // size 확인
            assertEquals(feedImgService.findAllByFeedId(Long.valueOf(1)).size(), 2);
            assertEquals(feedImgService.findAllByFeedId(Long.valueOf(4)).size(), 0);
        }

        @Test
        @DisplayName("피드사진전체조회_내부데이터확인_성공")
        void 피드사진_피드ID별전체조회_내부데이터확인() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:59 통과
         */
            // size 확인
            assertEquals(feedImgService.findAllByFeedId(Long.valueOf(1)).size(), 2);
            assertEquals(feedImgService.findAllByFeedId(Long.valueOf(4)).size(), 0);
        }
    }
}