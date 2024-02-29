package com.test.service;

import com.test.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse getUserById(Long id);
}
