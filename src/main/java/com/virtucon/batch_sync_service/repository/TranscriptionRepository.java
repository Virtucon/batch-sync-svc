package com.virtucon.batch_sync_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtucon.batch_sync_service.entity.Transcription;

@Repository
public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {
    
    Optional<Transcription> findByCallId(UUID callId);
    
    Optional<Transcription> findByTaskId(UUID taskId);
    
    @Query("SELECT t FROM Transcription t LEFT JOIN FETCH t.audioQualityMetric WHERE t.callId = :callId")
    Optional<Transcription> findByCallIdWithAudioMetrics(@Param("callId") UUID callId);
    
    @Query("SELECT t FROM Transcription t LEFT JOIN FETCH t.audioQualityMetric WHERE t.taskId = :taskId")
    Optional<Transcription> findByTaskIdWithAudioMetrics(@Param("taskId") UUID taskId);
    
    @Query("SELECT t FROM Transcription t LEFT JOIN FETCH t.words WHERE t.callId = :callId")
    Optional<Transcription> findByCallIdWithWords(@Param("callId") UUID callId);
    
    @Query("SELECT t FROM Transcription t LEFT JOIN FETCH t.words WHERE t.taskId = :taskId")
    Optional<Transcription> findByTaskIdWithWords(@Param("taskId") UUID taskId);
    
    boolean existsByCallId(UUID callId);
    
    boolean existsByTaskId(UUID taskId);
    
    @Query("SELECT COUNT(t) FROM Transcription t WHERE t.callId = :callId")
    long countByCallId(@Param("callId") UUID callId);
    
    @Query("SELECT COUNT(t) FROM Transcription t WHERE t.taskId = :taskId")
    long countByTaskId(@Param("taskId") UUID taskId);
}