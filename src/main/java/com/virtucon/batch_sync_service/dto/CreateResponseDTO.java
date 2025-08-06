package com.virtucon.batch_sync_service.dto;

import java.util.UUID;

public record CreateResponseDTO(
        Long id,
        UUID callId
) {
}