package com.example.internhub.validators;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.Gender;
import com.example.internhub.entities.WorkType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class EnumGenderTypeValidator implements ConstraintValidator<EnumGenderTypeConstraint, String> {

    @Override
    public void initialize(EnumGenderTypeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (gender != null) Gender.valueOf(gender.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
