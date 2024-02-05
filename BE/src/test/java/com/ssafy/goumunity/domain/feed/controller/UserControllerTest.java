package com.ssafy.goumunity.domain.feed.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.domain.feed.controller.response.FeedSearchResult;
import com.ssafy.goumunity.domain.feed.controller.response.SavingResult;
import com.ssafy.goumunity.domain.feed.domain.FeedSearchResource;
import com.ssafy.goumunity.domain.feed.domain.SavingResource;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.controller.UserController;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import com.ssafy.goumunity.domain.user.service.UserService;
import com.ssafy.goumunity.domain.user.service.VerificationService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class UserControllerTest {

    @MockBean FeedService feedService;

    @MockBean UserService userService;

    @MockBean VerificationService verificationService;

    @Autowired MockMvc mockMvc;

    private ObjectMapper mapper;

    @Test
    void 유저별_피드조회테스트() throws Exception {

        mapper = new ObjectMapper();

        List<FeedSearchResource> resources = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            resources.add(
                    FeedSearchResource.builder().feedId(Long.valueOf(i)).content(i + " 번쨰 내용").build());
        }

        FeedSearchResult result = FeedSearchResult.from(resources);

        BDDMockito.given(feedService.findAllFeedByUserId(any())).willReturn(result);

        ResultActions resultActions =
                mockMvc.perform(get("/api/users/1/feeds")).andExpect(status().isOk());

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    void 유저별_절약내역테스트() throws Exception {

        mapper = new ObjectMapper();

        List<SavingResource> resources = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            resources.add(
                    SavingResource.from(
                            FeedEntity.builder()
                                    .price(10000 * i)
                                    .afterPrice(5000 * i)
                                    .userEntity(UserEntity.userEntityOnlyWithId(Long.valueOf(1)))
                                    .createdAt(Instant.now())
                                    .build()));
        }

        SavingResult result = SavingResult.from(resources);

        BDDMockito.given(feedService.findAllSavingByUserId(any())).willReturn(result);

        ResultActions resultActions =
                mockMvc.perform(get("/api/users/1/savings")).andExpect(status().isOk());

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
}
