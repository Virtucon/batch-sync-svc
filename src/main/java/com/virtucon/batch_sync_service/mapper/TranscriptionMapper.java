package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.AudioQualityMetricDTO;
import com.virtucon.batch_sync_service.dto.MetadataDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.dto.WordDTO;
import com.virtucon.batch_sync_service.entity.AudioQualityMetric;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.entity.Word;
import com.virtucon.batch_sync_service.entity.WordMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TranscriptionMapper {

    public Transcription toEntity(TranscriptionDTO dto) {
        if (dto == null) {
            return null;
        }

        AudioQualityMetric audioQualityMetric = toAudioQualityMetricEntity(dto.audioQualityMetric());
        List<Word> words = toWordEntities(dto.words());

        return new Transcription(
                dto.callId(),
                audioQualityMetric,
                dto.runConfigId(),
                words,
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

    private List<Word> toWordEntities(List<WordDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        List<Word> words = new ArrayList<>();
        for (WordDTO dto : dtos) {
            words.add(toWordEntity(dto));
        }
        return words;
    }

    private Word toWordEntity(WordDTO dto) {
        if (dto == null) {
            return null;
        }

        WordMetadata metadata = toWordMetadataEntity(dto.metadata());
        
        return new Word(
                dto.word(),
                dto.start(),
                dto.end(),
                dto.confidence(),
                metadata
        );
    }

    private WordMetadata toWordMetadataEntity(MetadataDTO dto) {
        if (dto == null) {
            return null;
        }

        return new WordMetadata(
                dto.leftEnergy(),
                dto.rightEnergy(),
                dto.leftZcr(),
                dto.rightZcr()
        );
    }
}