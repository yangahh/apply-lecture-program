package com.example.registerlecture.lecture.domain.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;

public interface LectureRepo {
    Lecture getById(long lectureId);
}
