package com.virtucon.batch_sync_service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.virtucon.batch_sync_service.dto.MetadataDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.dto.WordDTO;
import com.virtucon.batch_sync_service.entity.AudioQualityMetric;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.entity.Word;
import com.virtucon.batch_sync_service.entity.WordMetadata;

@Component
public class TranscriptionMapper {

    private final AudioQualityMetricMapper audioQualityMetricMapper;

    public TranscriptionMapper(AudioQualityMetricMapper audioQualityMetricMapper) {
        this.audioQualityMetricMapper = audioQualityMetricMapper;
    }

    public Transcription toEntity(TranscriptionDTO dto) {
        if (dto == null) {
            return null;
        }

        AudioQualityMetric audioQualityMetric = audioQualityMetricMapper.toEntity(dto.audioQualityMetric());
        List<Word> words = toWordEntities(dto.words());

        return new Transcription(
                dto.callId(),
                dto.taskId(),
                audioQualityMetric,
                dto.runConfigId(),
                words,
                dto.generatedAt()
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