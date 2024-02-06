package com.ssafy.goumunity.domain.feed.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.common.config.security.CustomDetails;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.domain.feed.service.FeedScrapService;
import com.ssafy.goumunity.domain.user.domain.Gender;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import com.ssafy.goumunity.domain.user.domain.UserStatus;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {FeedScrapController.class, GlobalExceptionHandler.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class FeedScrapControllerTest {

    @MockBean FeedScrapService feedScrapService;

    @Autowired MockMvc mockMvc;

    private ObjectMapper mapper;

    @Test
    @DisplayName("스크랩테스트")
    void 스크랩테스트() throws Exception {

        MockHttpSession httpSession = new MockHttpSession();

        User user =
                User.builder()
                        .id(Long.valueOf(1))
                        .age(25)
                        .userCategory(UserCategory.EMPLOYEE)
                        .userStatus(UserStatus.ACTIVE)
                        .email("ssafy@ssafy.com")
                        .password("ssafy")
                        .regionId(Long.valueOf(1))
                        .monthBudget(Long.valueOf(1000000))
                        .imgSrc("SRC")
                        .nickname("ssafy")
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .lastPasswordModifiedDate(Instant.now())
                        .gender(Gender.MALE)
                        .build();

        mockMvc
                .perform(
                        post("/api/feeds/1/scrap")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .session(httpSession))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스크랩취소_성공")
    void 스크랩취소() throws Exception {

        MockHttpSession httpSession = new MockHttpSession();

        User user =
                User.builder()
                        .id(Long.valueOf(1))
                        .age(25)
                        .userCategory(UserCategory.EMPLOYEE)
                        .userStatus(UserStatus.ACTIVE)
                        .email("ssafy@ssafy.com")
                        .password("ssafy")
                        .regionId(Long.valueOf(1))
                        .monthBudget(Long.valueOf(1000000))
                        .imgSrc("SRC")
                        .nickname("ssafy")
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .lastPasswordModifiedDate(Instant.now())
                        .gender(Gender.MALE)
                        .build();

        mockMvc
                .perform(
                        delete("/api/feeds/1/unscrap")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .session(httpSession))
                .andExpect(status().isOk());
    }
}
