package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, UUID> {
    List<LicenseType> findByApplication_AppId(UUID appId);
}