package com.virtucon.batch_sync_service.service;

import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.entity.Transcription;


public interface TranscriptionService extends BaseCallService<Transcription, TranscriptionDTO> {
    
    Transcription createTranscription(TranscriptionDTO transcriptionDTO);
}