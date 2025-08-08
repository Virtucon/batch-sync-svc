package com.virtucon.batch_sync_service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.dto.SentenceDTO;
import com.virtucon.batch_sync_service.entity.AudioQualityMetric;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.entity.Sentence;
import com.virtucon.batch_sync_service.service.JsonSerializationService;

@Component
public class EnrichmentMapper {

    private final AudioQualityMetricMapper audioQualityMetricMapper;
    private final JsonSerializationService jsonSerializationService;

    public EnrichmentMapper(AudioQualityMetricMapper audioQualityMetricMapper,
                          JsonSerializationService jsonSerializationService) {
        this.audioQualityMetricMapper = audioQualityMetricMapper;
        this.jsonSerializationService = jsonSerializationService;
    }

    public Enrichment toEntity(EnrichmentDTO dto) {
        if (dto == null) {
            return null;
        }

        AudioQualityMetric audioQualityMetric = audioQualityMetricMapper.toEntity(dto.audioQualityMetric());
        List<Sentence> sentences = toSentenceEntities(dto.sentences());

        return new Enrichment(
                dto.callId(),
                dto.taskId(),
                audioQualityMetric,
                dto.runConfigId(),
                sentences,
                dto.generatedAt()
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

        String asrConfidenceJson = jsonSerializationService.serialize(dto.asrConfidence());
        String diarisationConfidenceJson = jsonSerializationService.serialize(dto.diarisationConfidence());

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

}