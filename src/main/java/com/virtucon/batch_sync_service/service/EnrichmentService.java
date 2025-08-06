package com.virtucon.batch_sync_service.service;

import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;


public interface EnrichmentService extends BaseCallService<Enrichment, EnrichmentDTO> {
    
    Enrichment createEnrichment(EnrichmentDTO enrichmentDTO);
}