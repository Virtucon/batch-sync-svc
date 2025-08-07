package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.MetadataDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionResponseDTO;
import com.virtucon.batch_sync_service.dto.WordDTO;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.entity.Word;
import com.virtucon.batch_sync_service.entity.WordMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AudioQualityMetricMapper.class})
public interface TranscriptionMapperNew {

    Transcription toEntity(TranscriptionDTO dto);
    
    Word toEntity(WordDTO dto);
    
    WordMetadata toEntity(MetadataDTO dto);
    
    @Mapping(target = "wordCount", expression = "java(entity.getWords().size())")
    TranscriptionResponseDTO toResponseDto(Transcription entity);
}