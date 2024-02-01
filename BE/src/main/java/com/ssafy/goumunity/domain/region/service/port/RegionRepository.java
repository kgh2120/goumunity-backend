package com.ssafy.goumunity.domain.region.service.port;

import com.ssafy.goumunity.domain.region.controller.request.RegionRequest;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import java.util.List;
import java.util.Optional;

public interface RegionRepository {
    List<Region> findAll();

    Optional<Region> findOneByRegionId(Long regionId);

    void save(RegionEntity regionEntity);

    boolean isExistsRegion(Long id);

    boolean isExistsRegion(RegionRequest regionRequest);
}
