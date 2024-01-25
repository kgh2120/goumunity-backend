package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Nested
    class 피드_SERVICE {
        @Test
        @Transactional
        @DisplayName("단건지역조회_NOTNULL확인_성공")
        void 단건지역조회_NULL확인() {
        /*
            2024.01.24 15:38 통과
         */

            // null 테스트
            assertNotNull(feedService.findOneByFeedId(Long.valueOf(1)));
        }

        @Test
        @Transactional
        @DisplayName("단건지역조회_예외처리확인_성공")
        void 단건지역조회_예외처리확인() {
        /*
            2024.01.24 15:38 통과
         */

            // 예외처리 테스트
            assertThrows(ResourceNotFoundException.class, () -> feedService.findOneByFeedId(Long.valueOf(4)));

        }

        @Test
        @Transactional
        @DisplayName("단건지역조회_내부데이터확인_성공")
        void 단건지역조회_내부데이터확인() {
        /*
            2024.01.24 15:38 통과
         */

            // 내부 데이터 테스트
            assertEquals(feedService.findOneByFeedId(Long.valueOf(1)).getContent(), "이게 내용이다");
        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_NULL확인_성공")
        void 전체지역조회_NULL확인() {
        /*
            2024.01.24 15:41 통과
         */

            // null체크
            assertNotNull(feedService.findAllByUserId(Long.valueOf(1)));
            assertNotNull(feedService.findAllByUserId(Long.valueOf(0)));
        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_사이즈확인_성공")
        void 전체지역조회_사이즈확인() {
        /*
            2024.01.24 15:41 통과
         */

            // size체크
            assertEquals(feedService.findAllByUserId(Long.valueOf(1)).size(), 3);
            assertEquals(feedService.findAllByUserId(Long.valueOf(5)).size(), 0);

        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_내부데이터확인_성공")
        void 전체지역조회_내부데이터확인() {
        /*
            2024.01.24 15:41 통과
         */

            // 내부 데이터 체크
            assertEquals(feedService.findAllByUserId(Long.valueOf(1)).get(0).getContent(), "이게 내용이다");
        }
    }
}