package com.virtucon.batch_sync_service.controller;

import com.virtucon.batch_sync_service.dto.CreateResponseDTO;
import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.dto.EnrichmentResponseDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.response.ApiResponse;
import com.virtucon.batch_sync_service.service.EnrichmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/call/{callId}")
    public ResponseEntity<ApiResponse<EnrichmentResponseDTO>> getEnrichmentByCallId(@PathVariable UUID callId) {
        return enrichmentService.findByCallId(callId)
                .map(enrichment -> {
                    EnrichmentResponseDTO data = new EnrichmentResponseDTO(
                            enrichment.getId(),
                            enrichment.getCallId(),
                            enrichment.getRunConfigId(),
                            enrichment.getGeneratedAt(),
                            enrichment.getSentences().size()
                    );
                    ApiResponse<EnrichmentResponseDTO> response = ApiResponse.success("Enrichment found", data);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<EnrichmentResponseDTO> response = ApiResponse.error("Enrichment with call ID " + callId + " not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

}