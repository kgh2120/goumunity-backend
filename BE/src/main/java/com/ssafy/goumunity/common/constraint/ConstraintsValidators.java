package com.ssafy.goumunity.common.constraint;

import com.ssafy.goumunity.domain.user.controller.request.UserLoginRequest;
import java.util.regex.Pattern;

/**
 * 정규표현식으로 검증을 하는 클래스입니다.
 *
 * @author 김규현
 */
public class ConstraintsValidators {
    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEXP =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%])[a-zA-Z\\d!@#\\$%]{8,20}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEXP);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);

    private ConstraintsValidators() {
        throw new IllegalStateException("This is utility class. Do not initialize this class");
    }

    public static boolean validateLoginPattern(UserLoginRequest userLoginRequest) {
        return validateEmailPattern(userLoginRequest.getId())
                && validatePasswordPattern(userLoginRequest.getPassword());
    }

    public static boolean validatePasswordPattern(String password) {
        if (password == null) {
            return false;
        }

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean validateEmailPattern(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
