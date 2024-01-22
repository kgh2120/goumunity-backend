package com.ssafy.goumunity.domain.region.service.port;

import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import java.util.List;

public interface RegionRepository {
    List<RegionEntity> findAll();
}
