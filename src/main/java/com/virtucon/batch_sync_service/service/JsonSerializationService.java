package com.virtucon.batch_sync_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonSerializationService {

    private final ObjectMapper objectMapper;

    public JsonSerializationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    public <T> List<T> deserializeList(String json, Class<T> elementType) {
        try {
            return objectMapper.readValue(json, 
                objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to list", e);
        }
    }

    public List<String> deserializeStringList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to string list", e);
        }
    }

    public List<Double> deserializeDoubleList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Double>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to double list", e);
        }
    }
}