package com.example.internhub.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumRoleValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumRoleConstraint {
    String message() default "Unknown role.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
