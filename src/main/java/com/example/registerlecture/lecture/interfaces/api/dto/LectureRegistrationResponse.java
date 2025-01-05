package com.example.registerlecture.lecture.interfaces.api.dto;

import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LectureRegistrationResponse {
    private long lectureId;
    private long userId;
    private long registrationId;
    private String lectureTitle;
    private LocalDateTime lectureDateTime;
    private String speakerName;
    private LocalDateTime registeredAt;

    public static LectureRegistrationResponse from(LectureRegistrationResult result) {
        return LectureRegistrationResponse.builder()
                .lectureId(result.lectureId())
                .userId(result.userId())
                .registrationId(result.registrationId())
                .lectureTitle(result.title())
                .lectureDateTime(result.lectureDateTime())
                .speakerName(result.speakerName())
                .registeredAt(result.registeredAt())
                .build();
    }
}
