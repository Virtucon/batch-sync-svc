package com.virtucon.batch_sync_service.repository;

import com.virtucon.batch_sync_service.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    
    Optional<FileEntity> findByUrl(String url);
    
    boolean existsByUrl(String url);
}