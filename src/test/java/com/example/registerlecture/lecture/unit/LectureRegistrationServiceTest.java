package com.example.registerlecture.lecture.unit;

import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.lecture.domain.exception.AlreadyRegisteredException;
import com.example.registerlecture.lecture.domain.exception.CapacityExceededException;
import com.example.registerlecture.lecture.domain.repository.LectureRegistrationRepo;
import com.example.registerlecture.lecture.domain.repository.LectureRepo;
import com.example.registerlecture.lecture.domain.service.LectureRegistrationService;
import com.example.registerlecture.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class LectureRegistrationServiceTest {
    @InjectMocks
    private LectureRegistrationService sut;

    @Mock
    private LectureRepo lectureRepo;

    @Mock
    private LectureRegistrationRepo lectureRegistrationRepo;

    @Mock
    private User mockUser;

    @Mock
    private Lecture mockLecture;

    @DisplayName("정원이 초과되면 특강 신청에 실패한다.")
    @Test
    void shouldFailWhenExceedCapacity() {
        // given
        long lectureId = 1L;
        Lecture lecture = Lecture.builder()
                .currentCapacity(10)
                .maxCapacity(10)
                .build();

        given(lectureRepo.getById(lectureId)).willReturn(lecture);

        // when  // then
        assertThatThrownBy(() -> sut.registerLecture(mockUser, lectureId))
                .isInstanceOf(CapacityExceededException.class)
                .hasMessage("정원이 초과되었습니다.");
        then(lectureRegistrationRepo).should(never()).save(mockUser, lecture);
    }

    @DisplayName("사용자는 동일한 강의를 중복 신청할 수 없다.")
    @Test
    void shouldFailWhenDuplicateRegistration() {
        // given
        long userId = 1L;
        long lectureId = 1L;
        given(mockUser.getId()).willReturn(userId);
        given(mockLecture.getId()).willReturn(lectureId);
        given(lectureRepo.getById(lectureId)).willReturn(mockLecture);
        given(lectureRegistrationRepo.existsByUserIdAndLectureId(userId, lectureId)).willReturn(true);

        // when  // then
        assertThatThrownBy(() -> sut.registerLecture(mockUser, lectureId))
                .isInstanceOf(AlreadyRegisteredException.class)
                .hasMessage("이미 신청한 특강입니다.");
        then(lectureRegistrationRepo).should(never()).save(mockUser, mockLecture);
    }

    @DisplayName("정원이 초과되지 않았고, 사용자가 아직 해당 강의를 신청하기 않았으며 현재 시점이 특강 신청 가능한 시간일 경우 특강은 특강 신청에 성공한다.")
    @Test
    void shouldRegisterLectureSuccessfully() {
        // given
        long userId = 1L;
        long lectureId = 1L;

        given(mockUser.getId()).willReturn(userId);
        given(mockLecture.getId()).willReturn(lectureId);
        given(mockLecture.isFull()).willReturn(false);

        LectureRegistration expected = LectureRegistration.builder()
                .user(mockUser)
                .lecture(mockLecture)
                .build();

        // 리플렉션을 사용해 ID 설정
        ReflectionTestUtils.setField(expected, "id", 1L);

        given(lectureRepo.getById(lectureId)).willReturn(mockLecture);
        given(lectureRegistrationRepo.existsByUserIdAndLectureId(userId, lectureId)).willReturn(false);
        given(lectureRegistrationRepo.save(mockUser, mockLecture)).willReturn(expected);

        // when
        LectureRegistrationResult result = sut.registerLecture(mockUser, lectureId);

        // then
        then(lectureRegistrationRepo).should().save(mockUser, mockLecture);
        assertThat(result.lectureId()).isEqualTo(lectureId);
    }

}
