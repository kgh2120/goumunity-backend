package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegionServiceImplTest {

    @Autowired
    private RegionService regionService;
    
    @Test
    void findAll() {
        /*
            test data query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 12:07 통과
         */
        
        // null 테스트
        assertNotNull(regionService.findAll());
        
        // 사이즈 테스트
        assertEquals(regionService.findAll().size(), 2);
        
        // 내부데이터 테스트
        assertEquals(regionService.findAll().get(0).getSi(), "서울시");
        assertEquals(regionService.findAll().get(1).getGungu(), "서초구");
    }

    @Test
    void findOneByRegionId() {
        /*
            test data query

            insert into region(region_id, si, gungu) value (1, '서울시', '관악구');
            insert into region(region_id, si, gungu) value (2, '서울시', '서초구');
            select * from region;

            2024.01.24 12.16 통과
            
            예외테스트 2024.01.24 12.38 통과
         */
        
        // 존재하는 데이터 null테스트
        assertNotNull(regionService.findOneByRegionId(Long.valueOf(1)));

        // 존재하지 않는 데이터 예외테스트
        assertThrows(ResourceNotFoundException.class, () -> regionService.findOneByRegionId(Long.valueOf(3)));
        
        // 존재하는 데이터 empty 테스트
        assertNotEquals(regionService.findOneByRegionId(Long.valueOf(1)), Optional.empty());
        
        // 내부 데이터 테스트
        assertEquals(regionService.findOneByRegionId(Long.valueOf(1)).getSi(), "서울시");
        assertEquals(regionService.findOneByRegionId(Long.valueOf(2)).getGungu(), "서초구");
    }
}