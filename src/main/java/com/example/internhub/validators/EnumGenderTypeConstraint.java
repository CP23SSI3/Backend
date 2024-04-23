package com.example.internhub.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumGenderTypeValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumGenderTypeConstraint {
    String message() default "Unknown gender type.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}