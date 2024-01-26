package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.service.port.RegionFindService;
import com.ssafy.goumunity.domain.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegionFindServiceImpl implements RegionFindService {
    private final RegionService regionService;

    @Override
    public boolean isExistsRegion(Long id) {
        return regionService.isExistsRegion(id);
    }
}
