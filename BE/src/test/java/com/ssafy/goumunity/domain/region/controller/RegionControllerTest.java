package com.ssafy.goumunity.domain.region.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.domain.region.controller.request.RegionRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.service.RegionService;
import com.ssafy.goumunity.domain.region.service.port.RegionRepository;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(
        controllers = {RegionController.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class RegionControllerTest {

    @MockBean private RegionService regionService;

    @MockBean private RegionRepository regionRepository;

    @Autowired private MockMvc mockMvc;

    private ObjectMapper mapper;
    private RegionRequest regionRequest;

    @BeforeEach
    void 매퍼등록및단건등록() throws Exception {
        mapper = new ObjectMapper();

        regionRequest = RegionRequest.builder().si("서울시").gungu("중구").build();

        RegionResponse regionResponse =
                RegionResponse.builder()
                        .regionId(Long.valueOf(1))
                        .si("서울시")
                        .gungu("중구")
                        .createdAt(Instant.now().toEpochMilli())
                        .updatedAt(Instant.now().toEpochMilli())
                        .build();

        List<RegionResponse> regionResponses = new ArrayList<>();
        regionResponses.add(regionResponse);

        BDDMockito.given(regionService.findOneByRegionId(Long.valueOf(1))).willReturn(regionResponse);

        BDDMockito.given(regionService.findAll()).willReturn(regionResponses);

        mockMvc
                .perform(
                        post("/api/regions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(regionRequest))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Nested
    class 등록테스트 {

        @Test
        @DisplayName("정상지역등록_성공_BeforeEach_이전")
        @Deprecated
        void 정상지역등록() throws Exception {

            ResultActions resultActions =
                    mockMvc
                            .perform(get("/api/regions/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(status().isOk());

            RegionResponse result =
                    mapper.readValue(
                            resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                            RegionResponse.class);

            SoftAssertions sa = new SoftAssertions();

            sa.assertThat(result.getRegionId()).isEqualTo(Long.valueOf(1));
            sa.assertThat(result.getSi()).isEqualTo("서울시");
            sa.assertThat(result.getGungu()).isEqualTo("중구");

            sa.assertAll();
        }

        @Test
        @DisplayName("널값등록_성공")
        void NULL등록() throws Exception {

            regionRequest = RegionRequest.builder().si(null).gungu(null).build();

            mockMvc
                    .perform(
                            post("/api/regions")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(regionRequest))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("빈값등록_성공")
        void 빈값등록() throws Exception {

            regionRequest = RegionRequest.builder().si("").gungu("").build();

            mockMvc
                    .perform(
                            post("/api/regions")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(regionRequest))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("공백등록_성공")
        void 공백등록() throws Exception {

            regionRequest = RegionRequest.builder().si(" ").gungu(" ").build();

            mockMvc
                    .perform(
                            post("/api/regions")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(regionRequest))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class 조회테스트 {

        @Test
        @DisplayName("ID별_단건조회_성공")
        void 단건조회_ID별() throws Exception {

            ResultActions resultActions =
                    mockMvc
                            .perform(get("/api/regions/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(status().isOk());

            SoftAssertions sa = new SoftAssertions();

            RegionResponse result =
                    mapper.readValue(
                            resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                            RegionResponse.class);

            sa.assertThat(result.getRegionId()).isEqualTo(Long.valueOf(1));
            sa.assertThat(result.getSi()).isEqualTo("서울시");
            sa.assertThat(result.getGungu()).isEqualTo("중구");

            sa.assertAll();
        }

        @Test
        @DisplayName("전체조회_정상")
        void 전체조회_정상() throws Exception {

            ResultActions resultActions =
                    mockMvc
                            .perform(get("/api/regions").contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(status().isOk());

            List<RegionResponse> result =
                    mapper.readValue(
                            resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                            List.class);

            SoftAssertions sa = new SoftAssertions();

            sa.assertThat(result.size()).isEqualTo(1);

            sa.assertAll();
        }
    }
}
