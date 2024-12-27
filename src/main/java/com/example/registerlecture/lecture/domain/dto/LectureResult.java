package com.example.registerlecture.lecture.domain.dto;

import com.example.registerlecture.lecture.domain.entity.Lecture;

import java.time.LocalDateTime;

public record LectureResult(
        long id,
        String title,
        String speakerName,
        LocalDateTime lectureDateTime,
        LocalDateTime registrationStartDateTime,
        LocalDateTime registrationEndDateTime,
        int maxCapacity,
        boolean isFull
) {
    public static LectureResult from(Lecture lecture) {
        return new LectureResult(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getSpeakerName(),
                lecture.getLectureDateTime(),
                lecture.getRegistrationStartDateTime(),
                lecture.getRegistrationEndDateTime(),
                lecture.getMaxCapacity(),
                lecture.isFull());
    }
}
