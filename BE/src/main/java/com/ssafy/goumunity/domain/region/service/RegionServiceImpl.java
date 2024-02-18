package com.ssafy.goumunity.domain.region.service;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.goumunity.domain.region.controller.request.RegionRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.exception.RegionErrorCode;
import com.ssafy.goumunity.domain.region.exception.RegionException;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

	private final RegionRepository regionRepository;

	@Cacheable("regions")
	@Override
	public List<RegionResponse> findAll() {
		return regionRepository.findAll().stream().map(RegionResponse::from).toList();
	}

	@Override
	public RegionResponse findOneByRegionId(Long regionId) {
		return RegionResponse.from(
			regionRepository
				.findOneByRegionId(regionId)
				.orElseThrow(() -> new RegionException(RegionErrorCode.NO_REGION_DATA)));
	}

	@Override
	@Transactional
	public void save(RegionRequest regionRequest) {
		if (regionRepository.isExistsRegion(regionRequest)) {
			throw new RegionException(RegionErrorCode.ALREADY_EXISTS);
		} else {
			regionRepository.save(RegionEntity.from(Region.from(regionRequest)));
		}
	}

	@Override
	public boolean isExistsRegion(Long id) {
		return regionRepository.isExistsRegion(id);
	}
}
