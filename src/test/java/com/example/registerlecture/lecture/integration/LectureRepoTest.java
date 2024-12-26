package com.example.registerlecture.lecture.integration;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.repository.LectureRepo;
import com.example.registerlecture.lecture.infrastructure.persistence.repository.JpaLectureRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class LectureRepoTest {
    @Autowired
    private LectureRepo lectureRepo;

    @Autowired
    private JpaLectureRepo jpaLectureRepo;

    @AfterEach
    void tearDown() {
        jpaLectureRepo.deleteAll();
    }

    @DisplayName("존재하지 않는 lectureId로 getById 메소드를 호출하면 예외가 발생한다.")
    @Test
    void shouldThrowExceptionWhenGetByIdWithNonExistingId() {
        // when // then
        assertThatThrownBy(() -> lectureRepo.getById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("[id=999] 특강을 찾을 수 없습니다.");

    }

    @DisplayName("존재하는 lectureId로 getById 메소드를 호출하면 Lecture를 반환한다.")
    @Test
    void shouldReturnLectureWhenGetByIdWithExistingId() {
        // given
        Lecture lecture = Lecture.builder()
                .title("title1")
                .speakerName("speaker1")
                .maxCapacity(10)
                .currentCapacity(0)
                .lectureDateTime(LocalDateTime.now())
                .registrationStartDateTime(LocalDateTime.now().minusDays(1))
                .registrationEndDateTime(LocalDateTime.now().plusDays(1))
                .build();

        Lecture saved = jpaLectureRepo.save(lecture);
        long expectedId = saved.getId();

        // when
        Lecture result = lectureRepo.getById(expectedId);

        // then
        assertThat(result.getId()).isEqualTo(expectedId);
        assertThat(result.getTitle()).isEqualTo("title1");
    }
}
