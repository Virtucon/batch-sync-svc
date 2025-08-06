package com.virtucon.batch_sync_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "sentences")
public class Sentence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idx", nullable = false)
    private Integer idx;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "emotion", nullable = false, length = 50)
    private String emotion;

    @Column(name = "emotion_score", nullable = false, precision = 5, scale = 4)
    private BigDecimal emotionScore;

    @Column(name = "speaker", nullable = false, length = 50)
    private String speaker;

    @Column(name = "start_time", nullable = false, precision = 10, scale = 3)
    private BigDecimal startTime;

    @Column(name = "end_time", nullable = false, precision = 10, scale = 3)
    private BigDecimal endTime;

    @Column(name = "asr_confidence", nullable = false, columnDefinition = "TEXT")
    private String asrConfidence;

    @Column(name = "diarisation_confidence", nullable = false, columnDefinition = "TEXT")
    private String diarisationConfidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrichment_id")
    private Enrichment enrichment;

    protected Sentence() {
    }

    public Sentence(Integer idx, String text, String emotion, BigDecimal emotionScore, String speaker, 
                   BigDecimal startTime, BigDecimal endTime, String asrConfidence, String diarisationConfidence) {
        this.idx = idx;
        this.text = text;
        this.emotion = emotion;
        this.emotionScore = emotionScore;
        this.speaker = speaker;
        this.startTime = startTime;
        this.endTime = endTime;
        this.asrConfidence = asrConfidence;
        this.diarisationConfidence = diarisationConfidence;
    }

    public Long getId() {
        return id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public BigDecimal getEmotionScore() {
        return emotionScore;
    }

    public void setEmotionScore(BigDecimal emotionScore) {
        this.emotionScore = emotionScore;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public BigDecimal getStartTime() {
        return startTime;
    }

    public void setStartTime(BigDecimal startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getEndTime() {
        return endTime;
    }

    public void setEndTime(BigDecimal endTime) {
        this.endTime = endTime;
    }

    public String getAsrConfidence() {
        return asrConfidence;
    }

    public void setAsrConfidence(String asrConfidence) {
        this.asrConfidence = asrConfidence;
    }

    public String getDiarisationConfidence() {
        return diarisationConfidence;
    }

    public void setDiarisationConfidence(String diarisationConfidence) {
        this.diarisationConfidence = diarisationConfidence;
    }

    public Enrichment getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(Enrichment enrichment) {
        this.enrichment = enrichment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(id, sentence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}