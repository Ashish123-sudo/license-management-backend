package com.example.licensemanagementbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "organization_contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationContact {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "contact_id", updatable = false, nullable = false)
    private UUID contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "designation", length = 100)
    private String designation;

    @Column(name = "role", length = 100)
    private String role;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "mobile", length = 50)
    private String mobile;

    @Column(name = "office_phone", length = 50)
    private String officePhone;

    @Column(name = "ext", length = 10)
    private String ext;

    @Column(name = "fax", length = 50)
    private String fax;

    @Column(name = "linked_in", length = 500)
    private String linkedIn;

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