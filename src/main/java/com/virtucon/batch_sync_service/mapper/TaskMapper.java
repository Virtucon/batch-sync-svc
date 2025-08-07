package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.dto.UpdateTaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskMapper {

    @Mapping(target = "fileId", source = "file.id")
    @Mapping(target = "fileUrl", source = "file.url")
    TaskDto toDto(TaskEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TaskEntity toEntity(CreateTaskDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(UpdateTaskDto dto, @MappingTarget TaskEntity entity);
}