package com.virtucon.batch_sync_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeRange {
    String message() default "Start time must be less than or equal to end time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String startField() default "start";
    String endField() default "end";
}