package com.example.registerlecture.lecture.domain.dto;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;

import java.time.LocalDateTime;

public record LectureRegistrationResult(
        long userId,
        long lectureId,
        long registrationId,
        String title,
        String speakerName,
        LocalDateTime lectureDateTime,
        LocalDateTime registeredAt
) {
    public static LectureRegistrationResult from(LectureRegistration lectureRegistration) {
        Lecture lecture = lectureRegistration.getLecture();
        return new LectureRegistrationResult(
                lectureRegistration.getUser().getId(),
                lecture.getId(),
                lectureRegistration.getId(),
                lecture.getTitle(),
                lecture.getSpeakerName(),
                lecture.getLectureDateTime(),
                lectureRegistration.getRegisteredAt());
    }
}
