package com.virtucon.batch_sync_service.service;

import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.entity.Transcription;

import java.util.Optional;
import java.util.UUID;

public interface TranscriptionService {
    
    Transcription createTranscription(TranscriptionDTO transcriptionDTO);
    
    Optional<Transcription> findByCallId(UUID callId);
    
    boolean existsByCallId(UUID callId);
}