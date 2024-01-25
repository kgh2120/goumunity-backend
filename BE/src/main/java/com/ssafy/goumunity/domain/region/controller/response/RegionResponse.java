package com.ssafy.goumunity.domain.region.controller.response;

import com.ssafy.goumunity.domain.region.domain.Region;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionResponse {
    private Long regionId;
    private String si;
    private String gungu;

    private Long createdAt;
    private Long updatedAt;

    public static RegionResponse from(Region region) {
        return RegionResponse.builder()
                .regionId(region.getRegionId())
                .si(region.getSi())
                .gungu(region.getGungu())
                .createdAt(region.getCreatedAt().getEpochSecond())
                .updatedAt(region.getUpdatedAt().getEpochSecond())
                .build();
    }
}
