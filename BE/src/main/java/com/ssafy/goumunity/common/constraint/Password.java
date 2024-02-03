package com.ssafy.goumunity.common.constraint;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 비밀번호 검증에 사용되는 어노테이션입니다.
 *
 * @author 김규현
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "패스워드 패턴이 맞지 않습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
