package com.example.licensemanagementbackend.service.impl;

import com.example.licensemanagementbackend.model.Application;
import com.example.licensemanagementbackend.model.LicenseType;
import com.example.licensemanagementbackend.repository.ApplicationRepository;
import com.example.licensemanagementbackend.repository.LicenseTypeRepository;
import com.example.licensemanagementbackend.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseTypeServiceImpl implements LicenseTypeService {

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public LicenseType saveLicenseType(LicenseType licenseType) {
        Application existingApp = applicationRepository.findById(licenseType.getApplication().getAppId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        licenseType.setApplication(existingApp);
        return licenseTypeRepository.save(licenseType);
    }

    @Override
    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }

    @Override
    public LicenseType getLicenseTypeById(UUID id) {
        return licenseTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License Type not found with id: " + id));
    }

    @Override
    public List<LicenseType> getLicenseTypesByAppId(UUID appId) {
        return licenseTypeRepository.findByApplication_AppId(appId);
    }

    @Override
    public LicenseType updateLicenseType(UUID id, LicenseType details) {
        LicenseType licenseType = getLicenseTypeById(id);

        licenseType.setPricingPlan(details.getPricingPlan());
        licenseType.setPricingLimit(details.getPricingLimit());
        licenseType.setLimitUom(details.getLimitUom());
        licenseType.setLimitFrequency(details.getLimitFrequency());

        Application existingApp = applicationRepository.findById(details.getApplication().getAppId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        licenseType.setApplication(existingApp);

        return licenseTypeRepository.save(licenseType);
    }

    @Override
    public List<LicenseType> getLicenseTypesByOwnerId(UUID ownerId) {
        return licenseTypeRepository.findByProductOwnerId(ownerId);
    }

    @Override
    public void deleteLicenseType(UUID id) {
        LicenseType licenseType = getLicenseTypeById(id);
        licenseTypeRepository.delete(licenseType);
    }
}