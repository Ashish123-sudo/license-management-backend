package com.example.licensemanagementbackend.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.licensemanagementbackend.dto.LoginRequest;
import com.example.licensemanagementbackend.dto.LoginResponse;
import com.example.licensemanagementbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder; // ← add this

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage() + " | " + e.getClass().getName());
        }
    }

    @GetMapping("/test-password")
    public ResponseEntity<String> testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "password123";
        String freshHash = encoder.encode(raw);
        boolean matchesFresh = encoder.matches(raw, freshHash);

        String storedHash = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        boolean matchesStored = encoder.matches(raw, storedHash);

        return ResponseEntity.ok(
                "matchesFresh=" + matchesFresh +
                        " | matchesStored=" + matchesStored +
                        " | freshHash=" + freshHash
        );
    }


}