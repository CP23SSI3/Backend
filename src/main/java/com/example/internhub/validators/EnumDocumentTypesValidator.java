package com.example.internhub.validators;

import com.example.internhub.entities.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EnumDocumentTypesValidator implements ConstraintValidator<EnumDocumentTypesConstraint, List<String>> {

    @Override
    public void initialize(EnumDocumentTypesConstraint constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> document, ConstraintValidatorContext constraintValidatorContext) {
//        System.out.println(document);
        return true;
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Testing");
//        return false;
    }
}
