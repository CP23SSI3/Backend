package com.example.internhub.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumDocumentTypesValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumDocumentTypesConstraint {
    String message() default "ENUM not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
