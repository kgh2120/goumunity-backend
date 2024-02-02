package com.ssafy.goumunity.common.constraint;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 이메일 제약조건에 사용하고 싶을 때 사용하는 어노테이션입니다. 이메일 규칙은 (영문,숫자)@(영문,숫자,.,-).(영어 2~6글자)입니다.
 *
 * @author 김규현
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "이메일 형식이 맞지 않습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
