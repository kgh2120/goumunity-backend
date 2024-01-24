package com.ssafy.goumunity.domain.region.infra;

import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionRepository;

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll().stream().map(item -> item.to()).collect(Collectors.toList());
    }

    @Override
    public Optional<Region> findOneByRegionId(Long regionId) {
        return regionRepository.findOneByRegionId(regionId).map(RegionEntity::to);
    }

    @Override
    public Optional<Region> findOneBySiGungu(String si, String gungu) {
        return regionRepository.findOneBySiGungu(si, gungu).map(RegionEntity::to);
    }

    @Override
    public RegionEntity save(RegionEntity regionEntity) {
        return regionRepository.save(regionEntity);
    }
}
