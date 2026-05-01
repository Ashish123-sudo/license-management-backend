package com.example.licensemanagementbackend.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.licensemanagementbackend.dto.LoginRequest;
import com.example.licensemanagementbackend.dto.LoginResponse;
import com.example.licensemanagementbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder; // ← add this

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}