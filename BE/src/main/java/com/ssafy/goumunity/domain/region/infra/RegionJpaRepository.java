package com.ssafy.goumunity.domain.region.infra;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {
    List<RegionEntity> findAll();

    @Query("select r from RegionEntity r where r.regionId=:regionId")
    Optional<RegionEntity> findOneByRegionId(@Param("regionId") Long regionId);
}
