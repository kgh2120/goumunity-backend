package com.ssafy.goumunity.domain.user.service;

import static com.ssafy.goumunity.domain.user.util.RandomEnumGenerator.*;

import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import com.ssafy.goumunity.domain.user.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NicknameServiceImpl implements NicknameService {

    private final UserService userService;
    private static final int NICKNAME_CODE_LENGTH = 4;
    private static final int NICKNAME_CREATE_TRY_NUM = 20;

    @Override
    @Transactional
    public String createRandomNickname() {
        for (int tryNum = 0; tryNum < NICKNAME_CREATE_TRY_NUM; tryNum++) {
            String first = FIRST_NICKNAME_RANDOM_ENUM_GENERATOR.randomEnum().getKr();
            String middle = MIDDLE_NICKNAME_RANDOM_ENUM_GENERATOR.randomEnum().getKr();
            String last = LAST_NICKNAME_RANDOM_ENUM_GENERATOR.randomEnum().getKr();
            String code = RandomCodeGenerator.randomCode(NICKNAME_CODE_LENGTH);

            String nickname = first + " " + middle + last + " #" + code;

            if (!userService.isExistNickname(nickname)) {
                return nickname;
            }
        }
        // NICKNAME_CREATE_TRY_NUM 횟수 이상 중복 닉네임이 발생하면 생성 실패
        throw new UserException(UserErrorCode.RANDOM_NICKNAME_CREATE_FAILED);
    }
}
