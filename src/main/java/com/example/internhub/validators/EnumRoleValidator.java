package com.example.internhub.validators;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.WorkType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            WorkType.valueOf(role.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
