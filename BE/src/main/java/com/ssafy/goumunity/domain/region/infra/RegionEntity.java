package com.ssafy.goumunity.domain.region.infra;

import com.ssafy.goumunity.domain.region.domain.Region;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "region")
public class RegionEntity {

    @Id
    @GeneratedValue
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "si")
    private String si;

    @Column(name = "gungu")
    private String gungu;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Region to() {
        return Region.builder()
                .regionId(regionId)
                .si(si)
                .gungu(gungu)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static RegionEntity from(Region region) {

        return RegionEntity.builder()
                .regionId(region.getRegionId())
                .si(region.getSi())
                .gungu(region.getGungu())
                .createdAt(region.getCreatedAt())
                .updatedAt(region.getUpdatedAt())
                .build();
    }

    public static RegionEntity regionEntityOnlyWithId(Long regionId) {
        return RegionEntity.builder().regionId(regionId).build();
    }
}
