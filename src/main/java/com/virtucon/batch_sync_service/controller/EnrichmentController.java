package com.virtucon.batch_sync_service.controller;

import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.response.ApiResponse;
import com.virtucon.batch_sync_service.service.EnrichmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/enrichments")
public class EnrichmentController {

    private final EnrichmentService enrichmentService;

    @Autowired
    public EnrichmentController(EnrichmentService enrichmentService) {
        this.enrichmentService = enrichmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> createEnrichment(@Valid @RequestBody EnrichmentDTO enrichmentDTO) {
        try {
            Enrichment savedEnrichment = enrichmentService.createEnrichment(enrichmentDTO);
            
            Map<String, Object> data = Map.of(
                    "id", savedEnrichment.getId(),
                    "callId", savedEnrichment.getCallId()
            );
            
            ApiResponse<Map<String, Object>> response = ApiResponse.success("Enrichment saved successfully.", data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            ApiResponse<Map<String, Object>> response = ApiResponse.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (Exception e) {
            ApiResponse<Map<String, Object>> response = ApiResponse.error("Failed to create enrichment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/call/{callId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getEnrichmentByCallId(@PathVariable UUID callId) {
        return enrichmentService.findByCallId(callId)
                .map(enrichment -> {
                    Map<String, Object> data = Map.of(
                            "id", enrichment.getId(),
                            "callId", enrichment.getCallId(),
                            "runConfigId", enrichment.getRunConfigId(),
                            "generatedAt", enrichment.getGeneratedAt(),
                            "sentenceCount", enrichment.getSentences().size()
                    );
                    ApiResponse<Map<String, Object>> response = ApiResponse.success("Enrichment found", data);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Map<String, Object>> response = ApiResponse.error("Enrichment with call ID " + callId + " not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception e) {
        ApiResponse<Object> response = ApiResponse.error("Unexpected error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}