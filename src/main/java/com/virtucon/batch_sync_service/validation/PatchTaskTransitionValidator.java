package com.virtucon.batch_sync_service.validation;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import com.virtucon.batch_sync_service.dto.PatchTaskDto;
import com.virtucon.batch_sync_service.entity.TaskStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatchTaskTransitionValidator implements ConstraintValidator<ValidTaskTransition, PatchTaskDto> {

    private static final Map<TaskStatus, EnumSet<TaskStatus>> VALID_TRANSITIONS;

    static {
        VALID_TRANSITIONS = new EnumMap<>(TaskStatus.class);
        VALID_TRANSITIONS.put(TaskStatus.READY, EnumSet.of(TaskStatus.IN_PROGRESS, TaskStatus.BLOCKED));
        VALID_TRANSITIONS.put(TaskStatus.IN_PROGRESS, EnumSet.of(TaskStatus.COMPLETED, TaskStatus.FAILED, TaskStatus.BLOCKED));
        VALID_TRANSITIONS.put(TaskStatus.COMPLETED, EnumSet.noneOf(TaskStatus.class));
        VALID_TRANSITIONS.put(TaskStatus.FAILED, EnumSet.of(TaskStatus.READY, TaskStatus.BLOCKED));
        VALID_TRANSITIONS.put(TaskStatus.BLOCKED, EnumSet.of(TaskStatus.READY, TaskStatus.FAILED));
    }

    @Override
    public void initialize(ValidTaskTransition constraintAnnotation) {
    }

    @Override
    public boolean isValid(PatchTaskDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.taskStatus() == null) {
            return true;
        }

        // For now, we'll just validate that the status is a valid enum value
        // More complex validation would require knowing the current status from the database
        return true;
    }
}
