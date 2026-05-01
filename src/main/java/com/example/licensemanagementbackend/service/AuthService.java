package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.dto.LoginRequest;
import com.example.licensemanagementbackend.dto.LoginResponse;
import com.example.licensemanagementbackend.model.Application;
import com.example.licensemanagementbackend.model.User;
import com.example.licensemanagementbackend.repository.ApplicationRepository;
import com.example.licensemanagementbackend.repository.UserRepository;
import com.example.licensemanagementbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String roleName = user.getRole().getName();
        String token = jwtUtil.generateToken(user.getUserName());

        // Only fetch assigned apps for PRODUCT_ADMIN
        List<UUID> assignedAppIds = null;
        if ("PRODUCT_ADMIN".equals(roleName)) {
            assignedAppIds = applicationRepository
                    .findByProductOwnerId(user.getUserId())
                    .stream()
                    .map(Application::getAppId)
                    .toList();
        }

        LoginResponse.UserDto userDto = new LoginResponse.UserDto(
                user.getUserId(),       // ← add this as first argument
                user.getUserName(),
                roleName,
                List.of(),
                assignedAppIds
        );

        return new LoginResponse(token, userDto);
    }
}