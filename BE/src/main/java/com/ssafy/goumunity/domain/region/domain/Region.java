package com.ssafy.goumunity.domain.region.domain;

import com.ssafy.goumunity.domain.region.controller.request.RegionRequest;
import java.time.Instant;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Region {
    private Long regionId;
    private String si;
    private String gungu;

    private Instant createdAt;
    private Instant updatedAt;

    public static Region from(RegionRequest regionRequest) {
        return Region.builder()
                .si(regionRequest.getSi())
                .gungu(regionRequest.getGungu())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
