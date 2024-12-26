package com.example.registerlecture.lecture.infrastructure.persistence.repository;

import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLectureRegistrationRepo extends JpaRepository<LectureRegistration, Long> {
    @Query("SELECT CASE WHEN COUNT(lr) > 0 THEN true ELSE false END " +
            "FROM LectureRegistration lr " +
            "WHERE lr.user.id = :userId AND lr.lecture.id = :lectureId")
    boolean existsByUserIdAndLectureId(@Param("userId") Long userId, @Param("lectureId") Long lectureId);
    List<LectureRegistration> findAllByUserId(long userId);

}
