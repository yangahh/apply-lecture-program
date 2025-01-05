package com.example.registerlecture.lecture.domain.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LectureRepo {
    Lecture getById(long lectureId);
    List<Lecture> findAvailableRegistration(Optional<LocalDate> date);
}
