package com.ssafy.goumunity.domain.region.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionRegistRequest {
    @NotBlank(message = "시 데이터가 올바르지 않습니다.")
    private String si;

    @NotBlank(message = "군구 데이터가 올바르지 않습니다.")
    private String gungu;
}
