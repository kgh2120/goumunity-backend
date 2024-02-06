package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.common.util.RandomEnumGenerator;
import com.ssafy.goumunity.domain.user.domain.FirstNickname;
import com.ssafy.goumunity.domain.user.domain.LastNickname;
import com.ssafy.goumunity.domain.user.domain.MiddleNickname;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
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
        RandomEnumGenerator<FirstNickname> firstNicknameRandomGenerator =
                new RandomEnumGenerator<>(FirstNickname.class);
        RandomEnumGenerator<MiddleNickname> middleNicknameRandomGenerator =
                new RandomEnumGenerator<>(MiddleNickname.class);
        RandomEnumGenerator<LastNickname> lastNicknameRandomGenerator =
                new RandomEnumGenerator<>(LastNickname.class);

        try {
            for (int tryNum = 0; tryNum < NICKNAME_CREATE_TRY_NUM; tryNum++) {
                String first = firstNicknameRandomGenerator.randomEnum().getKr();
                String middle = middleNicknameRandomGenerator.randomEnum().getKr();
                String last = lastNicknameRandomGenerator.randomEnum().getKr();
                String code = createCode();

                String nickname = first + " " + middle + last + " #" + code;

                if (!userService.isExistNickname(nickname)) {
                    return nickname;
                }
            }
            // NICKNAME_CREATE_TRY_NUM 횟수 이상 중복 닉네임이 발생하면 생성 실패
            throw new UserException(UserErrorCode.RANDOM_NICKNAME_CREATE_FAILED);
        } catch (NoSuchAlgorithmException e) {
            throw new UserException(UserErrorCode.RANDOM_NICKNAME_CREATE_FAILED);
        }
    }

    private String createCode() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < NICKNAME_CODE_LENGTH; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
