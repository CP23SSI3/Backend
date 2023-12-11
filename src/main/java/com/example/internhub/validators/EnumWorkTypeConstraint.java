package com.example.internhub.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumWorkTypeValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumWorkTypeConstraint {
    String message() default "Unknown work type.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
