package com.example.licensemanagementbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "org_id", updatable = false, nullable = false)
    private UUID orgId;

    @Column(name = "org_code", unique = true, nullable = false, length = 20)
    private String orgCode;

    @Column(name = "org_name", nullable = false, length = 255)
    private String orgName;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "website", length = 500)
    private String website;

    @Column(name = "address1", columnDefinition = "TEXT")
    private String address1;

    @Column(name = "address2", columnDefinition = "TEXT")
    private String address2;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}