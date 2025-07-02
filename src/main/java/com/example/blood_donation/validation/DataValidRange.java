package com.example.blood_donation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataValidRangeValidator.class)
public @interface DataValidRange {
    String message() default "endTime must be after or equal to startTime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startFieldName() default "startTime";

    String endFieldName() default "endField";
}
