package com.ssafy.goumunity.domain.region.controller.response;

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
}
