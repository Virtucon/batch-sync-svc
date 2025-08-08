package com.virtucon.batch_sync_service.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = {PatchTaskTransitionValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaskTransition {
    String message() default "Invalid task status transition";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}