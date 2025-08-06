package com.virtucon.batch_sync_service.controller;

import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> createTranscription(@Valid @RequestBody TranscriptionDTO transcriptionDTO) {
        try {
            Transcription savedTranscription = transcriptionService.createTranscription(transcriptionDTO);
            
            Map<String, Object> data = Map.of(
                    "id", savedTranscription.getId(),
                    "callId", savedTranscription.getCallId()
            );
            
            ApiResponse<Map<String, Object>> response = ApiResponse.success("Transcription saved successfully.", data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            ApiResponse<Map<String, Object>> response = ApiResponse.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (Exception e) {
            ApiResponse<Map<String, Object>> response = ApiResponse.error("Failed to create transcription");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/call/{callId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTranscriptionByCallId(@PathVariable UUID callId) {
        return transcriptionService.findByCallId(callId)
                .map(transcription -> {
                    Map<String, Object> data = Map.of(
                            "id", transcription.getId(),
                            "callId", transcription.getCallId(),
                            "runConfigId", transcription.getRunConfigId(),
                            "generatedAt", transcription.getGeneratedAt(),
                            "wordCount", transcription.getWords().size()
                    );
                    ApiResponse<Map<String, Object>> response = ApiResponse.success("Transcription found", data);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Map<String, Object>> response = ApiResponse.error("Transcription with call ID " + callId + " not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception e) {
        ApiResponse<Object> response = ApiResponse.error("Unexpected error: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}