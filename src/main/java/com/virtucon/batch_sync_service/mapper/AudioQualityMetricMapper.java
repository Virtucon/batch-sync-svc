package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.AudioQualityMetricDTO;
import com.virtucon.batch_sync_service.entity.AudioQualityMetric;
import org.springframework.stereotype.Component;

@Component
public class AudioQualityMetricMapper {

    public AudioQualityMetric toEntity(AudioQualityMetricDTO dto) {
        if (dto == null) {
            return null;
        }

        return new AudioQualityMetric(
                dto.audioDurationMin(),
                dto.audioSampleRate(),
                dto.spectralCentroidsLeft(),
                dto.spectralCentroidsRight(),
                dto.spectralRolloffLeft(),
                dto.spectralRolloffRight(),
                dto.spectralBandwidthLeft(),
                dto.spectralBandwidthRight(),
                dto.loudnessRmsDbLeft(),
                dto.loudnessRmsDbRight(),
                dto.loudnessPeakDbLeft(),
                dto.loudnessPeakDbRight(),
                dto.loudnessDynamicRangeDbLeft(),
                dto.loudnessDynamicRangeDbRight(),
                dto.loudnessVolumeBalanceLeftMinusRightDb(),
                dto.activitySnrDbLeft(),
                dto.activitySnrDbRight(),
                dto.activitySnrDbAverage(),
                dto.activitySpeechDurationMinLeft(),
                dto.activitySpeechDurationMinRight(),
                dto.activitySilenceDurationMinLeft(),
                dto.activitySilenceDurationMinRight(),
                dto.activitySpeechRatioLeft(),
                dto.activitySpeechRatioRight(),
                dto.activitySpeechOverlapDurationSec(),
                dto.activityBothSilenceDurationSec(),
                dto.activityNumSilencePeriods(),
                dto.activityAvgSilenceDurationSec(),
                dto.activityMaxSilenceDurationSec(),
                dto.conversationNumTurnsLeft(),
                dto.conversationNumTurnsRight(),
                dto.conversationNumTurnsTotal(),
                dto.conversationAvgGapBetweenTurns(),
                dto.conversationTurnBalanceLeft(),
                dto.conversationTurnBalanceRight()
        );
    }
}