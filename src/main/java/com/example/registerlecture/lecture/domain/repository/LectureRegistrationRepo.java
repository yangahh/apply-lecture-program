package com.example.registerlecture.lecture.domain.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface LectureRegistrationRepo {
    boolean existsByUserIdAndLectureId(long userId, long lectureId);
    LectureRegistration save(User user, Lecture lecture);
    List<LectureRegistration> findAllByUserId(long userId);

}
