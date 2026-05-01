package com.example.licensemanagementbackend.controller;

import com.example.licensemanagementbackend.model.LicenseType;
import com.example.licensemanagementbackend.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.licensemanagementbackend.model.User;
import com.example.licensemanagementbackend.repository.UserRepository;
import com.example.licensemanagementbackend.util.JwtUtil;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/license-types")
@CrossOrigin(origins = "*")
public class LicenseTypeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/my-types")
    public ResponseEntity<List<LicenseType>> getMyLicenseTypes(
            @RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.extractUsername(authHeader.substring(7));
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(licenseTypeService.getLicenseTypesByOwnerId(user.getUserId()));
    }

    @PostMapping
    public ResponseEntity<LicenseType> createLicenseType(@RequestBody LicenseType licenseType) {
        return ResponseEntity.ok(licenseTypeService.saveLicenseType(licenseType));
    }

    @GetMapping
    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeService.getAllLicenseTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseType> getLicenseTypeById(@PathVariable UUID id) {
        return ResponseEntity.ok(licenseTypeService.getLicenseTypeById(id));
    }

    @GetMapping("/application/{appId}")
    public List<LicenseType> getLicenseTypesByAppId(@PathVariable UUID appId) {
        return licenseTypeService.getLicenseTypesByAppId(appId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseType> updateLicenseType(@PathVariable UUID id, @RequestBody LicenseType details) {
        return ResponseEntity.ok(licenseTypeService.updateLicenseType(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLicenseType(@PathVariable UUID id) {
        licenseTypeService.deleteLicenseType(id);
        return ResponseEntity.ok("License Type deleted successfully.");
    }
}