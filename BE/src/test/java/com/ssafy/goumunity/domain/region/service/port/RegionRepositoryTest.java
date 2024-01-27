package com.ssafy.goumunity.domain.region.service.port;

import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.region.infra.RegionJpaRepository;
import com.ssafy.goumunity.domain.region.infra.RegionRepositoryImpl;
import java.time.Instant;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegionRepositoryTest {

    @Mock RegionJpaRepository regionJpaRepository;

    @InjectMocks RegionRepositoryImpl regionRepository;

    @Nested
    class 입력테스트 {

        @Test
        @DisplayName("단건정상입력테스트_성공")
        void 정상입력테스트() {

            RegionEntity regionEntity =
                    RegionEntity.builder()
                            .regionId(Long.valueOf(100))
                            .si("서울시")
                            .gungu("중구")
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();

            BDDMockito.given(regionJpaRepository.save(regionEntity)).willReturn(regionEntity);

            RegionEntity result = regionRepository.save(regionEntity);

            SoftAssertions sa = new SoftAssertions();

            sa.assertThat(result.getRegionId()).isEqualTo(100);
            sa.assertThat(result.getSi()).isSameAs("서울시");
            sa.assertThat(result.getGungu()).isSameAs("중구");

            sa.assertAll();
        }
    }
}
