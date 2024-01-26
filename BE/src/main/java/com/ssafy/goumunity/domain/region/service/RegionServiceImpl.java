package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.common.exception.feed.DataExistException;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
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

    @Override
    public Region save(RegionRegistRequest regionRegistRequest) {
        regionRepository
                .findOneBySiGungu(regionRegistRequest.getSi(), regionRegistRequest.getGungu())
                .orElseThrow(() -> new DataExistException(this));

        return regionRepository.save(RegionEntity.from(Region.from(regionRegistRequest))).to();
    }

    @Override
    public boolean isExistsRegion(Long id) {
        return regionRepository.isExistsRegion(id);
    }
}
