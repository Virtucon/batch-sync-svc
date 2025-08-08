package com.virtucon.batch_sync_service.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.exception.EntityAlreadyExistsException;
import com.virtucon.batch_sync_service.mapper.EnrichmentMapper;
import com.virtucon.batch_sync_service.repository.EnrichmentRepository;
import com.virtucon.batch_sync_service.service.EnrichmentService;

@Service
@Transactional
public class EnrichmentServiceImpl implements EnrichmentService {

    private final EnrichmentRepository enrichmentRepository;
    private final EnrichmentMapper enrichmentMapper;

    public EnrichmentServiceImpl(EnrichmentRepository enrichmentRepository, 
                                EnrichmentMapper enrichmentMapper) {
        this.enrichmentRepository = enrichmentRepository;
        this.enrichmentMapper = enrichmentMapper;
    }

    @Override
    public Enrichment createEntity(EnrichmentDTO enrichmentDTO) {
        return createEnrichment(enrichmentDTO);
    }

    @Override
    public Enrichment createEnrichment(EnrichmentDTO enrichmentDTO) {
        if (existsByCallId(enrichmentDTO.callId())) {
            throw new EntityAlreadyExistsException("Enrichment", enrichmentDTO.callId());
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
    public Optional<Enrichment> findByTaskId(UUID taskId) {
        return enrichmentRepository.findByTaskId(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCallId(UUID callId) {
        return enrichmentRepository.existsByCallId(callId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTaskId(UUID taskId) {
        return enrichmentRepository.existsByTaskId(taskId);
    }
}