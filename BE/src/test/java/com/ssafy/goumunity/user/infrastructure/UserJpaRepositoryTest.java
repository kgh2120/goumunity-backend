package com.ssafy.goumunity.user.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.domain.UserCategory;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import com.ssafy.goumunity.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserJpaRepositoryTest {

    @Autowired private UserService userService;

    @Test
    @Transactional
    void 활성화_이메일_조회_테스트() {
        UserCreateDto user =
                UserCreateDto.builder()
                        .email("ssafy@ssafy.com")
                        .password("1234")
                        .age(22)
                        .userCategory(UserCategory.JOB_SEEKER)
                        .gender(1)
                        .regionId(1)
                        .build();

        userService.saveUser(user, null);
        User saved = userService.findUserByEmail(user.getEmail());
        assertEquals(saved.getEmail(), user.getEmail());
    }

    @Test
    @Transactional
    void 없는_이메일_조회_테스트() {
        assertThrows(CustomException.class, () -> userService.findUserByEmail("ssafy@ssafy.com"));
    }
}
