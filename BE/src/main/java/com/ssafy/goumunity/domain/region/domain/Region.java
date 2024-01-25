package com.ssafy.goumunity.domain.region.domain;

import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import java.time.Instant;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Region {
    private Long regionId;
    private String si;
    private String gungu;

    private Instant createdAt;
    private Instant updatedAt;

    public static Region from(RegionRegistRequest regionRegistRequest) {
        return Region.builder()
                .si(regionRegistRequest.getSi())
                .gungu(regionRegistRequest.getGungu())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
