package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedImgServiceImplTest {

    @Autowired
    private FeedImgService feedImgService;

    @Test
    void 피드사진_피드사진ID단건조회() {
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

        // 예외확인
        assertThrows(ResourceNotFoundException.class, () -> feedImgService.findOneByFeedImgId(Long.valueOf(4)));

        // 내부 데이터 확인
        assertEquals(feedImgService.findOneByFeedImgId(Long.valueOf(1)).getImgSrc(), "111111");
        assertEquals(feedImgService.findOneByFeedImgId(Long.valueOf(2)).getImgSrc(), "122222");
        assertEquals(feedImgService.findOneByFeedImgId(Long.valueOf(3)).getImgSrc(), "222222");
    }

    @Test
    void 피드사진_피드ID별전체조회() {
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

        // size 확인
        assertEquals(feedImgService.findAllByFeedId(Long.valueOf(1)).size(), 2);
        assertEquals(feedImgService.findAllByFeedId(Long.valueOf(4)).size(), 0);

        // 내부데이터 확인
        assertEquals(feedImgService.findAllByFeedId(Long.valueOf(1)).get(0).getImgSrc(), "111111");
        assertEquals(feedImgService.findAllByFeedId(Long.valueOf(1)).get(1).getImgSrc(), "122222");
        assertEquals(feedImgService.findAllByFeedId(Long.valueOf(2)).get(0).getImgSrc(), "222222");
    }
}