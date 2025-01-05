package com.example.registerlecture.lecture.infrastructure.persistence.repository;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaLectureRepo extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l " +
            "WHERE l.registrationStartDateTime <= :now " +
            "  AND l.registrationEndDateTime >= :now " +
            "  AND (:date IS NULL " +
            "       OR FUNCTION('DATE', l.lectureDateTime) = :date)")
    List<Lecture> findLecturesByDateAndNow(@Param("date") LocalDate date, @Param("now") LocalDateTime now);
}
