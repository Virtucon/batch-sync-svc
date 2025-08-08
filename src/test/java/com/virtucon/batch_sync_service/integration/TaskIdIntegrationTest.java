package com.virtucon.batch_sync_service.integration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtucon.batch_sync_service.TestcontainersConfiguration;
import com.virtucon.batch_sync_service.dto.EnrichmentDTO;
import com.virtucon.batch_sync_service.dto.TranscriptionDTO;
import com.virtucon.batch_sync_service.entity.Enrichment;
import com.virtucon.batch_sync_service.entity.Transcription;
import com.virtucon.batch_sync_service.service.EnrichmentService;
import com.virtucon.batch_sync_service.service.TranscriptionService;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@Transactional
class TaskIdIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private TranscriptionService transcriptionService;

    @Test
    void shouldDeserializeEnrichmentJsonWithTaskId() throws Exception {
        // Read the enrichment example JSON file and parse it
        byte[] enrichmentJsonBytes = getClass().getClassLoader()
                .getResourceAsStream("enrichment-example.json")
                .readAllBytes();
        String jsonContent = new String(enrichmentJsonBytes, java.nio.charset.StandardCharsets.UTF_8);
        
        EnrichmentDTO enrichmentDTO = objectMapper.readValue(jsonContent, EnrichmentDTO.class);
        
        // Verify the task_id field is properly deserialized
        assertThat(enrichmentDTO.taskId()).isNotNull();
        assertThat(enrichmentDTO.taskId()).isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertThat(enrichmentDTO.callId()).isNotNull();
        assertThat(enrichmentDTO.callId()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        
        // Verify we can create an entity from this DTO
        Enrichment enrichment = enrichmentService.createEnrichment(enrichmentDTO);
        assertThat(enrichment.getId()).isNotNull();
        assertThat(enrichment.getTaskId()).isEqualTo(enrichmentDTO.taskId());
        assertThat(enrichment.getCallId()).isEqualTo(enrichmentDTO.callId());
    }

    @Test
    void shouldDeserializeTranscriptionJsonWithTaskId() throws Exception {
        // Read the transcription example JSON file and parse it
        byte[] transcriptionJsonBytes = getClass().getClassLoader()
                .getResourceAsStream("transcription-example.json")
                .readAllBytes();
        String jsonContent = new String(transcriptionJsonBytes, java.nio.charset.StandardCharsets.UTF_8);
        
        TranscriptionDTO transcriptionDTO = objectMapper.readValue(jsonContent, TranscriptionDTO.class);
        
        // Verify the task_id field is properly deserialized
        assertThat(transcriptionDTO.taskId()).isNotNull();
        assertThat(transcriptionDTO.taskId()).isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertThat(transcriptionDTO.callId()).isNotNull();
        assertThat(transcriptionDTO.callId()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        
        // Verify we can create an entity from this DTO
        Transcription transcription = transcriptionService.createTranscription(transcriptionDTO);
        assertThat(transcription.getId()).isNotNull();
        assertThat(transcription.getTaskId()).isEqualTo(transcriptionDTO.taskId());
        assertThat(transcription.getCallId()).isEqualTo(transcriptionDTO.callId());
    }

    @Test
    void shouldQueryByTaskId() {
        // Create some test data
        UUID taskId = UUID.randomUUID();

        // This test verifies that the service methods exist and can be called
        assertThat(enrichmentService.findByTaskId(taskId)).isEmpty();
        assertThat(enrichmentService.existsByTaskId(taskId)).isFalse();
        
        assertThat(transcriptionService.findByTaskId(taskId)).isEmpty();
        assertThat(transcriptionService.existsByTaskId(taskId)).isFalse();
    }
}
