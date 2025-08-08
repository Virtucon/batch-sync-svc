package com.virtucon.batch_sync_service.dto;

import java.time.Instant;
import java.util.UUID;

public record EnrichmentResponseDTO(
        Long id,
        UUID callId,
        UUID taskId,
        UUID runConfigId,
        Instant generatedAt,
        int sentenceCount
) {
}