package com.ssafy.goumunity.common.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 *
 * <pre>@Email</pre>
 *
 * 에 작동하는 제약조건 검증자입니다. 검증시 false가 나오면 예외가 발생합니다.
 *
 * @author 김규현
 */
public class EmailValidator implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return ConstraintsValidators.validateEmailPattern(email);
    }
}
