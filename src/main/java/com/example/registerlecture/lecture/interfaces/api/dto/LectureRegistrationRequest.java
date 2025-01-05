package com.example.registerlecture.lecture.interfaces.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureRegistrationRequest {
    @NotNull
    @Positive
    private long userId;
}
