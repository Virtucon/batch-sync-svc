package com.virtucon.batch_sync_service.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "enrichments")
public class Enrichment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "call_id", nullable = false)
    private UUID callId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_quality_metric_id")
    private AudioQualityMetric audioQualityMetric;

    @Column(name = "run_config_id", nullable = false)
    private UUID runConfigId;

    @OneToMany(mappedBy = "enrichment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sentence> sentences = new ArrayList<>();

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    protected Enrichment() {
    }

    public Enrichment(UUID callId, AudioQualityMetric audioQualityMetric, 
                     UUID runConfigId, List<Sentence> sentences, Instant generatedAt) {
        this.callId = callId;
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