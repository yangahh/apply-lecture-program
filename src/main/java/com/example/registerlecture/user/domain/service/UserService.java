package com.example.registerlecture.user.domain.service;

import com.example.registerlecture.user.domain.entity.User;
import com.example.registerlecture.user.domain.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User getUser(long userId) {
        return userRepo.getById(userId);
    }
}
