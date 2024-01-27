package com.ssafy.goumunity.domain.region.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.ssafy.goumunity.common.exception.feed.DataExistException;
import com.ssafy.goumunity.common.exception.feed.ResourceNotFoundException;
import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock RegionRepository regionRepository;

    @InjectMocks RegionServiceImpl regionService;

    @Nested
    class 입력테스트 {
        @Test
        @DisplayName("단건입력테스트_성공")
        void 정상입력테스트() {

            RegionRegistRequest regionRegistRequest =
                    RegionRegistRequest.builder().si("서울시").gungu("중구").build();

            RegionEntity savedRegionEntity =
                    RegionEntity.builder()
                            .regionId(Long.valueOf(1))
                            .si("서울시")
                            .gungu("중구")
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();

            BDDMockito.given(regionRepository.save(any())).willReturn(savedRegionEntity);

            BDDMockito.given(regionRepository.isExistsRegion(regionRegistRequest)).willReturn(false);

            Region result = regionService.save(regionRegistRequest);

            SoftAssertions sa = new SoftAssertions();

            sa.assertThat(result.getSi()).isSameAs("서울시");
            sa.assertThat(result.getGungu()).isSameAs("중구");

            sa.assertAll();
        }

        @Test
        @DisplayName("중복입력테스트_성공")
        void 중복입력테스트() {

            RegionRegistRequest regionRegistRequest =
                    RegionRegistRequest.builder().si("서울시").gungu("중구").build();

            BDDMockito.given(regionRepository.isExistsRegion(any(RegionRegistRequest.class)))
                    .willReturn(true);

            assertThrows(DataExistException.class, () -> regionService.save(regionRegistRequest));
        }
    }

    @Nested
    class 조회테스트 {

        @Test
        @DisplayName("지역전량조회_성공")
        void 전부조회() {

            List<Region> three = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                three.add(
                        Region.builder()
                                .regionId(Long.valueOf(1))
                                .si("서울시")
                                .gungu("땡땡군")
                                .createdAt(Instant.now())
                                .updatedAt(Instant.now())
                                .build());
            }

            BDDMockito.given(regionRepository.findAll()).willReturn(three);

            List<RegionResponse> regions = regionService.findAll();

            assertTrue(regions.size() == 3);
        }

        @Test
        @DisplayName("지역단건조회_ID_성공")
        void 단건ID조회() {

            Region region =
                    Region.builder()
                            .regionId(Long.valueOf(1))
                            .si("서울시")
                            .gungu("테스트그만하고싶군")
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();

            BDDMockito.given(regionRepository.findOneByRegionId(any())).willReturn(Optional.of(region));

            RegionResponse regionResponse = regionService.findOneByRegionId(Long.valueOf(1));

            assertEquals(regionResponse.getRegionId(), Long.valueOf(1));
            assertEquals(regionResponse.getSi(), "서울시");
            assertEquals(regionResponse.getGungu(), "테스트그만하고싶군");
        }

        @Test
        @DisplayName("지역단건조회_NULL테스트_성공")
        void 지역단건조회_없는건() {

            BDDMockito.given(regionRepository.findOneByRegionId(any())).willReturn(Optional.empty());

            assertThrows(
                    ResourceNotFoundException.class, () -> regionService.findOneByRegionId(Long.valueOf(1)));
        }
    }

    @Nested
    class 삭제테스트{

        @Test
        @DisplayName("삭제불가테스트_성공")
        void 삭제불가테스트(){

            BDDMockito.given(
                    regionRepository.findOneByRegionId(Long.valueOf(1))
            ).willReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> regionService.deleteOneByRegionId(Long.valueOf(1)));
        }

    }
}
