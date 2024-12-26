package com.example.registerlecture.user.infrastructure.persistence.repository;

import com.example.registerlecture.user.domain.entity.User;
import com.example.registerlecture.user.domain.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {
    private final JpaUserRepo jpaUserRepo;

    @Override
    public User getById(long id) {
        return jpaUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("[id="+ id +"] 사용자를 찾을 수 없습니다."));
    }
}
