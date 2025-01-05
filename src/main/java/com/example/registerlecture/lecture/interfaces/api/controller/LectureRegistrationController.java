package com.example.registerlecture.lecture.interfaces.api.controller;

import com.example.registerlecture.global.api.response.ApiResponse;
import com.example.registerlecture.lecture.application.facade.LectureRegistrationFacade;
import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.domain.dto.LectureResult;
import com.example.registerlecture.lecture.interfaces.api.dto.LectureRegistrationRequest;
import com.example.registerlecture.lecture.interfaces.api.dto.LectureRegistrationResponse;
import com.example.registerlecture.lecture.interfaces.api.dto.LectureResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
@Validated
public class LectureRegistrationController {
    private final LectureRegistrationFacade lectureRegistrationFacade;

    @PostMapping("/{id}/registrations")
    public ResponseEntity<ApiResponse<LectureRegistrationResponse>> registerLecture(
            @PathVariable("id") @NotNull @Positive(message = "잘못된 형식의 ID 입니다.") long id,
            @RequestBody @Valid LectureRegistrationRequest request) {
        LectureRegistrationResult result = lectureRegistrationFacade.registerLecture(request.getUserId(), id);
        return ResponseEntity.ok(ApiResponse.ok(LectureRegistrationResponse.from(result)));
    }

    @GetMapping("/registrations")
    public ResponseEntity<ApiResponse<List<LectureRegistrationResponse>>> getUserRegisteredLectures(
            @RequestParam(value = "userId", required = true) @Positive(message = "잘못된 형식의 ID 입니다.") long userId) {
        List<LectureRegistrationResponse> result = lectureRegistrationFacade.getRegisteredLecturesByUser(userId).stream()
                .map(LectureRegistrationResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LectureResponse>>> getLecturesAvailableForRegistration(
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date) {
        List<LectureResponse> result = lectureRegistrationFacade.getLecturesAvailableForRegistration(Optional.ofNullable(date)).stream()
                .map(LectureResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
