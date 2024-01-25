package com.ssafy.goumunity.domain.region.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegionJpaRepositoryTest {

    @Autowired
    private RegionJpaRepository jpaRepository;

    @Nested
    class 지역_JPA {
        @Test
        @Transactional
        @DisplayName("전체지역조회_NOTNULL확인_성공")
        void 전체지역조회_테스트_NULL확인() {
        /*
            test data query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:01 통과
         */


            // Null오는지 테스트
            assertNotNull(jpaRepository.findAll());
        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_사이즈확인_성공")
        void 전체지역조회_테스트_사이즈확인() {
        /*
            test data query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:01 통과
         */

            // 사이즈 테스트
            assertEquals(jpaRepository.findAll().size(), 2);
        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_내부데이터테스트_성공")
        void 전체지역조회_내부데이터테스트() {
        /*
            test data query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:01 통과
         */

            // 데이터 테스트
            assertTrue(jpaRepository.findAll().get(0).getSi().equals("서울시"));
            assertTrue(jpaRepository.findAll().get(1).getGungu().equals("서초구"));
        }

        @Test
        @Transactional
        @DisplayName("지역_단건조회_NOTNULL테스트_성공")
        void 지역_ID단건조회_NULL테스트() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:43 통과
         */

            // 데이터 테스트
            assertNotNull(jpaRepository.findOneByRegionId(Long.valueOf(1)));
            assertNotNull(jpaRepository.findOneByRegionId(Long.valueOf(2)));
            assertNotNull(jpaRepository.findOneByRegionId(Integer.toUnsignedLong(3)));
        }

        @Test
        @Transactional
        @DisplayName("지역단건조회_EMPTY테스트_성공")
        void 지역_ID단건조회_EMPTY테스트_성공() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:43 통과
         */

            // 데이터 테스트
            assertNotEquals(jpaRepository.findOneByRegionId(Long.valueOf(1)), Optional.empty());
            assertNotEquals(jpaRepository.findOneByRegionId(Long.valueOf(2)), Optional.empty());

            // Optional.empty() 테스트
            assertEquals(jpaRepository.findOneByRegionId(Integer.toUnsignedLong(3)), Optional.empty());
        }

        @Test
        @Transactional
        @DisplayName("지역단건조회_내부데이터테스트_성공")
        void 지역_ID단건조회_내부데이터테스트() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:43 통과
         */

            // 내부 데이터 테스트
            assertTrue(jpaRepository.findOneByRegionId(Integer.toUnsignedLong(1)).get().getSi().equals("서울시"));
        }
    }
}