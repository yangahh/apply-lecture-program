package com.example.registerlecture.user.infrastructure.persistence.repository;

import com.example.registerlecture.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepo extends JpaRepository<User, Long> {
}
