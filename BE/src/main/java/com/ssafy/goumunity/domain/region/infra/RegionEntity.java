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

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
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
        RegionEntityBuilder regionEntityBuilder =
                RegionEntity.builder()
                        .regionId(region.getRegionId())
                        .si(region.getSi())
                        .gungu(region.getGungu());

        if (region.getCreatedAt() != null) regionEntityBuilder.createdAt(region.getCreatedAt());
        if (region.getUpdatedAt() != null) regionEntityBuilder.updatedAt(region.getUpdatedAt());

        return regionEntityBuilder.build();
    }

    public static RegionEntity regionEntityOnlyWithId(Long regionId) {
        return RegionEntity.builder().regionId(regionId).build();
    }
}
