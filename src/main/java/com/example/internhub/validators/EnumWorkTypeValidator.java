package com.example.internhub.validators;

import com.example.internhub.entities.WorkType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EnumWorkTypeValidator implements ConstraintValidator<EnumWorkTypeConstraint, String> {
    @Override
    public void initialize(EnumWorkTypeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String workType, ConstraintValidatorContext constraintValidatorContext) {
        try {
            WorkType.valueOf(workType.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
