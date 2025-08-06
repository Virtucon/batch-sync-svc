package com.virtucon.batch_sync_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(ValidTimeRange constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            BigDecimal startValue = getFieldValue(value, startField);
            BigDecimal endValue = getFieldValue(value, endField);

            if (startValue == null || endValue == null) {
                return true;
            }

            return startValue.compareTo(endValue) <= 0;
        } catch (Exception e) {
            return false;
        }
    }

    private BigDecimal getFieldValue(Object object, String fieldName) throws Exception {
        Method method = object.getClass().getMethod(fieldName);
        Object result = method.invoke(object);
        
        if (result instanceof BigDecimal) {
            return (BigDecimal) result;
        } else if (result instanceof Number) {
            return BigDecimal.valueOf(((Number) result).doubleValue());
        }
        
        return null;
    }
}