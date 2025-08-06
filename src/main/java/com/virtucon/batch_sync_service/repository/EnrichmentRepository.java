package com.virtucon.batch_sync_service.repository;

import com.virtucon.batch_sync_service.entity.Enrichment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrichmentRepository extends JpaRepository<Enrichment, Long> {
    
    Optional<Enrichment> findByCallId(UUID callId);
    
    boolean existsByCallId(UUID callId);
}