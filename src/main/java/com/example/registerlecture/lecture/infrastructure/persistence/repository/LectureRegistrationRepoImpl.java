package com.example.registerlecture.lecture.infrastructure.persistence.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.lecture.domain.repository.LectureRegistrationRepo;
import com.example.registerlecture.user.domain.entity.User;
import com.example.registerlecture.user.infrastructure.persistence.repository.JpaUserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureRegistrationRepoImpl implements LectureRegistrationRepo {
    private final JpaLectureRegistrationRepo jpaLectureRegistrationRepo;

    @Override
    public boolean existsByUserIdAndLectureId(long userId, long lectureId) {
        return jpaLectureRegistrationRepo.existsByUserIdAndLectureId(userId, lectureId);
    }

    @Override
    public LectureRegistration save(User user, Lecture lecture) {
        LectureRegistration registration = LectureRegistration.createRegistration(user, lecture);
        return jpaLectureRegistrationRepo.save(registration);
    }
}
