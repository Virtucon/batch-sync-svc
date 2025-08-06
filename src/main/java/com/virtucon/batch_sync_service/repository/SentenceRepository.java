package com.virtucon.batch_sync_service.repository;

import com.virtucon.batch_sync_service.entity.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long> {
    
    List<Sentence> findByEnrichmentIdOrderByIdx(Long enrichmentId);
}