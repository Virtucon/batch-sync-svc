package com.virtucon.batch_sync_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "words")
public class Word {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "start_time", nullable = false, precision = 10, scale = 3)
    private BigDecimal start;

    @Column(name = "end_time", nullable = false, precision = 10, scale = 3)
    private BigDecimal end;

    @Column(name = "confidence", nullable = false, precision = 5, scale = 4)
    private BigDecimal confidence;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "metadata_id")
    private WordMetadata metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transcription_id", nullable = false)
    private Transcription transcription;

    protected Word() {
    }

    public Word(String word, BigDecimal start, BigDecimal end, BigDecimal confidence, WordMetadata metadata) {
        this.word = word;
        this.start = start;
        this.end = end;
        this.confidence = confidence;
        this.metadata = metadata;
        if (metadata != null) {
            metadata.setWord(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public BigDecimal getStart() {
        return start;
    }

    public void setStart(BigDecimal start) {
        this.start = start;
    }

    public BigDecimal getEnd() {
        return end;
    }

    public void setEnd(BigDecimal end) {
        this.end = end;
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public void setConfidence(BigDecimal confidence) {
        this.confidence = confidence;
    }

    public WordMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(WordMetadata metadata) {
        this.metadata = metadata;
        if (metadata != null) {
            metadata.setWord(this);
        }
    }

    public Transcription getTranscription() {
        return transcription;
    }

    public void setTranscription(Transcription transcription) {
        this.transcription = transcription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(id, word.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}