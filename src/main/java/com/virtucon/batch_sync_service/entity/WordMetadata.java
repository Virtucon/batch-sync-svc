package com.virtucon.batch_sync_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "word_metadata")
public class WordMetadata {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "left_energy", nullable = false, precision = 10, scale = 6)
    private BigDecimal leftEnergy;

    @Column(name = "right_energy", nullable = false, precision = 10, scale = 6)
    private BigDecimal rightEnergy;

    @Column(name = "left_zcr", nullable = false, precision = 10, scale = 6)
    private BigDecimal leftZcr;

    @Column(name = "right_zcr", nullable = false, precision = 10, scale = 6)
    private BigDecimal rightZcr;

    @OneToOne(mappedBy = "metadata", fetch = FetchType.LAZY)
    private Word word;

    protected WordMetadata() {
    }

    public WordMetadata(BigDecimal leftEnergy, BigDecimal rightEnergy, BigDecimal leftZcr, BigDecimal rightZcr) {
        this.leftEnergy = leftEnergy;
        this.rightEnergy = rightEnergy;
        this.leftZcr = leftZcr;
        this.rightZcr = rightZcr;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getLeftEnergy() {
        return leftEnergy;
    }

    public void setLeftEnergy(BigDecimal leftEnergy) {
        this.leftEnergy = leftEnergy;
    }

    public BigDecimal getRightEnergy() {
        return rightEnergy;
    }

    public void setRightEnergy(BigDecimal rightEnergy) {
        this.rightEnergy = rightEnergy;
    }

    public BigDecimal getLeftZcr() {
        return leftZcr;
    }

    public void setLeftZcr(BigDecimal leftZcr) {
        this.leftZcr = leftZcr;
    }

    public BigDecimal getRightZcr() {
        return rightZcr;
    }

    public void setRightZcr(BigDecimal rightZcr) {
        this.rightZcr = rightZcr;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordMetadata that = (WordMetadata) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}