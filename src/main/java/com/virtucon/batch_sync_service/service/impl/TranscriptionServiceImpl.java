package com.virtucon.batch_sync_service.service.impl;

import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.exception.EntityAlreadyExistsException;
import com.virtucon.batch_sync_service.mapper.TranscriptionMapper;
import com.virtucon.batch_sync_service.repository.TranscriptionRepository;
import com.virtucon.batch_sync_service.service.TranscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TranscriptionServiceImpl implements TranscriptionService {

    private final TranscriptionRepository transcriptionRepository;
    private final TranscriptionMapper transcriptionMapper;

    public TranscriptionServiceImpl(TranscriptionRepository transcriptionRepository, 
                                   TranscriptionMapper transcriptionMapper) {
        this.transcriptionRepository = transcriptionRepository;
        this.transcriptionMapper = transcriptionMapper;
    }

    @Override
    public Transcription createEntity(TranscriptionDTO transcriptionDTO) {
        return createTranscription(transcriptionDTO);
    }

    @Override
    public Transcription createTranscription(TranscriptionDTO transcriptionDTO) {
        if (existsByCallId(transcriptionDTO.callId())) {
            throw new EntityAlreadyExistsException("Transcription", transcriptionDTO.callId());
        }
        
        Transcription transcription = transcriptionMapper.toEntity(transcriptionDTO);
        return transcriptionRepository.save(transcription);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transcription> findByCallId(UUID callId) {
        return transcriptionRepository.findByCallId(callId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCallId(UUID callId) {
        return transcriptionRepository.existsByCallId(callId);
    }
}