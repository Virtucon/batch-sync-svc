--liquibase formatted sql

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