package com.virtucon.batch_sync_service.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EnrichmentDTO(
        @JsonProperty("call_id")
        @NotNull(message = "Call ID cannot be null")
        UUID callId,

        @JsonProperty("task_id")
        @NotNull(message = "Task ID cannot be null")
        UUID taskId,

        @JsonProperty("audio_quality_metric")
        @Valid
        @NotNull(message = "Audio quality metric cannot be null")
        AudioQualityMetricDTO audioQualityMetric,

        @JsonProperty("run_config_id")
        @NotNull(message = "Run config ID cannot be null")
        UUID runConfigId,

        @Valid
        @NotEmpty(message = "Sentences list cannot be empty")
        List<SentenceDTO> sentences,

        @JsonProperty("generated_at")
        @NotNull(message = "Generated at timestamp cannot be null")
        Instant generatedAt
) {
}