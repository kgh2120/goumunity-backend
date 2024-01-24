package com.ssafy.goumunity.domain.region.infra;

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

    @Test
    @Transactional
    void 전체지역조회_테스트() {
        /*
            test data query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 11:01 통과
         */


        // Null오는지 테스트
        assertNotNull(jpaRepository.findAll());

        // 사이즈 테스트
        assertEquals(jpaRepository.findAll().size(), 2);

        // 데이터 테스트
        assertTrue(jpaRepository.findAll().get(0).getSi().equals("서울시"));
        assertTrue(jpaRepository.findAll().get(1).getGungu().equals("서초구"));
    }

    @Test
    @Transactional
    void 지역_ID단건조회_테스트() {
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

        assertNotEquals(jpaRepository.findOneByRegionId(Long.valueOf(1)), Optional.empty());
        assertNotEquals(jpaRepository.findOneByRegionId(Long.valueOf(2)), Optional.empty());

        // Optional.empty() 테스트
        assertNotNull(jpaRepository.findOneByRegionId(Integer.toUnsignedLong(3)));
        assertEquals(jpaRepository.findOneByRegionId(Integer.toUnsignedLong(3)), Optional.empty());

        // 내부 데이터 테스트
        assertTrue(jpaRepository.findOneByRegionId(Integer.toUnsignedLong(1)).get().getSi().equals("서울시"));
    }
}