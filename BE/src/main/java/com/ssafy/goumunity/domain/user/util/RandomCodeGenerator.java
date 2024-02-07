package com.ssafy.goumunity.domain.user.util;

import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomCodeGenerator {

    public static String randomCode(int length) {
        Random random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new UserException(UserErrorCode.CREATE_RANDOM_CODE_FAILED);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
