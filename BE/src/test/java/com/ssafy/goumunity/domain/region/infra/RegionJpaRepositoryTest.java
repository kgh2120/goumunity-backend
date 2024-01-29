package com.ssafy.goumunity.domain.region.infra;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RegionJpaRepositoryTest {

    @Autowired RegionJpaRepository regionJpaRepository;

    @Test
    void t() {
        System.out.println(regionJpaRepository.existsBySiAndGungu("si", "gungu"));
    }
}
