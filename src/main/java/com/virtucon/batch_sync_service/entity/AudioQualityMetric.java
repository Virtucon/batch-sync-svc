package com.virtucon.batch_sync_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "audio_quality_metrics")
public class AudioQualityMetric {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "audio_duration_min", nullable = false, precision = 10, scale = 3)
    private BigDecimal audioDurationMin;

    @Column(name = "audio_sample_rate", nullable = false)
    private Integer audioSampleRate;

    @Column(name = "spectral_centroids_left", nullable = false, precision = 10, scale = 3)
    private BigDecimal spectralCentroidsLeft;

    @Column(name = "spectral_centroids_right", nullable = false, precision = 10, scale = 3)
    private BigDecimal spectralCentroidsRight;

    @Column(name = "spectral_rolloff_left", nullable = false, precision = 10, scale = 6)
    private BigDecimal spectralRolloffLeft;

    @Column(name = "spectral_rolloff_right", nullable = false, precision = 10, scale = 6)
    private BigDecimal spectralRolloffRight;

    @Column(name = "spectral_bandwidth_left", nullable = false, precision = 10, scale = 6)
    private BigDecimal spectralBandwidthLeft;

    @Column(name = "spectral_bandwidth_right", nullable = false, precision = 10, scale = 6)
    private BigDecimal spectralBandwidthRight;

    @Column(name = "loudness_rms_db_left", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessRmsDbLeft;

    @Column(name = "loudness_rms_db_right", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessRmsDbRight;

    @Column(name = "loudness_peak_db_left", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessPeakDbLeft;

    @Column(name = "loudness_peak_db_right", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessPeakDbRight;

    @Column(name = "loudness_dynamic_range_db_left", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessDynamicRangeDbLeft;

    @Column(name = "loudness_dynamic_range_db_right", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessDynamicRangeDbRight;

    @Column(name = "loudness_volume_balance_left_minus_right_db", nullable = false, precision = 8, scale = 3)
    private BigDecimal loudnessVolumeBalanceLeftMinusRightDb;

    @Column(name = "activity_snr_db_left", nullable = false, precision = 8, scale = 3)
    private BigDecimal activitySnrDbLeft;

    @Column(name = "activity_snr_db_right", nullable = false, precision = 8, scale = 3)
    private BigDecimal activitySnrDbRight;

    @Column(name = "activity_snr_db_average", nullable = false, precision = 8, scale = 3)
    private BigDecimal activitySnrDbAverage;

    @Column(name = "activity_speech_duration_min_left", nullable = false, precision = 10, scale = 3)
    private BigDecimal activitySpeechDurationMinLeft;

    @Column(name = "activity_speech_duration_min_right", nullable = false, precision = 10, scale = 3)
    private BigDecimal activitySpeechDurationMinRight;

    @Column(name = "activity_silence_duration_min_left", nullable = false, precision = 10, scale = 3)
    private BigDecimal activitySilenceDurationMinLeft;

    @Column(name = "activity_silence_duration_min_right", nullable = false, precision = 10, scale = 3)
    private BigDecimal activitySilenceDurationMinRight;

    @Column(name = "activity_speech_ratio_left", nullable = false, precision = 8, scale = 6)
    private BigDecimal activitySpeechRatioLeft;

    @Column(name = "activity_speech_ratio_right", nullable = false, precision = 8, scale = 6)
    private BigDecimal activitySpeechRatioRight;

    @Column(name = "activity_speech_overlap_duration_sec", nullable = false, precision = 10, scale = 3)
    private BigDecimal activitySpeechOverlapDurationSec;

    @Column(name = "activity_both_silence_duration_sec", nullable = false, precision = 10, scale = 3)
    private BigDecimal activityBothSilenceDurationSec;

    @Column(name = "activity_num_silence_periods", nullable = false, precision = 10, scale = 3)
    private BigDecimal activityNumSilencePeriods;

    @Column(name = "activity_avg_silence_duration_sec", nullable = false, precision = 8, scale = 3)
    private BigDecimal activityAvgSilenceDurationSec;

    @Column(name = "activity_max_silence_duration_sec", nullable = false, precision = 8, scale = 3)
    private BigDecimal activityMaxSilenceDurationSec;

    @Column(name = "conversation_num_turns_left", nullable = false, precision = 10, scale = 3)
    private BigDecimal conversationNumTurnsLeft;

    @Column(name = "conversation_num_turns_right", nullable = false, precision = 10, scale = 3)
    private BigDecimal conversationNumTurnsRight;

    @Column(name = "conversation_num_turns_total", nullable = false, precision = 10, scale = 3)
    private BigDecimal conversationNumTurnsTotal;

    @Column(name = "conversation_avg_gap_between_turns", nullable = false, precision = 8, scale = 3)
    private BigDecimal conversationAvgGapBetweenTurns;

    @Column(name = "conversation_turn_balance_left", nullable = false, precision = 8, scale = 6)
    private BigDecimal conversationTurnBalanceLeft;

    @Column(name = "conversation_turn_balance_right", nullable = false, precision = 8, scale = 6)
    private BigDecimal conversationTurnBalanceRight;

    @OneToOne(mappedBy = "audioQualityMetric", fetch = FetchType.LAZY)
    private Transcription transcription;

    protected AudioQualityMetric() {
    }

    public AudioQualityMetric(BigDecimal audioDurationMin, Integer audioSampleRate, 
                             BigDecimal spectralCentroidsLeft, BigDecimal spectralCentroidsRight,
                             BigDecimal spectralRolloffLeft, BigDecimal spectralRolloffRight,
                             BigDecimal spectralBandwidthLeft, BigDecimal spectralBandwidthRight,
                             BigDecimal loudnessRmsDbLeft, BigDecimal loudnessRmsDbRight,
                             BigDecimal loudnessPeakDbLeft, BigDecimal loudnessPeakDbRight,
                             BigDecimal loudnessDynamicRangeDbLeft, BigDecimal loudnessDynamicRangeDbRight,
                             BigDecimal loudnessVolumeBalanceLeftMinusRightDb,
                             BigDecimal activitySnrDbLeft, BigDecimal activitySnrDbRight,
                             BigDecimal activitySnrDbAverage,
                             BigDecimal activitySpeechDurationMinLeft, BigDecimal activitySpeechDurationMinRight,
                             BigDecimal activitySilenceDurationMinLeft, BigDecimal activitySilenceDurationMinRight,
                             BigDecimal activitySpeechRatioLeft, BigDecimal activitySpeechRatioRight,
                             BigDecimal activitySpeechOverlapDurationSec, BigDecimal activityBothSilenceDurationSec,
                             BigDecimal activityNumSilencePeriods, BigDecimal activityAvgSilenceDurationSec,
                             BigDecimal activityMaxSilenceDurationSec,
                             BigDecimal conversationNumTurnsLeft, BigDecimal conversationNumTurnsRight,
                             BigDecimal conversationNumTurnsTotal, BigDecimal conversationAvgGapBetweenTurns,
                             BigDecimal conversationTurnBalanceLeft, BigDecimal conversationTurnBalanceRight) {
        this.audioDurationMin = audioDurationMin;
        this.audioSampleRate = audioSampleRate;
        this.spectralCentroidsLeft = spectralCentroidsLeft;
        this.spectralCentroidsRight = spectralCentroidsRight;
        this.spectralRolloffLeft = spectralRolloffLeft;
        this.spectralRolloffRight = spectralRolloffRight;
        this.spectralBandwidthLeft = spectralBandwidthLeft;
        this.spectralBandwidthRight = spectralBandwidthRight;
        this.loudnessRmsDbLeft = loudnessRmsDbLeft;
        this.loudnessRmsDbRight = loudnessRmsDbRight;
        this.loudnessPeakDbLeft = loudnessPeakDbLeft;
        this.loudnessPeakDbRight = loudnessPeakDbRight;
        this.loudnessDynamicRangeDbLeft = loudnessDynamicRangeDbLeft;
        this.loudnessDynamicRangeDbRight = loudnessDynamicRangeDbRight;
        this.loudnessVolumeBalanceLeftMinusRightDb = loudnessVolumeBalanceLeftMinusRightDb;
        this.activitySnrDbLeft = activitySnrDbLeft;
        this.activitySnrDbRight = activitySnrDbRight;
        this.activitySnrDbAverage = activitySnrDbAverage;
        this.activitySpeechDurationMinLeft = activitySpeechDurationMinLeft;
        this.activitySpeechDurationMinRight = activitySpeechDurationMinRight;
        this.activitySilenceDurationMinLeft = activitySilenceDurationMinLeft;
        this.activitySilenceDurationMinRight = activitySilenceDurationMinRight;
        this.activitySpeechRatioLeft = activitySpeechRatioLeft;
        this.activitySpeechRatioRight = activitySpeechRatioRight;
        this.activitySpeechOverlapDurationSec = activitySpeechOverlapDurationSec;
        this.activityBothSilenceDurationSec = activityBothSilenceDurationSec;
        this.activityNumSilencePeriods = activityNumSilencePeriods;
        this.activityAvgSilenceDurationSec = activityAvgSilenceDurationSec;
        this.activityMaxSilenceDurationSec = activityMaxSilenceDurationSec;
        this.conversationNumTurnsLeft = conversationNumTurnsLeft;
        this.conversationNumTurnsRight = conversationNumTurnsRight;
        this.conversationNumTurnsTotal = conversationNumTurnsTotal;
        this.conversationAvgGapBetweenTurns = conversationAvgGapBetweenTurns;
        this.conversationTurnBalanceLeft = conversationTurnBalanceLeft;
        this.conversationTurnBalanceRight = conversationTurnBalanceRight;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAudioDurationMin() {
        return audioDurationMin;
    }

    public void setAudioDurationMin(BigDecimal audioDurationMin) {
        this.audioDurationMin = audioDurationMin;
    }

    public Integer getAudioSampleRate() {
        return audioSampleRate;
    }

    public void setAudioSampleRate(Integer audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }

    public BigDecimal getSpectralCentroidsLeft() {
        return spectralCentroidsLeft;
    }

    public void setSpectralCentroidsLeft(BigDecimal spectralCentroidsLeft) {
        this.spectralCentroidsLeft = spectralCentroidsLeft;
    }

    public BigDecimal getSpectralCentroidsRight() {
        return spectralCentroidsRight;
    }

    public void setSpectralCentroidsRight(BigDecimal spectralCentroidsRight) {
        this.spectralCentroidsRight = spectralCentroidsRight;
    }

    public BigDecimal getSpectralRolloffLeft() {
        return spectralRolloffLeft;
    }

    public void setSpectralRolloffLeft(BigDecimal spectralRolloffLeft) {
        this.spectralRolloffLeft = spectralRolloffLeft;
    }

    public BigDecimal getSpectralRolloffRight() {
        return spectralRolloffRight;
    }

    public void setSpectralRolloffRight(BigDecimal spectralRolloffRight) {
        this.spectralRolloffRight = spectralRolloffRight;
    }

    public BigDecimal getSpectralBandwidthLeft() {
        return spectralBandwidthLeft;
    }

    public void setSpectralBandwidthLeft(BigDecimal spectralBandwidthLeft) {
        this.spectralBandwidthLeft = spectralBandwidthLeft;
    }

    public BigDecimal getSpectralBandwidthRight() {
        return spectralBandwidthRight;
    }

    public void setSpectralBandwidthRight(BigDecimal spectralBandwidthRight) {
        this.spectralBandwidthRight = spectralBandwidthRight;
    }

    public BigDecimal getLoudnessRmsDbLeft() {
        return loudnessRmsDbLeft;
    }

    public void setLoudnessRmsDbLeft(BigDecimal loudnessRmsDbLeft) {
        this.loudnessRmsDbLeft = loudnessRmsDbLeft;
    }

    public BigDecimal getLoudnessRmsDbRight() {
        return loudnessRmsDbRight;
    }

    public void setLoudnessRmsDbRight(BigDecimal loudnessRmsDbRight) {
        this.loudnessRmsDbRight = loudnessRmsDbRight;
    }

    public BigDecimal getLoudnessPeakDbLeft() {
        return loudnessPeakDbLeft;
    }

    public void setLoudnessPeakDbLeft(BigDecimal loudnessPeakDbLeft) {
        this.loudnessPeakDbLeft = loudnessPeakDbLeft;
    }

    public BigDecimal getLoudnessPeakDbRight() {
        return loudnessPeakDbRight;
    }

    public void setLoudnessPeakDbRight(BigDecimal loudnessPeakDbRight) {
        this.loudnessPeakDbRight = loudnessPeakDbRight;
    }

    public BigDecimal getLoudnessDynamicRangeDbLeft() {
        return loudnessDynamicRangeDbLeft;
    }

    public void setLoudnessDynamicRangeDbLeft(BigDecimal loudnessDynamicRangeDbLeft) {
        this.loudnessDynamicRangeDbLeft = loudnessDynamicRangeDbLeft;
    }

    public BigDecimal getLoudnessDynamicRangeDbRight() {
        return loudnessDynamicRangeDbRight;
    }

    public void setLoudnessDynamicRangeDbRight(BigDecimal loudnessDynamicRangeDbRight) {
        this.loudnessDynamicRangeDbRight = loudnessDynamicRangeDbRight;
    }

    public BigDecimal getLoudnessVolumeBalanceLeftMinusRightDb() {
        return loudnessVolumeBalanceLeftMinusRightDb;
    }

    public void setLoudnessVolumeBalanceLeftMinusRightDb(BigDecimal loudnessVolumeBalanceLeftMinusRightDb) {
        this.loudnessVolumeBalanceLeftMinusRightDb = loudnessVolumeBalanceLeftMinusRightDb;
    }

    public BigDecimal getActivitySnrDbLeft() {
        return activitySnrDbLeft;
    }

    public void setActivitySnrDbLeft(BigDecimal activitySnrDbLeft) {
        this.activitySnrDbLeft = activitySnrDbLeft;
    }

    public BigDecimal getActivitySnrDbRight() {
        return activitySnrDbRight;
    }

    public void setActivitySnrDbRight(BigDecimal activitySnrDbRight) {
        this.activitySnrDbRight = activitySnrDbRight;
    }

    public BigDecimal getActivitySnrDbAverage() {
        return activitySnrDbAverage;
    }

    public void setActivitySnrDbAverage(BigDecimal activitySnrDbAverage) {
        this.activitySnrDbAverage = activitySnrDbAverage;
    }

    public BigDecimal getActivitySpeechDurationMinLeft() {
        return activitySpeechDurationMinLeft;
    }

    public void setActivitySpeechDurationMinLeft(BigDecimal activitySpeechDurationMinLeft) {
        this.activitySpeechDurationMinLeft = activitySpeechDurationMinLeft;
    }

    public BigDecimal getActivitySpeechDurationMinRight() {
        return activitySpeechDurationMinRight;
    }

    public void setActivitySpeechDurationMinRight(BigDecimal activitySpeechDurationMinRight) {
        this.activitySpeechDurationMinRight = activitySpeechDurationMinRight;
    }

    public BigDecimal getActivitySilenceDurationMinLeft() {
        return activitySilenceDurationMinLeft;
    }

    public void setActivitySilenceDurationMinLeft(BigDecimal activitySilenceDurationMinLeft) {
        this.activitySilenceDurationMinLeft = activitySilenceDurationMinLeft;
    }

    public BigDecimal getActivitySilenceDurationMinRight() {
        return activitySilenceDurationMinRight;
    }

    public void setActivitySilenceDurationMinRight(BigDecimal activitySilenceDurationMinRight) {
        this.activitySilenceDurationMinRight = activitySilenceDurationMinRight;
    }

    public BigDecimal getActivitySpeechRatioLeft() {
        return activitySpeechRatioLeft;
    }

    public void setActivitySpeechRatioLeft(BigDecimal activitySpeechRatioLeft) {
        this.activitySpeechRatioLeft = activitySpeechRatioLeft;
    }

    public BigDecimal getActivitySpeechRatioRight() {
        return activitySpeechRatioRight;
    }

    public void setActivitySpeechRatioRight(BigDecimal activitySpeechRatioRight) {
        this.activitySpeechRatioRight = activitySpeechRatioRight;
    }

    public BigDecimal getActivitySpeechOverlapDurationSec() {
        return activitySpeechOverlapDurationSec;
    }

    public void setActivitySpeechOverlapDurationSec(BigDecimal activitySpeechOverlapDurationSec) {
        this.activitySpeechOverlapDurationSec = activitySpeechOverlapDurationSec;
    }

    public BigDecimal getActivityBothSilenceDurationSec() {
        return activityBothSilenceDurationSec;
    }

    public void setActivityBothSilenceDurationSec(BigDecimal activityBothSilenceDurationSec) {
        this.activityBothSilenceDurationSec = activityBothSilenceDurationSec;
    }

    public BigDecimal getActivityNumSilencePeriods() {
        return activityNumSilencePeriods;
    }

    public void setActivityNumSilencePeriods(BigDecimal activityNumSilencePeriods) {
        this.activityNumSilencePeriods = activityNumSilencePeriods;
    }

    public BigDecimal getActivityAvgSilenceDurationSec() {
        return activityAvgSilenceDurationSec;
    }

    public void setActivityAvgSilenceDurationSec(BigDecimal activityAvgSilenceDurationSec) {
        this.activityAvgSilenceDurationSec = activityAvgSilenceDurationSec;
    }

    public BigDecimal getActivityMaxSilenceDurationSec() {
        return activityMaxSilenceDurationSec;
    }

    public void setActivityMaxSilenceDurationSec(BigDecimal activityMaxSilenceDurationSec) {
        this.activityMaxSilenceDurationSec = activityMaxSilenceDurationSec;
    }

    public BigDecimal getConversationNumTurnsLeft() {
        return conversationNumTurnsLeft;
    }

    public void setConversationNumTurnsLeft(BigDecimal conversationNumTurnsLeft) {
        this.conversationNumTurnsLeft = conversationNumTurnsLeft;
    }

    public BigDecimal getConversationNumTurnsRight() {
        return conversationNumTurnsRight;
    }

    public void setConversationNumTurnsRight(BigDecimal conversationNumTurnsRight) {
        this.conversationNumTurnsRight = conversationNumTurnsRight;
    }

    public BigDecimal getConversationNumTurnsTotal() {
        return conversationNumTurnsTotal;
    }

    public void setConversationNumTurnsTotal(BigDecimal conversationNumTurnsTotal) {
        this.conversationNumTurnsTotal = conversationNumTurnsTotal;
    }

    public BigDecimal getConversationAvgGapBetweenTurns() {
        return conversationAvgGapBetweenTurns;
    }

    public void setConversationAvgGapBetweenTurns(BigDecimal conversationAvgGapBetweenTurns) {
        this.conversationAvgGapBetweenTurns = conversationAvgGapBetweenTurns;
    }

    public BigDecimal getConversationTurnBalanceLeft() {
        return conversationTurnBalanceLeft;
    }

    public void setConversationTurnBalanceLeft(BigDecimal conversationTurnBalanceLeft) {
        this.conversationTurnBalanceLeft = conversationTurnBalanceLeft;
    }

    public BigDecimal getConversationTurnBalanceRight() {
        return conversationTurnBalanceRight;
    }

    public void setConversationTurnBalanceRight(BigDecimal conversationTurnBalanceRight) {
        this.conversationTurnBalanceRight = conversationTurnBalanceRight;
    }

    public Transcription getTranscription() {
        return transcription;
    }

    public void setTranscription(Transcription transcription) {
        this.transcription = transcription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AudioQualityMetric that = (AudioQualityMetric) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}