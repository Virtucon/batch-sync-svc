package com.virtucon.batch_sync_service.exception;

import java.time.Instant;

public record ErrorDetails(
    Instant timestamp,
    String path,
    String errorCode,
    String message,
    Object details
) {
    public ErrorDetails(Instant timestamp, String path, String errorCode, String message) {
        this(timestamp, path, errorCode, message, null);
    }
}