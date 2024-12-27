package com.example.registerlecture.lecture.interfaces.api.dto;

import com.example.registerlecture.lecture.domain.dto.LectureResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LectureResponse {
    private long lectureId;
    private String lectureTitle;
    private LocalDateTime lectureDateTime;
    private LocalDateTime registrationStartDatetime;
    private LocalDateTime registrationEndDatetime;
    private String speakerName;
    private boolean isFull;

    public static LectureResponse from(LectureResult result)
    {
        return LectureResponse.builder()
                .lectureId(result.id())
                .lectureTitle(result.title())
                .lectureDateTime(result.lectureDateTime())
                .registrationStartDatetime(result.registrationStartDateTime())
                .registrationEndDatetime(result.registrationEndDateTime())
                .speakerName(result.speakerName())
                .isFull(result.isFull())
                .build();

    }
}
