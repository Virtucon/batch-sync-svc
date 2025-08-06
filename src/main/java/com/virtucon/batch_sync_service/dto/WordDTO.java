package com.virtucon.batch_sync_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.virtucon.batch_sync_service.validation.ValidTimeRange;
import com.virtucon.batch_sync_service.validation.ValidConfidenceScore;

import java.math.BigDecimal;

@ValidTimeRange
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
        @ValidConfidenceScore
        BigDecimal confidence,

        @Valid
        @NotNull
        MetadataDTO metadata
) {
}