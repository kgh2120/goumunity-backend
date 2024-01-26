package com.ssafy.goumunity.domain.region.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.service.RegionService;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
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

    @Autowired private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    void 매퍼() {
        mapper = new ObjectMapper();
    }

    @Nested
    class 등록테스트 {}

    @Nested
    class 조회테스트 {

        @Test
        @DisplayName("ID별_단건조회_성공")
        void 단건조회_ID별() throws Exception {

            RegionResponse regionResponse =
                    RegionResponse.builder()
                            .regionId(Long.valueOf(1))
                            .si("서울시")
                            .gungu("중구")
                            .createdAt(Instant.now().toEpochMilli())
                            .updatedAt(Instant.now().toEpochMilli())
                            .build();

            BDDMockito.given(regionService.findOneByRegionId(any())).willReturn(regionResponse);

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
    }
}
