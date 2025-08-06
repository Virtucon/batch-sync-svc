package com.virtucon.batch_sync_service.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String entityType, UUID id) {
        super(String.format("%s with ID %s not found", entityType, id));
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}