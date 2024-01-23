package com.ssafy.goumunity.domain.region.domain;

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
}
