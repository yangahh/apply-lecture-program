package com.example.registerlecture.lecture.application.facade;

import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.domain.service.LectureRegistrationService;
import com.example.registerlecture.user.domain.entity.User;
import com.example.registerlecture.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureRegistrationFacade {
    private final LectureRegistrationService lectureRegistrationService;
    private final UserService userService;

    public LectureRegistrationResult registerLecture(long userId, long lectureId) {
        User user = userService.getUser(userId);
        return lectureRegistrationService.registerLecture(user, lectureId);
    }

    public List<LectureRegistrationResult> getRegisteredLecturesByUser(long userId) {
        userService.getUser(userId);  // user 검증
        return lectureRegistrationService.getRegisteredLecturesByUser(userId);
    }
}
