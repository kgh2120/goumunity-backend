package com.ssafy.goumunity.domain.region.infra;

import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegionRepositoryImplTest {

    @Autowired
    private RegionRepository regionRepository;

    @Nested
    class 지역_REPO {
        @Test
        @Transactional
        @DisplayName("전체지역조회_NOTNULL확인_성공")
        void 전체지역조회_NULL테스트() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 통과
         */

            // null테스트
            assertNotNull(regionRepository.findAll());
        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_사이즈테스트_성공")
        void 전체지역조회_사이즈테스트() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 통과
         */
            // 전체조회건수 테스트
            assertEquals(regionRepository.findAll().size(), 2);
        }

        @Test
        @Transactional
        @DisplayName("전체지역조회_내부데이터테스트_성공")
        void 전체지역조회_테스트_내부데이터테스트() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 통과
         */
            // 내부데이터 테스트
            assertEquals(regionRepository.findAll().get(0).getSi(), "서울시");
            assertEquals(regionRepository.findAll().get(1).getGungu(), "서초구");
        }

        @Test
        @Transactional
        @DisplayName("단건지역조회_NOTNULL확인_성공")
        void 단건지역조회_NULL확인() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 통과
         */

            // null 테스트
            assertNotNull(regionRepository.findOneByRegionId(Long.valueOf(1)));

            // 존재하지 않는 건 null 테스트
            assertNotNull(regionRepository.findOneByRegionId(Long.valueOf(3)));
        }

        @Test
        @Transactional
        @DisplayName("단건지역조회_EMPTY확인_성공")
        void 단건지역조회_EMPTY확인() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 통과
         */

            // empty 테스트
            assertNotEquals(regionRepository.findOneByRegionId(Long.valueOf(1)), Optional.empty());

            // 존재하지 않는 건 empty 테스트
            assertEquals(regionRepository.findOneByRegionId(Long.valueOf(3)), Optional.empty());
        }
        @Test
        @Transactional
        @DisplayName("단건지역조회_내부데이터테스트_성공")
        void 단건지역조회_내부데이터테스트() {
        /*
            test query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 통과
         */

            // 내부 데이터 테스트
            assertEquals(regionRepository.findOneByRegionId(Long.valueOf(1)).get().getSi(), "서울시");
        }
        
    }
}