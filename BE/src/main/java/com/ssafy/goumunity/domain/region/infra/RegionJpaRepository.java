package com.ssafy.goumunity.domain.region.infra;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {

    Optional<RegionEntity> findOneByRegionId(Long regionId);

    boolean existsBySiAndGungu(String si, String gungu);
}
