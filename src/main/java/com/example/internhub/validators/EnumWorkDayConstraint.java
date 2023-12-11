package com.example.internhub.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumWorkDayValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumWorkDayConstraint {
    String message() default "Unknown work day.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
