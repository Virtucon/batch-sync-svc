package com.virtucon.batch_sync_service.controller;

import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.service.TranscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/transcriptions")
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    @Autowired
    public TranscriptionController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTranscription(@Valid @RequestBody TranscriptionDTO transcriptionDTO) {
        try {
            Transcription savedTranscription = transcriptionService.createTranscription(transcriptionDTO);
            
            Map<String, Object> response = Map.of(
                    "message", "Transcription created successfully",
                    "id", savedTranscription.getId(),
                    "callId", savedTranscription.getCallId()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = Map.of(
                    "error", "Duplicate transcription",
                    "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                    "error", "Internal server error",
                    "message", "Failed to create transcription"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/call/{callId}")
    public ResponseEntity<Map<String, Object>> getTranscriptionByCallId(@PathVariable UUID callId) {
        return transcriptionService.findByCallId(callId)
                .map(transcription -> {
                    Map<String, Object> response = Map.of(
                            "id", transcription.getId(),
                            "callId", transcription.getCallId(),
                            "runConfigId", transcription.getRunConfigId(),
                            "generatedAt", transcription.getGeneratedAt(),
                            "wordCount", transcription.getWords().size()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> errorResponse = Map.of(
                            "error", "Not found",
                            "message", "Transcription with call ID " + callId + " not found"
                    );
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                });
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        Map<String, Object> errorResponse = Map.of(
                "error", "Unexpected error",
                "message", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}