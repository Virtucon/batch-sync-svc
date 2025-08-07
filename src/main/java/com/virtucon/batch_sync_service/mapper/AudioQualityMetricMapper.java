package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.AudioQualityMetricDTO;
import com.virtucon.batch_sync_service.entity.AudioQualityMetric;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AudioQualityMetricMapper {

    AudioQualityMetric toEntity(AudioQualityMetricDTO dto);
}