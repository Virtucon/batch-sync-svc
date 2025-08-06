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
CREATE INDEX idx_transcriptions_call_id ON transcriptions(call_id);
CREATE INDEX idx_words_transcription_id ON words(transcription_id);
CREATE INDEX idx_transcriptions_generated_at ON transcriptions(generated_at);