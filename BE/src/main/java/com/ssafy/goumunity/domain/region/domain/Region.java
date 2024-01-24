package com.ssafy.goumunity.domain.region.domain;

import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
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

    public RegionResponse to() {
        return RegionResponse.builder()
                .regionId(regionId)
                .si(si)
                .gungu(gungu)
                .createdAt(createdAt.getEpochSecond())
                .updatedAt(updatedAt.getEpochSecond())
                .build();
    }

    public static Region from(RegionRegistRequest regionRegistRequest){
        return Region.builder()
                .si(regionRegistRequest.getSi())
                .gungu(regionRegistRequest.getGungu())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
