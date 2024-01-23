package com.ssafy.goumunity.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import com.ssafy.goumunity.domain.user.dto.UserCreateDto;
import com.ssafy.goumunity.domain.user.service.UserServiceImpl;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import com.ssafy.goumunity.image.Image;
import com.ssafy.goumunity.util.SingleImageHandler;
import java.io.FileInputStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock UserRepository userRepository;

    @Mock SingleImageHandler singleImageHandler;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks UserServiceImpl userService;

    @Test
    void 유저_생성_테스트() throws Exception {

        Clock fixed = Clock.fixed(Instant.now(), ZoneId.of(ZoneId.systemDefault().getId()));

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        // given
        UserCreateDto userCreateDto =
                UserCreateDto.builder()
                        .email("ssafy@naver.com")
                        .password("1q2w3e4r!@Q")
                        .monthBudget(30_0000L)
                        .age(27)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .nickname("청룡이")
                        .regionId(1)
                        .build();

        FileInputStream fileInputStream =
                new FileInputStream("src/main/resources/images/user-profile/20240118/12237787285700.jpg");
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "jpg", fileInputStream);

        given(userRepository.existsByEmail(any())).willReturn(false);
        given(singleImageHandler.parseFileInfo(any()))
                .willReturn(
                        Image.builder().originalFileName("from").storedFilePath("to").fileSize(1L).build());
        //        given()
        given(passwordEncoder.encode(any())).willReturn("1q2w3e4r!@Q");
        given(userRepository.save(any()))
                .willReturn(
                        User.builder()
                                .id(1L)
                                .age(userCreateDto.getAge())
                                .email(userCreateDto.getEmail())
                                .nickname(userCreateDto.getNickname())
                                .password(userCreateDto.getPassword())
                                .regionId(userCreateDto.getRegionId())
                                .monthBudget(userCreateDto.getMonthBudget())
                                .userCategory(userCreateDto.getUserCategory())
                                .createdAt(Instant.now(fixed))
                                .build());

        User sut = userService.saveUser(userCreateDto, image);

        assertAll(
                () -> {
                    assertThat(sut.getId()).isEqualTo(1L);
                    assertThat(sut.getEmail()).isEqualTo(userCreateDto.getEmail());
                    assertThat(sut.getUserCategory()).isEqualTo(userCreateDto.getUserCategory());
                    assertThat(sut.getNickname()).isEqualTo(userCreateDto.getNickname());
                    assertThat(sut.getAge()).isSameAs(userCreateDto.getAge());
                    assertThat(sut.getMonthBudget()).isSameAs(userCreateDto.getMonthBudget());
                    assertThat(sut.getRegionId()).isSameAs(userCreateDto.getRegionId());
                    assertThat(sut.getCreatedAt()).isEqualTo(Instant.now(fixed));
                });
    }
}
