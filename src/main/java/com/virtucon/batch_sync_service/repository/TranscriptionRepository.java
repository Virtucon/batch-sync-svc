package com.virtucon.batch_sync_service.repository;

import com.virtucon.batch_sync_service.entity.Transcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
    
    Optional<Transcription> findByCallId(UUID callId);
    
    @Query("SELECT t FROM Transcription t LEFT JOIN FETCH t.audioQualityMetric WHERE t.callId = :callId")
    Optional<Transcription> findByCallIdWithAudioMetrics(@Param("callId") UUID callId);
    
    @Query("SELECT t FROM Transcription t LEFT JOIN FETCH t.words WHERE t.callId = :callId")
    Optional<Transcription> findByCallIdWithWords(@Param("callId") UUID callId);
    
    boolean existsByCallId(UUID callId);
    
    @Query("SELECT COUNT(t) FROM Transcription t WHERE t.callId = :callId")
    long countByCallId(@Param("callId") UUID callId);
}