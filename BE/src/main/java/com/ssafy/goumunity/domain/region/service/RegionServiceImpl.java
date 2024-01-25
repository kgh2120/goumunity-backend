package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<RegionResponse> findAll() {
        return regionRepository.findAll().stream().map(RegionResponse::from).toList();
    }

    @Override
    public RegionResponse findOneByRegionId(Long regionId) {
        return RegionResponse.from(
                regionRepository
                        .findOneByRegionId(regionId)
                        .orElseThrow(() -> new ResourceNotFoundException("해당 지역을 찾을 수 없습니다.", this)));
    }
}
