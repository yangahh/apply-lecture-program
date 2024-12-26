package com.example.registerlecture.lecture.infrastructure.persistence.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.repository.LectureRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureRepoImpl implements LectureRepo {
    private final JpaLectureRepo jpaLectureRepo;

    @Override
    public Lecture getById(long lectureId) {
        return jpaLectureRepo.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("[id=" + lectureId + "] 특강을 찾을 수 없습니다."));
    }
}
