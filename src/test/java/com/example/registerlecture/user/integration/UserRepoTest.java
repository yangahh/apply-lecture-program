package com.example.registerlecture.user.integration;

import com.example.registerlecture.user.domain.entity.User;
import com.example.registerlecture.user.domain.repository.UserRepo;
import com.example.registerlecture.user.infrastructure.persistence.repository.JpaUserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JpaUserRepo jpaUserRepo;

    @AfterEach
    void tearDown() {
        jpaUserRepo.deleteAll();
    }

    @DisplayName("존재하지 않는 userId로 getById 메소드를 호출하면 예외가 발생한다.")
    @Test
    void shouldThrowExceptionWhenGetByIdWithNonExistingId() {
        // when  // then
        assertThatThrownBy(() -> userRepo.getById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("[id=999] 사용자를 찾을 수 없습니다.");
    }

    @DisplayName("존재하는 userId로 getById 메소드를 호출하면 User를 반환한다.")
    @Test
    void shouldReturnUserWhenGetByIdWithExistingId() {
        // given
        User user = User.builder()
                .username("test")
                .build();
        User saved = jpaUserRepo.save(user);
        long expectedId = saved.getId();

        // when
        User result = userRepo.getById(expectedId);

        // then
        assertThat(result.getId()).isEqualTo(expectedId);
    }
}
