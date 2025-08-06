package com.virtucon.batch_sync_service.controller;

import com.virtucon.batch_sync_service.dto.CreateResponseDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionResponseDTO;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.response.ApiResponse;
import com.virtucon.batch_sync_service.service.TranscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/transcriptions")
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    public TranscriptionController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateResponseDTO>> createTranscription(@Valid @RequestBody TranscriptionDTO transcriptionDTO) {
        Transcription savedTranscription = transcriptionService.createTranscription(transcriptionDTO);
        
        CreateResponseDTO data = new CreateResponseDTO(
                savedTranscription.getId(),
                savedTranscription.getCallId()
        );
        
        ApiResponse<CreateResponseDTO> response = ApiResponse.success("Transcription saved successfully.", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/call/{callId}")
    public ResponseEntity<ApiResponse<TranscriptionResponseDTO>> getTranscriptionByCallId(@PathVariable UUID callId) {
        return transcriptionService.findByCallId(callId)
                .map(transcription -> {
                    TranscriptionResponseDTO data = new TranscriptionResponseDTO(
                            transcription.getId(),
                            transcription.getCallId(),
                            transcription.getRunConfigId(),
                            transcription.getGeneratedAt(),
                            transcription.getWords().size()
                    );
                    ApiResponse<TranscriptionResponseDTO> response = ApiResponse.success("Transcription found", data);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<TranscriptionResponseDTO> response = ApiResponse.error("Transcription with call ID " + callId + " not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

}