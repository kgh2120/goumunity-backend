package com.ssafy.goumunity.domain.region.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionRegistRequest {
    @NotBlank private String si;
    @NotBlank private String gungu;
}
