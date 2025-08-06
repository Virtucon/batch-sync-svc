package com.virtucon.batch_sync_service.service;

import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;

import java.util.Optional;
import java.util.UUID;

public interface EnrichmentService {
    
    Enrichment createEnrichment(EnrichmentDTO enrichmentDTO);
    
    Optional<Enrichment> findByCallId(UUID callId);
    
    boolean existsByCallId(UUID callId);
}