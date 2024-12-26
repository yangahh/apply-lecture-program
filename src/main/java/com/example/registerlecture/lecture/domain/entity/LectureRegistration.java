package com.example.registerlecture.lecture.domain.entity;

import com.example.registerlecture.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_lecture_uk",
                        columnNames = {"user_id", "lecture_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class LectureRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", referencedColumnName = "id")
    private Lecture lecture;

    @CreatedDate
    @Column(name = "registered_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime registeredAt;

    @Builder
    private LectureRegistration(User user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
    }

    public static LectureRegistration createRegistration(User user, Lecture lecture) {
        return LectureRegistration.builder()
                .user(user)
                .lecture(lecture)
                .build();
    }

}
