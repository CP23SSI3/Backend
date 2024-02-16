package com.example.internhub.validators;

import com.example.internhub.entities.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EnumRoleValidator implements ConstraintValidator<EnumRoleConstraint, String> {

    @Override
    public void initialize(EnumRoleConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Role.valueOf(role.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
