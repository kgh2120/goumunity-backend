package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.domain.feed.service.post.FeedImgRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedImgRepositoryImplTest {

    @Autowired
    private FeedImgRepository feedImgRepository;

    @Test
    void 피드ID별_피드사진단건조회() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:33 통과
         */

        // null 확인
        assertNotNull(feedImgRepository.findOneByFeedImgId(Long.valueOf(1)));
        assertNotNull(feedImgRepository.findOneByFeedImgId(Long.valueOf(4)));

        // empty 확인
        assertFalse(feedImgRepository.findOneByFeedImgId(Long.valueOf(1)).isEmpty());
        assertTrue(feedImgRepository.findOneByFeedImgId(Long.valueOf(4)).isEmpty());

        // 내부데이터 확인
        assertEquals(feedImgRepository.findOneByFeedImgId(Long.valueOf(1)).get().getImgSrc(), "111111");
        assertEquals(feedImgRepository.findOneByFeedImgId(Long.valueOf(3)).get().getSequence(), 1);
        assertEquals(feedImgRepository.findOneByFeedImgId(Long.valueOf(2)).get().getSequence(), 2);
    }

    @Test
    void 피드별_피드사진전체조회() {
        /*
            test query

            insert into feed_img(feed_img_id, feed_id, img_src, sequence)
            values(1, 1, '111111', 1),
            (2, 1, '122222', 2),
            (3, 2, '222222', 1);

            2024.01.24 16:35 통과
         */

        // null 확인
        assertNotNull(feedImgRepository.findAllByFeedId(Long.valueOf(1)));
        assertNotNull(feedImgRepository.findAllByFeedId(Long.valueOf(4)));

        // size 확인
        assertEquals(feedImgRepository.findAllByFeedId(Long.valueOf(1)).size(), 2);
        assertEquals(feedImgRepository.findAllByFeedId(Long.valueOf(4)).size(), 0);

        // 내부데이터 확인
        assertEquals(feedImgRepository.findAllByFeedId(Long.valueOf(1)).get(0).getSequence(), 1);
        assertEquals(feedImgRepository.findAllByFeedId(Long.valueOf(1)).get(1).getImgSrc(), "122222");
        assertEquals(feedImgRepository.findAllByFeedId(Long.valueOf(2)).get(0).getImgSrc(), "222222");
    }
}