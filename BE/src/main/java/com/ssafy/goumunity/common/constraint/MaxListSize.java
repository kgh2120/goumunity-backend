package com.ssafy.goumunity.common.constraint;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxListSizeConstraintValidator.class)
public @interface MaxListSize {

    String message() default "지정된 사이즈보다 많은 양을 넣었습니다.";

    Class[] groups() default {};

    Class[] payload() default {};

    int maxSize();
}
