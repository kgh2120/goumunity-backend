package com.ssafy.goumunity.common.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 비밀번호 검증 어노테이션
 *
 * <pre>@Password</pre>
 *
 * 를 사용해 검증하는 클래스입니다.
 *
 * @author 김규현
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return ConstraintsValidators.validatePasswordPattern(password);
    }
}
