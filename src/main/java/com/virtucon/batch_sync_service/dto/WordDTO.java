package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WordDTO(
        @NotBlank(message = "Word cannot be blank")
        String word,

        @NotNull
        @DecimalMin(value = "0.0", message = "Start time must be non-negative")
        BigDecimal start,

        @NotNull
        @DecimalMin(value = "0.0", message = "End time must be non-negative")
        BigDecimal end,

        @NotNull
        @DecimalMin(value = "0.0", message = "Confidence must be between 0 and 1")
        @DecimalMax(value = "1.0", message = "Confidence must be between 0 and 1")
        BigDecimal confidence,

        @Valid
        @NotNull
        MetadataDTO metadata
) {
}