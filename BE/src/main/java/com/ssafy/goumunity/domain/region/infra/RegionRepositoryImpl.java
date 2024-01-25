package com.ssafy.goumunity.domain.region.infra;

import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionRepository;

    @Override
    public List<RegionEntity> findAll() {
        return regionRepository.findAll();
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
