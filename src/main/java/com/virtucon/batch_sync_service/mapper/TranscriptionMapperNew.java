package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.MetadataDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionResponseDTO;
import com.virtucon.batch_sync_service.dto.WordDTO;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.entity.Word;
import com.virtucon.batch_sync_service.entity.WordMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TranscriptionMapperNew {

    private final AudioQualityMetricMapper audioQualityMetricMapper;

    public TranscriptionMapperNew(AudioQualityMetricMapper audioQualityMetricMapper) {
        this.audioQualityMetricMapper = audioQualityMetricMapper;
    }

    public Transcription toEntity(TranscriptionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        List<Word> words = new ArrayList<>();
        if (dto.words() != null) {
            for (WordDTO wordDto : dto.words()) {
                words.add(toEntity(wordDto));
            }
        }
        
        return new Transcription(
            dto.callId(),
            audioQualityMetricMapper.toEntity(dto.audioQualityMetric()),
            dto.runConfigId(),
            words,
            dto.generatedAt()
        );
    }
    
    public Word toEntity(WordDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new Word(
            dto.word(),
            dto.start(),
            dto.end(),
            dto.confidence(),
            toEntity(dto.metadata())
        );
    }
    
    public WordMetadata toEntity(MetadataDTO dto) {
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
    
    public TranscriptionResponseDTO toResponseDto(Transcription entity) {
        if (entity == null) {
            return null;
        }
        
        return new TranscriptionResponseDTO(
            entity.getId(),
            entity.getCallId(),
            entity.getRunConfigId(),
            entity.getGeneratedAt(),
            entity.getWords() != null ? entity.getWords().size() : 0
        );
    }
}