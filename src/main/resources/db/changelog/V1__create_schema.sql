--liquibase formatted sql

--changeset virtucon:1
CREATE TABLE audio_quality_metrics (
    id BIGSERIAL PRIMARY KEY,
    audio_duration_min DECIMAL(10,3) NOT NULL,
    audio_sample_rate INTEGER NOT NULL,
    spectral_centroids_left DECIMAL(10,3) NOT NULL,
    spectral_centroids_right DECIMAL(10,3) NOT NULL,
    spectral_rolloff_left DECIMAL(10,6) NOT NULL,
    spectral_rolloff_right DECIMAL(10,6) NOT NULL,
    spectral_bandwidth_left DECIMAL(10,6) NOT NULL,
    spectral_bandwidth_right DECIMAL(10,6) NOT NULL,
    loudness_rms_db_left DECIMAL(8,3) NOT NULL,
    loudness_rms_db_right DECIMAL(8,3) NOT NULL,
    loudness_peak_db_left DECIMAL(8,3) NOT NULL,
    loudness_peak_db_right DECIMAL(8,3) NOT NULL,
    loudness_dynamic_range_db_left DECIMAL(8,3) NOT NULL,
    loudness_dynamic_range_db_right DECIMAL(8,3) NOT NULL,
    loudness_volume_balance_left_minus_right_db DECIMAL(8,3) NOT NULL,
    activity_snr_db_left DECIMAL(8,3) NOT NULL,
    activity_snr_db_right DECIMAL(8,3) NOT NULL,
    activity_snr_db_average DECIMAL(8,3) NOT NULL,
    activity_speech_duration_min_left DECIMAL(10,3) NOT NULL,
    activity_speech_duration_min_right DECIMAL(10,3) NOT NULL,
    activity_silence_duration_min_left DECIMAL(10,3) NOT NULL,
    activity_silence_duration_min_right DECIMAL(10,3) NOT NULL,
    activity_speech_ratio_left DECIMAL(8,6) NOT NULL,
    activity_speech_ratio_right DECIMAL(8,6) NOT NULL,
    activity_speech_overlap_duration_sec DECIMAL(10,3) NOT NULL,
    activity_both_silence_duration_sec DECIMAL(10,3) NOT NULL,
    activity_num_silence_periods DECIMAL(10,3) NOT NULL,
    activity_avg_silence_duration_sec DECIMAL(8,3) NOT NULL,
    activity_max_silence_duration_sec DECIMAL(8,3) NOT NULL,
    conversation_num_turns_left DECIMAL(10,3) NOT NULL,
    conversation_num_turns_right DECIMAL(10,3) NOT NULL,
    conversation_num_turns_total DECIMAL(10,3) NOT NULL,
    conversation_avg_gap_between_turns DECIMAL(8,3) NOT NULL,
    conversation_turn_balance_left DECIMAL(8,6) NOT NULL,
    conversation_turn_balance_right DECIMAL(8,6) NOT NULL
);

--changeset virtucon:2
CREATE TABLE transcriptions (
    id BIGSERIAL PRIMARY KEY,
    call_id UUID NOT NULL UNIQUE,
    audio_quality_metric_id BIGINT NOT NULL,
    run_config_id UUID NOT NULL,
    generated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (audio_quality_metric_id) REFERENCES audio_quality_metrics(id)
);

--changeset virtucon:3
CREATE TABLE word_metadata (
    id BIGSERIAL PRIMARY KEY,
    left_energy DECIMAL(10,6) NOT NULL,
    right_energy DECIMAL(10,6) NOT NULL,
    left_zcr DECIMAL(10,6) NOT NULL,
    right_zcr DECIMAL(10,6) NOT NULL
);

--changeset virtucon:4
CREATE TABLE words (
    id BIGSERIAL PRIMARY KEY,
    word VARCHAR(255) NOT NULL,
    start_time DECIMAL(10,3) NOT NULL,
    end_time DECIMAL(10,3) NOT NULL,
    confidence DECIMAL(5,4) NOT NULL,
    metadata_id BIGINT NOT NULL,
    transcription_id BIGINT NOT NULL,
    FOREIGN KEY (metadata_id) REFERENCES word_metadata(id),
    FOREIGN KEY (transcription_id) REFERENCES transcriptions(id)
);

--changeset virtucon:5
CREATE TABLE enrichments (
    id BIGSERIAL PRIMARY KEY,
    call_id UUID NOT NULL UNIQUE,
    audio_quality_metric_id BIGINT NOT NULL,
    run_config_id UUID NOT NULL,
    generated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (audio_quality_metric_id) REFERENCES audio_quality_metrics(id)
);

--changeset virtucon:6
CREATE TABLE sentences (
    id BIGSERIAL PRIMARY KEY,
    idx INTEGER NOT NULL,
    text TEXT NOT NULL,
    emotion VARCHAR(50) NOT NULL,
    emotion_score DECIMAL(5,4) NOT NULL,
    speaker VARCHAR(50) NOT NULL,
    start_time DECIMAL(10,3) NOT NULL,
    end_time DECIMAL(10,3) NOT NULL,
    asr_confidence TEXT NOT NULL,
    diarisation_confidence TEXT NOT NULL,
    enrichment_id BIGINT NOT NULL,
    FOREIGN KEY (enrichment_id) REFERENCES enrichments(id)
);

--changeset virtucon:7
CREATE INDEX idx_transcriptions_call_id ON transcriptions(call_id);
CREATE INDEX idx_words_transcription_id ON words(transcription_id);
CREATE INDEX idx_transcriptions_generated_at ON transcriptions(generated_at);
CREATE INDEX idx_enrichments_call_id ON enrichments(call_id);
CREATE INDEX idx_sentences_enrichment_id ON sentences(enrichment_id);
CREATE INDEX idx_enrichments_generated_at ON enrichments(generated_at);

--changeset virtucon:8
CREATE TABLE files (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    url VARCHAR(1000) NOT NULL UNIQUE
);

--changeset virtucon:9
CREATE TABLE tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    file_id UUID NOT NULL UNIQUE,
    task_type VARCHAR(50) NOT NULL CHECK (task_type IN ('TRANSCRIPTION', 'ENRICHMENT', 'RELEVANCY', 'NOT_ASSIGNED')),
    task_status VARCHAR(50) NOT NULL CHECK (task_status IN ('READY', 'IN_PROGRESS', 'COMPLETED', 'FAILED', 'BLOCKED')),
    processing_start TIMESTAMP,
    processing_end TIMESTAMP,
    owner VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE
);

--changeset virtucon:10
CREATE INDEX idx_files_url ON files(url);
CREATE INDEX idx_tasks_file_id ON tasks(file_id);
CREATE INDEX idx_tasks_task_type ON tasks(task_type);
CREATE INDEX idx_tasks_task_status ON tasks(task_status);
CREATE INDEX idx_tasks_owner ON tasks(owner);
CREATE INDEX idx_tasks_created_at ON tasks(created_at);