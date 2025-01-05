package com.example.registerlecture.user.domain.repository;

import com.example.registerlecture.user.domain.entity.User;

public interface UserRepo {
    User getById(long id);
}
