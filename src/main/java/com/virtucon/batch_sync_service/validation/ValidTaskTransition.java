package com.virtucon.batch_sync_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaskTransitionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaskTransition {
    String message() default "Invalid task status transition";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}