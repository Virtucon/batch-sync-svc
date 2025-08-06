package com.virtucon.batch_sync_service.exception;

import java.util.UUID;

public class EntityAlreadyExistsException extends RuntimeException {
    
    public EntityAlreadyExistsException(String entityType, UUID id) {
        super(String.format("%s with call ID %s already exists", entityType, id));
    }
    
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}