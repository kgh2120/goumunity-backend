package com.ssafy.goumunity.common.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * List의 size가 maxSize보다 작은지 검증하는 제약검증 클래스입니다.
 *
 * <pre>@MaxListSize에 선언된 maxSize 변수와 @MaxListSize가 붙은 필드의 사이즈를 비교합니다. </pre>
 */
public class MaxListSizeConstraintValidator implements ConstraintValidator<MaxListSize, List> {

    private int maxSize;

    @Override
    public void initialize(MaxListSize constraintAnnotation) {
        maxSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List ts, ConstraintValidatorContext constraintValidatorContext) {
        return ts.size() <= maxSize;
    }
}
