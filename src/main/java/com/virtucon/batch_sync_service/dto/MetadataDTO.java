package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MetadataDTO(
        @JsonProperty("left_energy")
        @NotNull
        @DecimalMin(value = "0.0", message = "Left energy must be non-negative")
        @DecimalMax(value = "1.0", message = "Left energy must be at most 1.0")
        BigDecimal leftEnergy,

        @JsonProperty("right_energy")
        @NotNull
        @DecimalMin(value = "0.0", message = "Right energy must be non-negative")
        @DecimalMax(value = "1.0", message = "Right energy must be at most 1.0")
        BigDecimal rightEnergy,

        @JsonProperty("left_zcr")
        @NotNull
        @DecimalMin(value = "0.0", message = "Left ZCR must be non-negative")
        BigDecimal leftZcr,

        @JsonProperty("right_zcr")
        @NotNull
        @DecimalMin(value = "0.0", message = "Right ZCR must be non-negative")
        BigDecimal rightZcr
) {
}