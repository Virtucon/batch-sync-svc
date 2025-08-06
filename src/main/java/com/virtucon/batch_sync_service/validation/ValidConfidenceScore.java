package com.virtucon.batch_sync_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConfidenceScoreValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidConfidenceScore {
    String message() default "Confidence score must be between 0.0 and 1.0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}