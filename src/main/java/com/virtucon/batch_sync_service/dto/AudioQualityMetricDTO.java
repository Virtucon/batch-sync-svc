package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AudioQualityMetricDTO(
        @JsonProperty("audio_duration_min")
        @NotNull
        @Positive
        BigDecimal audioDurationMin,

        @JsonProperty("audio_sample_rate")
        @NotNull
        @Positive
        Integer audioSampleRate,

        @JsonProperty("spectral_centroids_left")
        @NotNull
        BigDecimal spectralCentroidsLeft,

        @JsonProperty("spectral_centroids_right")
        @NotNull
        BigDecimal spectralCentroidsRight,

        @JsonProperty("spectral_rolloff_left")
        @NotNull
        BigDecimal spectralRolloffLeft,

        @JsonProperty("spectral_rolloff_right")
        @NotNull
        BigDecimal spectralRolloffRight,

        @JsonProperty("spectral_bandwidth_left")
        @NotNull
        BigDecimal spectralBandwidthLeft,

        @JsonProperty("spectral_bandwidth_right")
        @NotNull
        BigDecimal spectralBandwidthRight,

        @JsonProperty("loudness_rms_db_left")
        @NotNull
        BigDecimal loudnessRmsDbLeft,

        @JsonProperty("loudness_rms_db_right")
        @NotNull
        BigDecimal loudnessRmsDbRight,

        @JsonProperty("loudness_peak_db_left")
        @NotNull
        BigDecimal loudnessPeakDbLeft,

        @JsonProperty("loudness_peak_db_right")
        @NotNull
        BigDecimal loudnessPeakDbRight,

        @JsonProperty("loudness_dynamic_range_db_left")
        @NotNull
        BigDecimal loudnessDynamicRangeDbLeft,

        @JsonProperty("loudness_dynamic_range_db_right")
        @NotNull
        BigDecimal loudnessDynamicRangeDbRight,

        @JsonProperty("loudness_volume_balance_left_minus_right_db")
        @NotNull
        BigDecimal loudnessVolumeBalanceLeftMinusRightDb,

        @JsonProperty("activity_snr_db_left")
        @NotNull
        BigDecimal activitySnrDbLeft,

        @JsonProperty("activity_snr_db_right")
        @NotNull
        BigDecimal activitySnrDbRight,

        @JsonProperty("activity_snr_db_average")
        @NotNull
        BigDecimal activitySnrDbAverage,

        @JsonProperty("activity_speech_duration_min_left")
        @NotNull
        BigDecimal activitySpeechDurationMinLeft,

        @JsonProperty("activity_speech_duration_min_right")
        @NotNull
        BigDecimal activitySpeechDurationMinRight,

        @JsonProperty("activity_silence_duration_min_left")
        @NotNull
        BigDecimal activitySilenceDurationMinLeft,

        @JsonProperty("activity_silence_duration_min_right")
        @NotNull
        BigDecimal activitySilenceDurationMinRight,

        @JsonProperty("activity_speech_ratio_left")
        @NotNull
        BigDecimal activitySpeechRatioLeft,

        @JsonProperty("activity_speech_ratio_right")
        @NotNull
        BigDecimal activitySpeechRatioRight,

        @JsonProperty("activity_speech_overlap_duration_sec")
        @NotNull
        BigDecimal activitySpeechOverlapDurationSec,

        @JsonProperty("activity_both_silence_duration_sec")
        @NotNull
        BigDecimal activityBothSilenceDurationSec,

        @JsonProperty("activity_num_silence_periods")
        @NotNull
        BigDecimal activityNumSilencePeriods,

        @JsonProperty("activity_avg_silence_duration_sec")
        @NotNull
        BigDecimal activityAvgSilenceDurationSec,

        @JsonProperty("activity_max_silence_duration_sec")
        @NotNull
        BigDecimal activityMaxSilenceDurationSec,

        @JsonProperty("conversation_num_turns_left")
        @NotNull
        BigDecimal conversationNumTurnsLeft,

        @JsonProperty("conversation_num_turns_right")
        @NotNull
        BigDecimal conversationNumTurnsRight,

        @JsonProperty("conversation_num_turns_total")
        @NotNull
        BigDecimal conversationNumTurnsTotal,

        @JsonProperty("conversation_avg_gap_between_turns")
        @NotNull
        BigDecimal conversationAvgGapBetweenTurns,

        @JsonProperty("conversation_turn_balance_left")
        @NotNull
        BigDecimal conversationTurnBalanceLeft,

        @JsonProperty("conversation_turn_balance_right")
        @NotNull
        BigDecimal conversationTurnBalanceRight
) {
}