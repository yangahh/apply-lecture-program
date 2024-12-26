package com.example.registerlecture.lecture.domain.service;

import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.lecture.domain.exception.AlreadyRegisteredException;
import com.example.registerlecture.lecture.domain.exception.CapacityExceededException;
import com.example.registerlecture.lecture.domain.repository.LectureRegistrationRepo;
import com.example.registerlecture.lecture.domain.repository.LectureRepo;
import com.example.registerlecture.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureRegistrationService {
    private final LectureRepo lectureRepo;
    private final LectureRegistrationRepo lectureRegistrationRepo;

    @Transactional
    public LectureRegistrationResult registerLecture(User user, long lectureId) {
        Lecture lecture = lectureRepo.getById(lectureId);
        validateLectureRegistration(user, lecture);

        LectureRegistration registration = lectureRegistrationRepo.save(user, lecture);
        return LectureRegistrationResult.from(lecture, registration);
    }

    private void validateLectureRegistration(User user, Lecture lecture) {
        if (lecture.isFull()) {
            throw new CapacityExceededException();
        }

        if (lectureRegistrationRepo.existsByUserIdAndLectureId(user.getId(), lecture.getId())) {
            throw new AlreadyRegisteredException();
        }
    }

}
