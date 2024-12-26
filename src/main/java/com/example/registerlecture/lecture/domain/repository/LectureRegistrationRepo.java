package com.example.registerlecture.lecture.domain.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.user.domain.entity.User;

public interface LectureRegistrationRepo {
    boolean existsByUserIdAndLectureId(long userId, long lectureId);
    LectureRegistration save(User user, Lecture lecture);
}
