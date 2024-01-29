package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import java.util.List;

public interface RegionService {
    List<RegionResponse> findAll();

    RegionResponse findOneByRegionId(Long regionId);

    void save(RegionRegistRequest regionRegistRequest);

    boolean isExistsRegion(Long id);
}
