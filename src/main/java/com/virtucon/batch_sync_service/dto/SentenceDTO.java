package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record SentenceDTO(
        @NotNull(message = "Index cannot be null")
        Integer idx,

        @NotEmpty(message = "Text cannot be empty")
        String text,

        @NotEmpty(message = "Emotion cannot be empty")
        String emotion,

        @JsonProperty("emotion_score")
        @NotNull(message = "Emotion score cannot be null")
        @DecimalMin(value = "0.0", message = "Emotion score must be between 0 and 1")
        @DecimalMax(value = "1.0", message = "Emotion score must be between 0 and 1")
        BigDecimal emotionScore,

        @NotEmpty(message = "Speaker cannot be empty")
        String speaker,

        @NotNull(message = "Start time cannot be null")
        @DecimalMin(value = "0.0", message = "Start time must be non-negative")
        BigDecimal start,

        @NotNull(message = "End time cannot be null")
        @DecimalMin(value = "0.0", message = "End time must be non-negative")
        BigDecimal end,

        @JsonProperty("asr_confidence")
        @NotEmpty(message = "ASR confidence list cannot be empty")
        List<BigDecimal> asrConfidence,

        @JsonProperty("diarisation_confidence")
        @NotEmpty(message = "Diarisation confidence list cannot be empty")
        List<String> diarisationConfidence
) {
}