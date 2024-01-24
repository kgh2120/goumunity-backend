package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<RegionResponse> findAll() {
        return regionRepository.findAll().stream().map(item -> item.to()).collect(Collectors.toList());
    }

    @Override
    public RegionResponse findOneByRegionId(Long regionId) {
        Optional<Region> region = regionRepository.findOneByRegionId(regionId);
        if (region.isEmpty()) {
            throw new ResourceNotFoundException("해당 지역을 찾을 수 없습니다.", this);
        } else {
            return region.get().to();
        }
    }
}
