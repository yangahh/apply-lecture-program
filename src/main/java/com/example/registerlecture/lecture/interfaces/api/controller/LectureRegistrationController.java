package com.example.registerlecture.lecture.interfaces.api.controller;

import com.example.registerlecture.global.api.response.ApiResponse;
import com.example.registerlecture.lecture.application.facade.LectureRegistrationFacade;
import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.interfaces.api.dto.LectureRegistrationRequest;
import com.example.registerlecture.lecture.interfaces.api.dto.LectureRegistrationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureRegistrationFacade lectureRegistrationFacade;

    @PostMapping("/{id}/registrations")
    public ApiResponse<LectureRegistrationResponse> registerLecture(
            @PathVariable("id") @NotNull @Positive(message = "잘못된 형식의 ID 입니다.") long id,
            @RequestBody @Valid LectureRegistrationRequest request) {
        LectureRegistrationResult result = lectureRegistrationFacade.registerLecture(request.getUserId(), id);
        return ApiResponse.ok(LectureRegistrationResponse.from(result));
    }
}
