package com.virtucon.batch_sync_service.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrichments")
public class Enrichment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "call_id", nullable = false)
    private UUID callId;

    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_quality_metric_id")
    private AudioQualityMetric audioQualityMetric;

    @Column(name = "run_config_id", nullable = false)
    private UUID runConfigId;

    @OneToMany(mappedBy = "enrichment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @org.hibernate.annotations.BatchSize(size = 15)
    private List<Sentence> sentences = new ArrayList<>();

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    protected Enrichment() {
    }

    public Enrichment(UUID callId, UUID taskId, AudioQualityMetric audioQualityMetric, 
                     UUID runConfigId, List<Sentence> sentences, Instant generatedAt) {
        this.callId = callId;
        this.taskId = taskId;
        this.audioQualityMetric = audioQualityMetric;
        this.runConfigId = runConfigId;
        this.sentences = sentences != null ? sentences : new ArrayList<>();
        this.generatedAt = generatedAt;
        
        if (audioQualityMetric != null) {
            audioQualityMetric.setEnrichment(this);
        }
        
        for (Sentence sentence : this.sentences) {
            sentence.setEnrichment(this);
        }
    }

    public Long getId() {
        return id;
    }

    public UUID getCallId() {
        return callId;
    }

    public void setCallId(UUID callId) {
        this.callId = callId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public AudioQualityMetric getAudioQualityMetric() {
        return audioQualityMetric;
    }

    public void setAudioQualityMetric(AudioQualityMetric audioQualityMetric) {
        this.audioQualityMetric = audioQualityMetric;
        if (audioQualityMetric != null) {
            audioQualityMetric.setEnrichment(this);
        }
    }

    public UUID getRunConfigId() {
        return runConfigId;
    }

    public void setRunConfigId(UUID runConfigId) {
        this.runConfigId = runConfigId;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences != null ? sentences : new ArrayList<>();
        for (Sentence sentence : this.sentences) {
            sentence.setEnrichment(this);
        }
    }

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
        sentence.setEnrichment(this);
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrichment that = (Enrichment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}