package com.example.registerlecture.lecture.infrastructure.persistence.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepo extends JpaRepository<Lecture, Long> {
}
