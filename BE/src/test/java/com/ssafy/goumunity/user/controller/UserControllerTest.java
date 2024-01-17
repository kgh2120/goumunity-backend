package com.ssafy.goumunity.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.GlobalExceptionHandler;
import com.ssafy.goumunity.config.SecurityConfig;
import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.domain.UserCategory;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import com.ssafy.goumunity.user.service.UserService;
import com.ssafy.goumunity.user.service.VertificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class, GlobalExceptionHandler.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                OAuth2ResourceServerAutoConfiguration.class}
)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private VertificationService vertificationService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원 가입 성공")
    @Test
    void 회원가입성공() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UserCreateDto user = userCreateDto();
        FileInputStream fileInputStream = fileInputStream();

        MockPart data = new MockPart("data", "", mapper.writeValueAsBytes(user), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "jpg", fileInputStream);

        given(userService.saveUser(any(),any())).willReturn(
                fromUserCreateDto(user)
        );

        this.mockMvc.perform(multipart("/api/users/join")
                .file(image).part(data).contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isCreated());
    }

    @DisplayName("회원 가입 실패 중복 이메일")
    @Test
    void 회원가입실패_중복이메일() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UserCreateDto user = userCreateDto();
        FileInputStream fileInputStream = fileInputStream();

        MockPart data = new MockPart("data", "", mapper.writeValueAsBytes(user), MediaType.APPLICATION_JSON);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "jpg", fileInputStream);

        given(userService.saveUser(any(),any())).willThrow(
                new CustomException(CustomErrorCode.EXIST_EMAIL)
        );

        this.mockMvc.perform(multipart("/api/users/join")
                .file(image).part(data).contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("이메일로 회원 조회 성공")
    @Test
    void 이메일로회원조회() throws Exception{
        UserCreateDto user = userCreateDto();

        given(userService.findUserByEmail(any())).willReturn(
                fromUserCreateDto(user)
        );

        this.mockMvc.perform(get("/api/users/" + user.getEmail()))
                .andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("이메일로 회원 조회 실패 존재하지 않는 이메일")
    @Test
    void 이메일로회원조회실패_존재하지않는이메일() throws Exception{
        UserCreateDto user = userCreateDto();

        given(userService.findUserByEmail(any())).willThrow(
                new CustomException(CustomErrorCode.EMAIL_NOT_FOUND)
        );

        this.mockMvc.perform(get("/api/users/" + user.getEmail()))
                .andExpect(status().isNotFound()).andDo(print());
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

    private User fromUserCreateDto(UserCreateDto dto){
        return User.builder()
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

    private FileInputStream fileInputStream() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/images/user-profile/20240116/24706676499700.jpg");
    }
}