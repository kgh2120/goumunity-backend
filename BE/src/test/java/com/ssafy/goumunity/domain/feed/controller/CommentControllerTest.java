package com.ssafy.goumunity.domain.feed.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.config.security.CustomDetails;
import com.ssafy.goumunity.domain.feed.controller.request.CommentRequest;
import com.ssafy.goumunity.domain.feed.controller.response.CommentResponse;
import com.ssafy.goumunity.domain.feed.domain.Comment;
import com.ssafy.goumunity.domain.feed.service.CommentService;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import com.ssafy.goumunity.domain.user.dto.UserCreateDto;
import com.ssafy.goumunity.domain.user.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {CommentController.class, GlobalExceptionHandler.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class CommentControllerTest {

    @MockBean private CommentService commentService;

    @Autowired private MockMvc mockMvc;

    @DisplayName("댓글 작성 성공")
    @Test
    void saveComment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user = fromUserCreateDto(userCreateDto());
        Long feedId = 1L;
        CommentRequest.Create comment =
                CommentRequest.Create.builder().content("댓글 테스트 입니다 아아 해안짬타 퇴화론").build();

        given(commentService.saveComment(any(), any(), any()))
                .willReturn(Comment.from(user.getId(), feedId, comment));

        this.mockMvc
                .perform(
                        post("/api/feeds/" + feedId + "/comments")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(comment))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("댓글 작성 실패 - 댓글 내용이 비었을 때")
    @Test
    void saveCommentFailWithEmptyContent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user = fromUserCreateDto(userCreateDto());
        Long feedId = 1L;
        CommentRequest.Create comment = CommentRequest.Create.builder().build();

        given(commentService.saveComment(any(), any(), any()))
                .willThrow(MethodArgumentNotValidException.class);

        this.mockMvc
                .perform(
                        post("/api/feeds/" + feedId + "/comments")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(comment))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("댓글 작성 실패 - 댓글 내용이 200자 초과했을때")
    @Test
    void saveCommentFailWithLongContent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpSession session = new MockHttpSession();
        User user = fromUserCreateDto(userCreateDto());
        Long feedId = 1L;

        String content =
                "동해물과 백두산이 마르고 닳도록\n"
                        + "하느님이 보우하사 우리나라 만세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세"
                        + "남산 위에 저 소나무 철갑을 두른 듯\n"
                        + "바람 서리 불변함은 우리 기상일세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세"
                        + "가을 하늘 공활한데 높고 구름 없이\n"
                        + "밝은 달은 우리 가슴 일편단심일세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세"
                        + "이 기상과 이 맘으로 충성을 다하여\n"
                        + "괴로우나 즐거우나 나라 사랑하세\n"
                        + "무궁화 삼천리 화려 강산\n"
                        + "대한 사람 대한으로 길이 보전하세";

        CommentRequest.Create comment = CommentRequest.Create.builder().content(content).build();

        given(commentService.saveComment(any(), any(), any()))
                .willThrow(MethodArgumentNotValidException.class);

        this.mockMvc
                .perform(
                        post("/api/feeds/" + feedId + "/comments")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(comment))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("어떤 게시글의 댓글 조회")
    @Test
    void findAllComments() throws Exception {
        User user = fromUserCreateDto(userCreateDto());
        Long feedId = 1L;
        int page = 1;
        int size = 10;
        List<CommentResponse> commentResponseList = new ArrayList<>();
        commentResponseList.add(
                CommentResponse.builder()
                        .commentId(1L)
                        .feedId(1L)
                        .content("석주윤")
                        .user(UserResponse.from(user))
                        .build());
        Pageable pageable = PageRequest.of(page, size);
        Slice<CommentResponse> res = new SliceImpl<>(commentResponseList, pageable, true);

        given(commentService.findAllByFeedId(any(), any())).willReturn(res);

        this.mockMvc
                .perform(
                        get("/api/feeds/" + feedId + "/comments")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private UserCreateDto userCreateDto() {
        return UserCreateDto.builder()
                .email("gyu@naver.com")
                .password("AAbb11!!")
                .monthBudget(100000L)
                .age(20)
                .userCategory(UserCategory.JOB_SEEKER)
                .gender(1)
                .nickname("규준")
                .regionId(1)
                .build();
    }

    private User fromUserCreateDto(UserCreateDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .monthBudget(dto.getMonthBudget())
                .age(dto.getAge())
                .userCategory(dto.getUserCategory())
                .gender(dto.getGender())
                .nickname(dto.getNickname())
                .regionId(dto.getRegionId())
                .build();
    }
}
