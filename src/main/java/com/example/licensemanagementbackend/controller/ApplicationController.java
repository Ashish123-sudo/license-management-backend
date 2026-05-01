package com.example.licensemanagementbackend.controller;

import com.example.licensemanagementbackend.model.Application;
import com.example.licensemanagementbackend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import java.util.Map;
import com.example.licensemanagementbackend.model.User;
import com.example.licensemanagementbackend.repository.UserRepository;
import com.example.licensemanagementbackend.util.JwtUtil;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*") // Adjust this for your Angular frontend port
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.createApplication(application));
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable UUID id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/my-apps")
    public ResponseEntity<List<Application>> getMyApps(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(applicationService.getApplicationsByOwnerId(user.getUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable UUID id, @RequestBody Application details) {
        return ResponseEntity.ok(applicationService.updateApplication(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable UUID id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409
                    .body(Map.of("message", "Cannot delete this application — it is linked to existing " +
                            "License Types or Customer Licenses. Please remove those first."));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) // 400
                    .body(Map.of("message", e.getMessage()));
        }
    }
}