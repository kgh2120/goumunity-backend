package com.ssafy.goumunity.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.config.SecurityConfig;
import com.ssafy.goumunity.common.config.security.CustomDetails;
import com.ssafy.goumunity.common.exception.GlobalErrorCode;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.controller.UserController;
import com.ssafy.goumunity.domain.user.controller.request.PasswordModifyRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserCreateRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserModifyRequest;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import com.ssafy.goumunity.domain.user.service.UserService;
import com.ssafy.goumunity.domain.user.service.VerificationService;
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
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {UserController.class, GlobalExceptionHandler.class},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
        },
        excludeAutoConfiguration = {
            SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class
        })
class UserControllerTest {

    @MockBean private UserService userService;

    @MockBean private VerificationService verificationService;
    @MockBean private FeedService feedService;

    @Autowired private MockMvc mockMvc;

    private static final String USER_CONTROLLER_API_PREFIX = "/api/users";

    @DisplayName("회원 가입 성공")
    @Test
    void 회원가입성공() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserCreateRequest user = userCreateDto();

        MockPart data =
                new MockPart("data", "", mapper.writeValueAsBytes(user), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg".getBytes());

        given(userService.saveUser(any(), any())).willReturn(fromUserCreateDto(user));

        this.mockMvc
                .perform(
                        multipart("/api/users/join")
                                .file(image)
                                .part(data)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

    @DisplayName("회원 가입 실패 중복 이메일")
    @Test
    void 회원가입실패_중복이메일() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserCreateRequest user = userCreateDto();

        MockPart data =
                new MockPart("data", "", mapper.writeValueAsBytes(user), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg".getBytes());

        given(userService.saveUser(any(), any()))
                .willThrow(new UserException(UserErrorCode.EXIST_EMAIL));

        this.mockMvc
                .perform(
                        multipart("/api/users/join")
                                .file(image)
                                .part(data)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isConflict())
                .andDo(print());
        this.mockMvc
                .perform(
                        multipart("/api/users/join")
                                .file(image)
                                .part(data)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @DisplayName("이메일로 회원 조회 성공")
    @Test
    void 이메일로회원조회() throws Exception {
        UserCreateRequest user = userCreateDto();

        given(userService.findUserByEmail(any())).willReturn(fromUserCreateDto(user));

        this.mockMvc
                .perform(get("/api/users/" + user.getEmail()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("이메일로 회원 조회 실패 존재하지 않는 이메일")
    @Test
    void 이메일로회원조회실패_존재하지않는이메일() throws Exception {
        UserCreateRequest user = userCreateDto();

        given(userService.findUserByEmail(any()))
                .willThrow(new UserException(UserErrorCode.EMAIL_NOT_FOUND));

        this.mockMvc
                .perform(get("/api/users/" + user.getEmail()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("내 비밀번호 변경")
    @Test
    void 비밀번호변경성공() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User user = fromUserCreateDto(userCreateDto());
        PasswordModifyRequest dto = PasswordModifyRequest.builder().password("ab214A2!!").build();
        MockHttpSession session = new MockHttpSession();

        given(userService.modifyPassword(user, dto.getPassword())).willReturn(user);

        this.mockMvc
                .perform(
                        put("/api/users/my/password")
                                .with(SecurityMockMvcRequestPostProcessors.user(new CustomDetails(user)))
                                .content(mapper.writeValueAsString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print());
    }

    @DisplayName("비밀번호 변경 실패 - 유효성 검사")
    @Test
    void 비밀번호변경실패_유효성검사() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PasswordModifyRequest dto = PasswordModifyRequest.builder().password("11").build();

        this.mockMvc
                .perform(
                        put("/api/users/my/password")
                                .content(mapper.writeValueAsString(dto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorName").value(GlobalErrorCode.BIND_ERROR.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(GlobalErrorCode.BIND_ERROR.getErrorMessage()))
                .andDo(print());
    }

    @DisplayName("닉네임 중복 검사 성공 중복O")
    @Test
    void 닉네임중복검사성공_중복O() throws Exception {
        String nickname = "해안짬타";
        boolean result = true;
        given(userService.isExistNickname(anyString())).willReturn(result);

        this.mockMvc
                .perform(get("/api/users/nickname/validation?nickname=" + nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exist").value(result))
                .andDo(print());
    }

    @DisplayName("닉네임 중복 검사 성공 중복X")
    @Test
    void 닉네임중복검사성공_중복X() throws Exception {
        String nickname = "해안짬타";
        boolean result = false;
        given(userService.isExistNickname(anyString())).willReturn(result);

        this.mockMvc
                .perform(get("/api/users/nickname/validation?nickname=" + nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exist").value(result))
                .andDo(print());
    }

    @DisplayName("내 정보 조회 성공")
    @Test
    void 내정보조회() throws Exception {
        UserCreateRequest user = userCreateDto();
        MockHttpSession session = new MockHttpSession();

        given(userService.findUserByEmail(any())).willReturn(fromUserCreateDto(user));

        this.mockMvc
                .perform(get("/api/users/my").session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("내 회원정보 수정 성공")
    @Test
    void 내정보수정성공() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserModifyRequest userModifyRequest =
                UserModifyRequest.builder().userCategory(UserCategory.EMPLOYEE).age(100).build();
        User user = fromUserCreateDto(userCreateDto());
        MockHttpSession session = new MockHttpSession();

        given(userService.modifyUser(any(), any())).willReturn(user);

        this.mockMvc
                .perform(
                        put("/api/users/my")
                                .session(session)
                                .content(mapper.writeValueAsString(userModifyRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("내 회원정보 수정 실패_비어있는 요청")
    @Test
    void 내정보수정실패_비어있는요청() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserModifyRequest userModifyRequest = UserModifyRequest.builder().build();
        MockHttpSession session = new MockHttpSession();

        given(userService.modifyUser(any(), any()))
                .willThrow(new UserException(UserErrorCode.NO_INPUT_FOR_MODIFY_USER_INFO));

        this.mockMvc
                .perform(
                        put("/api/users/my")
                                .session(session)
                                .content(mapper.writeValueAsString(userModifyRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.errorName")
                                .value(UserErrorCode.NO_INPUT_FOR_MODIFY_USER_INFO.getErrorName()))
                .andExpect(
                        jsonPath("$.errorMessage")
                                .value(UserErrorCode.NO_INPUT_FOR_MODIFY_USER_INFO.getErrorMessage()))
                .andDo(print());
    }

    @Test
    void 내_거지방_조회_테스트_성공() throws Exception {
        // given
        String api = USER_CONTROLLER_API_PREFIX + "/my/chat-rooms";
        Long time = 100L;
        int page = 0;
        int size = 12;
        given(userService.findMyChatRoom(any(), any(), any()))
                .willReturn(
                        new SliceImpl<>(
                                List.of(
                                        MyChatRoomResponse.builder()
                                                .title("거지방")
                                                .chatRoomId(1L)
                                                .currentUserCount(5)
                                                .build()),
                                PageRequest.of(0, 12),
                                false));
        // when // then
        mockMvc
                .perform(
                        get(api)
                                .queryParam("time", Long.toString(time))
                                .queryParam("page", Integer.toString(page))
                                .queryParam("size", Integer.toString(size)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.hasNext").value(false),
                        jsonPath("$.contents.length()").value(1),
                        jsonPath("$.contents[0].title").value("거지방"),
                        jsonPath("$.contents[0].chatRoomId").value(1L),
                        jsonPath("$.contents[0].currentUserCount").value(5))
                .andDo(print());
    }

    private UserCreateRequest userCreateDto() {
        return UserCreateRequest.builder()
                .email("gyu@naver.com")
                .password("AAbb11!!")
                .monthBudget(100000L)
                .age(20)
                .userCategory(UserCategory.JOB_SEEKER)
                .gender(1)
                .nickname("규준")
                .regionId(1L)
                .build();
    }

    private User fromUserCreateDto(UserCreateRequest dto) {
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
