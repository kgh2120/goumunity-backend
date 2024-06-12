package com.ssafy.goumunity.domain.region.controller.response;

import java.util.List;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionsResponse {

    private List<RegionResponse> regions;

    public static RegionsResponse of(final List<RegionResponse> regions) {
        return builder().regions(regions).build();
    }
}
