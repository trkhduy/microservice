package com.test.service.impl;

import com.test.dto.response.UserResponse;
import com.test.entity.User;
import com.test.repository.UserRepository;
import com.test.service.UserService;
import com.test.utils.MapperUtils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserResponse> getAll() {
        return MapperUtils.toDTOs(repository.findAll(), UserResponse.class);
    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("user " + id + " does not exist");
        }
        return MapperUtils.toDTO(user.get(), UserResponse.class);
    }
}
