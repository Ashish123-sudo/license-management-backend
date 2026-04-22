package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(UUID id);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);
}