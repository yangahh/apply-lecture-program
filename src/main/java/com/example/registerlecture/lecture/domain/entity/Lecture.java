package com.example.registerlecture.lecture.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String speakerName;

    @NotNull
    private int maxCapacity;

    @NotNull
    @ColumnDefault("0")
    private int currentCapacity;

    @NotNull
    private LocalDateTime lectureDateTime;

    @NotNull
    @Column(name = "registration_start_datetime", columnDefinition = "DATETIME")
    private LocalDateTime registrationStartDateTime;

    @NotNull
    @Column(name = "registration_end_datetime", columnDefinition = "DATETIME")
    private LocalDateTime registrationEndDateTime;

    @Builder
    private Lecture(String title, String speakerName, Integer maxCapacity, Integer currentCapacity, LocalDateTime lectureDateTime, LocalDateTime registrationEndDateTime, LocalDateTime registrationStartDateTime) {
        this.title = title;
        this.speakerName = speakerName;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.lectureDateTime = lectureDateTime;
        this.registrationEndDateTime = registrationEndDateTime;
        this.registrationStartDateTime = registrationStartDateTime;
    }

    public boolean isFull() {
        return maxCapacity <= currentCapacity;
    }
}
