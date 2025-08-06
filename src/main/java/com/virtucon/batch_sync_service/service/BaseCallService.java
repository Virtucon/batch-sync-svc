package com.virtucon.batch_sync_service.service;

import java.util.Optional;
import java.util.UUID;

public interface BaseCallService<T, DTO> {
    
    T createEntity(DTO dto);
    
    Optional<T> findByCallId(UUID callId);
    
    boolean existsByCallId(UUID callId);
}