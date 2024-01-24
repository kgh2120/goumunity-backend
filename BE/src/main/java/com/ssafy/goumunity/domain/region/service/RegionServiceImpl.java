package com.ssafy.goumunity.domain.region.service;

import com.ssafy.goumunity.common.exception.feed.DataExistException;
import com.ssafy.goumunity.common.exception.feed.ParameterEmptyException;
import com.ssafy.goumunity.common.exception.feed.RegionNotValidatedException;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

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

    @Override
    public Region save(RegionRegistRequest regionRegistRequest) {
        if(isEmpty(regionRegistRequest.getSi()) || isEmpty(regionRegistRequest.getGungu())){
            throw new ParameterEmptyException(this);
        }

        Optional<Region> region = regionRepository.findOneBySiGungu(regionRegistRequest.getSi(), regionRegistRequest.getGungu());

        if(!region.isEmpty()){
            throw new DataExistException(this);
        }

        return regionRepository.save(RegionEntity.from(Region.from(regionRegistRequest))).to();
    }

    public boolean isEmpty(String value){
        if(value == null || value.isEmpty() || value.length() == 0){
            return true;
        } else return false;
    }
}
