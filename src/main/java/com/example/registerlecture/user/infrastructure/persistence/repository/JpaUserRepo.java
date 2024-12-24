package com.example.registerlecture.user.infrastructure.persistence.repository;

import com.example.registerlecture.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepo extends JpaRepository<Users, Long> {
}
