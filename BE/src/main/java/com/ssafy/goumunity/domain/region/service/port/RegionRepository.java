package com.ssafy.goumunity.domain.region.service.port;

import com.ssafy.goumunity.domain.region.domain.Region;
import java.util.List;
import java.util.Optional;

public interface RegionRepository {
    List<Region> findAll();

    Optional<Region> findOneByRegionId(Long regionId);
}
