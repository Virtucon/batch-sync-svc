package com.virtucon.batch_sync_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtucon.batch_sync_service.entity.Enrichment;

@Repository
public interface EnrichmentRepository extends JpaRepository<Enrichment, Long> {
    
    Optional<Enrichment> findByCallId(UUID callId);
    
    Optional<Enrichment> findByTaskId(UUID taskId);
    
    boolean existsByCallId(UUID callId);
    
    boolean existsByTaskId(UUID taskId);
}