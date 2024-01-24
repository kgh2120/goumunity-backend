package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import java.util.List;

public interface RegionService {
    List<RegionResponse> findAll();

    RegionResponse findOneByRegionId(Long regionId);
}
