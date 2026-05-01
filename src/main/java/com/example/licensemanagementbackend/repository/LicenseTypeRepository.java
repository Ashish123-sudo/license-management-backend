package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import java.util.List;
import java.util.UUID;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, UUID> {
    List<LicenseType> findByApplication_AppId(UUID appId);
    @Query("SELECT lt FROM LicenseType lt WHERE lt.application.productOwner.userId = :ownerId")
    List<LicenseType> findByProductOwnerId(@Param("ownerId") UUID ownerId);
    List<LicenseType> findByApplication_AppNameContainingIgnoreCase(String appName);
}

