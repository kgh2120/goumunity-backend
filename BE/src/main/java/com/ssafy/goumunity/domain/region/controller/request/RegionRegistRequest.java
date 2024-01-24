package com.ssafy.goumunity.domain.region.controller.request;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionRegistRequest {
    private String si;
    private String gungu;
}
