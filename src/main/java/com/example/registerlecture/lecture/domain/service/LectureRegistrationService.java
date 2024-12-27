package com.example.registerlecture.lecture.domain.service;

import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.domain.dto.LectureResult;
import com.example.registerlecture.lecture.domain.entity.Lecture;
import com.example.registerlecture.lecture.domain.entity.LectureRegistration;
import com.example.registerlecture.lecture.domain.exception.AlreadyRegisteredException;
import com.example.registerlecture.lecture.domain.exception.CapacityExceededException;
import com.example.registerlecture.lecture.domain.repository.LectureRegistrationRepo;
import com.example.registerlecture.lecture.domain.repository.LectureRepo;
import com.example.registerlecture.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureRegistrationService {
    private final LectureRepo lectureRepo;
    private final LectureRegistrationRepo lectureRegistrationRepo;

    @Transactional
    public LectureRegistrationResult registerLecture(User user, long lectureId) {
        Lecture lecture = lectureRepo.getById(lectureId);
        validateLectureRegistration(user, lecture);

        LectureRegistration registration = lectureRegistrationRepo.save(user, lecture);
        return LectureRegistrationResult.from(registration);
    }

    private void validateLectureRegistration(User user, Lecture lecture) {
        if (lecture.isFull()) {
            throw new CapacityExceededException();
        }

        if (lectureRegistrationRepo.existsByUserIdAndLectureId(user.getId(), lecture.getId())) {
            throw new AlreadyRegisteredException();
        }
    }

    @Transactional
    public List<LectureRegistrationResult> getRegisteredLecturesByUser(long userId) {
        List<LectureRegistration> registrations = sortByRegisteredAt(lectureRegistrationRepo.findAllByUserId(userId));
        return registrations.stream().map(LectureRegistrationResult::from).toList();
    }

    private List<LectureRegistration> sortByRegisteredAt(List<LectureRegistration> registrations) {
        return registrations.stream()
                .sorted(Comparator.comparing(LectureRegistration::getRegisteredAt).reversed())
                .toList();
    }

    @Transactional
    public List<LectureResult> getLecturesAvailableForRegistration(Optional<LocalDate> date) {
        date.ifPresent(this::validateDate);

        return lectureRepo.findAvailableRegistration(date).stream()
                .map(LectureResult::from)
                .toList();
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("이미 지난 날짜로는 조회할 수 없습니다.");
        }
    }

}
