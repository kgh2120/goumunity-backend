package com.ssafy.goumunity.domain.region.infra;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {
    List<RegionEntity> findAll();
}
