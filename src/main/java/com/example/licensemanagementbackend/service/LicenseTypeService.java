package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.LicenseType;
import java.util.List;
import java.util.UUID;

public interface LicenseTypeService {
    LicenseType saveLicenseType(LicenseType licenseType);
    List<LicenseType> getAllLicenseTypes();
    LicenseType getLicenseTypeById(UUID id);
    List<LicenseType> getLicenseTypesByAppId(UUID appId);
    LicenseType updateLicenseType(UUID id, LicenseType details);
    void deleteLicenseType(UUID id);
    List<LicenseType> getLicenseTypesByOwnerId(UUID ownerId);
}