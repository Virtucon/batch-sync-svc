package com.virtucon.batch_sync_service.repository;

import com.virtucon.batch_sync_service.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    
    List<Word> findByTranscriptionId(Long transcriptionId);
}