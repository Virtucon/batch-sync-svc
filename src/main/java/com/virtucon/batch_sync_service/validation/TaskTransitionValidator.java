package com.virtucon.batch_sync_service.validation;

import com.virtucon.batch_sync_service.dto.UpdateTaskDto;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class TaskTransitionValidator implements ConstraintValidator<ValidTaskTransition, UpdateTaskDto> {

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
    public boolean isValid(UpdateTaskDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.taskStatus() == null) {
            return true;
        }

        return true;
    }
}