package com.virtucon.batch_sync_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtucon.batch_sync_service.dto.CreateResponseDTO;
import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.response.ApiResponse;
import com.virtucon.batch_sync_service.service.EnrichmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/enrichments")
public class EnrichmentController {

    private final EnrichmentService enrichmentService;

    public EnrichmentController(EnrichmentService enrichmentService) {
        this.enrichmentService = enrichmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponseDTO>> createEnrichment(@Valid @RequestBody EnrichmentDTO enrichmentDTO) {
        Enrichment savedEnrichment = enrichmentService.createEnrichment(enrichmentDTO);
        
        CreateResponseDTO data = new CreateResponseDTO(
                savedEnrichment.getId(),
                savedEnrichment.getCallId()
        );
        
        ApiResponse<CreateResponseDTO> response = ApiResponse.success("Enrichment saved successfully.", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}