package com.ssafy.goumunity.common.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxListSizeConstraintValidator implements ConstraintValidator<MaxListSize, List> {

    private int maxSize;

    @Override
    public void initialize(MaxListSize constraintAnnotation) {
        maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(List ts, ConstraintValidatorContext constraintValidatorContext) {
        return ts.size() <= maxSize;
    }
}
