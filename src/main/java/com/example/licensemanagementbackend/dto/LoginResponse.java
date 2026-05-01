package com.example.licensemanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserDto user;

    @Data
    @AllArgsConstructor
    public static class UserDto {
        private UUID userId;        // ← must be first
        private String fullName;
        private String roleName;
        private List<String> permissions;
        private List<UUID> assignedAppIds;
    }
}