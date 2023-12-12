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

    }

    @Override
    public boolean isValid(List<String> documentList, ConstraintValidatorContext constraintValidatorContext) {
        if (documentList == null) return true;
        try {
            if (documentList.size() > 0) {
                for (String document : documentList) {
                    Document.valueOf(document.toLowerCase());
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
