package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.domain.Region;
import java.util.List;

public interface RegionService {
    List<RegionResponse> findAll();

    RegionResponse findOneByRegionId(Long regionId);

    Region save(RegionRegistRequest regionRegistRequest);
}
