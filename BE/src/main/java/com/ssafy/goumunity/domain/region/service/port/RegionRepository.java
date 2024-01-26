package com.ssafy.goumunity.domain.region.service.port;

import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import java.util.List;
import java.util.Optional;

public interface RegionRepository {
    List<Region> findAll();

    Optional<Region> findOneByRegionId(Long regionId);

    Optional<Region> findOneBySiGungu(String si, String gungu);

    RegionEntity save(RegionEntity regionEntity);

    boolean isExistsRegion(Long id);
}
