package com.ssafy.goumunity.domain.region.infra;

import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionRepository;

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll().stream().map(RegionEntity::to).toList();
    }

    @Override
    public Optional<Region> findOneByRegionId(Long regionId) {
        return regionRepository.findOneByRegionId(regionId).map(RegionEntity::to);
    }
}
