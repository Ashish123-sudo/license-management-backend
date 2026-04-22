package com.example.licensemanagementbackend.service.impl;

import com.example.licensemanagementbackend.model.Role;
import com.example.licensemanagementbackend.model.User;
import com.example.licensemanagementbackend.repository.RoleRepository;
import com.example.licensemanagementbackend.repository.UserRepository;
import com.example.licensemanagementbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("Username already exists");
        }

        Role existingRole = roleRepository.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(existingRole);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(UUID id, User user) {
        User existingUser = getUserById(id);

        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());

        Role existingRole = roleRepository.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        existingUser.setRole(existingRole);

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}