package com.virtucon.batch_sync_service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtucon.batch_sync_service.dto.AudioQualityMetricDTO;
import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.dto.SentenceDTO;
import com.virtucon.batch_sync_service.entity.AudioQualityMetric;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.entity.Sentence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnrichmentMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Enrichment toEntity(EnrichmentDTO dto) {
        if (dto == null) {
            return null;
        }

        AudioQualityMetric audioQualityMetric = toAudioQualityMetricEntity(dto.audioQualityMetric());
        List<Sentence> sentences = toSentenceEntities(dto.sentences());

        return new Enrichment(
                dto.callId(),
                audioQualityMetric,
                dto.runConfigId(),
                sentences,
                dto.generatedAt()
        );
    }

    private AudioQualityMetric toAudioQualityMetricEntity(AudioQualityMetricDTO dto) {
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

    private List<Sentence> toSentenceEntities(List<SentenceDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        List<Sentence> sentences = new ArrayList<>();
        for (SentenceDTO dto : dtos) {
            sentences.add(toSentenceEntity(dto));
        }
        return sentences;
    }

    private Sentence toSentenceEntity(SentenceDTO dto) {
        if (dto == null) {
            return null;
        }

        String asrConfidenceJson = serializeToJson(dto.asrConfidence());
        String diarisationConfidenceJson = serializeToJson(dto.diarisationConfidence());

        return new Sentence(
                dto.idx(),
                dto.text(),
                dto.emotion(),
                dto.emotionScore(),
                dto.speaker(),
                dto.start(),
                dto.end(),
                asrConfidenceJson,
                diarisationConfidenceJson
        );
    }

    private String serializeToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }
}