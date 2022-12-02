package com.example.productapi.service.user;

import com.example.productapi.entity.User;
import com.example.productapi.model.user.AuthRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
    User register(AuthRegisterRequest authRegisterRequest);
    List<User> getUsers();
}
