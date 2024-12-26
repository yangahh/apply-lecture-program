package com.example.registerlecture.lecture.integration;

import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.lecture.domain.repository.LectureRegistrationRepo;
import com.example.registerlecture.lecture.infrastructure.persistence.repository.JpaLectureRegistrationRepo;
import com.example.registerlecture.lecture.infrastructure.persistence.repository.JpaLectureRepo;
import com.example.registerlecture.user.domain.entity.User;
import com.example.registerlecture.user.infrastructure.persistence.repository.JpaUserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class LectureRegistrationRepoTest {
    @Autowired
    private LectureRegistrationRepo sut;
    @Autowired
    private JpaLectureRegistrationRepo jpaLectureRegistrationRepo;
    @Autowired
    private JpaLectureRepo jpaLectureRepo;
    @Autowired
    private JpaUserRepo jpaUserRepo;

    private User existingUser;
    private Lecture existingLecture;

    @BeforeEach
    void setUp() {
        existingUser = User.builder().username("user1").build();
        existingLecture = Lecture.builder()
                .title("lecture1")
                .speakerName("speaker1")
                .maxCapacity(30)
                .currentCapacity(0)
                .lectureDateTime(LocalDateTime.now())
                .registrationStartDateTime(LocalDateTime.now().minusDays(1))
                .registrationEndDateTime(LocalDateTime.now().plusDays(1))
                .build();
        jpaUserRepo.save(existingUser);
        jpaLectureRepo.save(existingLecture);
    }

    @AfterEach
    void tearDown() {
        jpaLectureRegistrationRepo.deleteAll();
        jpaLectureRepo.deleteAll();
        jpaUserRepo.deleteAll();
    }

    @DisplayName("특정 userId와 lectureId가 이미 저장되어있는 상태에서 existsByUserIdAndLectureId 메소드를 호출하면 true를 반환한다.")
    @Test
    void shouldReturnTrueWhenExistsByUserIdAndLectureId() {
        // given
        LectureRegistration registration = LectureRegistration.builder()
                .user(existingUser)
                .lecture(existingLecture)
                .build();
        jpaLectureRegistrationRepo.save(registration);

        // when
        boolean result = sut.existsByUserIdAndLectureId(existingUser.getId(), existingLecture.getId());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("특정 userId 저장되어있지 않은 상태에서 existsByUserIdAndLectureId 메소드를 호출하면 false를 반환한다.")
    @Test
    void shouldReturnFalseWhenNotExistsByUserIdAndLectureId() {
        // when
        boolean result = sut.existsByUserIdAndLectureId(existingUser.getId(), existingLecture.getId());

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이미 저장된 userId와 lectureId로 save 메소드를 호출하면 예외가 발생한다.")
    @Test
    void shouldThrowExceptionWhenSaveWithExistingUserIdAndLectureId() {
        // given
        LectureRegistration registration = LectureRegistration.builder()
                .user(existingUser)
                .lecture(existingLecture)
                .build();
        jpaLectureRegistrationRepo.save(registration);

        // when  // then
        assertThatThrownBy(() -> sut.save(existingUser, existingLecture))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Duplicate entry");
    }

    @DisplayName("정상적으로 save 메소드를 호출하면 ID와 신청시점과 함께 DB에 저장된다.")
    @Test
    void shouldSaveLectureRegistration() {
        // when
        LectureRegistration saved = sut.save(existingUser, existingLecture);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRegisteredAt()).isNotNull();
    }
}
