package com.example.licensemanagementbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "license_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "license_type_id", updatable = false, nullable = false)
    private UUID licenseTypeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_id", nullable = false)
    private Application application;

    @Column(name = "pricing_plan", nullable = false, length = 100)
    private String pricingPlan;

    @Column(name = "pricing_limit", nullable = false)
    private Integer pricingLimit;

    @Column(name = "limit_uom", nullable = false, length = 100)
    private String limitUom; // Unit of Measure

    @Column(name = "limit_frequency", nullable = false)
    private Integer limitFrequency;

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