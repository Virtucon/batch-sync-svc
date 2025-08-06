package com.virtucon.batch_sync_service.service.impl;

import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.mapper.EnrichmentMapper;
import com.virtucon.batch_sync_service.repository.EnrichmentRepository;
import com.virtucon.batch_sync_service.service.EnrichmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EnrichmentServiceImpl implements EnrichmentService {

    private final EnrichmentRepository enrichmentRepository;
    private final EnrichmentMapper enrichmentMapper;

    @Autowired
    public EnrichmentServiceImpl(EnrichmentRepository enrichmentRepository, 
                                EnrichmentMapper enrichmentMapper) {
        this.enrichmentRepository = enrichmentRepository;
        this.enrichmentMapper = enrichmentMapper;
    }

    @Override
    public Enrichment createEnrichment(EnrichmentDTO enrichmentDTO) {
        if (existsByCallId(enrichmentDTO.callId())) {
            throw new IllegalArgumentException("Enrichment with call ID " + enrichmentDTO.callId() + " already exists");
        }
        
        Enrichment enrichment = enrichmentMapper.toEntity(enrichmentDTO);
        return enrichmentRepository.save(enrichment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enrichment> findByCallId(UUID callId) {
        return enrichmentRepository.findByCallId(callId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCallId(UUID callId) {
        return enrichmentRepository.existsByCallId(callId);
    }
}