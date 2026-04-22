package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.CustomerLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerLicenseRepository extends JpaRepository<CustomerLicense, UUID> {
    List<CustomerLicense> findByApplication_AppId(UUID appId);
    List<CustomerLicense> findByOrganization_OrgId(UUID orgId);
    List<CustomerLicense> findByIsActiveTrue();
}