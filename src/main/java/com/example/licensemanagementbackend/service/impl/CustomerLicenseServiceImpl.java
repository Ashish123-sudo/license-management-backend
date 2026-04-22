package com.example.licensemanagementbackend.service.impl;

import com.example.licensemanagementbackend.model.Application;
import com.example.licensemanagementbackend.model.CustomerLicense;
import com.example.licensemanagementbackend.model.LicenseType;
import com.example.licensemanagementbackend.model.Organization;
import com.example.licensemanagementbackend.repository.ApplicationRepository;
import com.example.licensemanagementbackend.repository.CustomerLicenseRepository;
import com.example.licensemanagementbackend.repository.LicenseTypeRepository;
import com.example.licensemanagementbackend.repository.OrganizationRepository;
import com.example.licensemanagementbackend.service.CustomerLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerLicenseServiceImpl implements CustomerLicenseService {

    @Autowired private CustomerLicenseRepository repository;
    @Autowired private ApplicationRepository applicationRepository;
    @Autowired private OrganizationRepository organizationRepository;
    @Autowired private LicenseTypeRepository licenseTypeRepository;

    @Override
    public CustomerLicense assignLicense(CustomerLicense customerLicense) {
        Application app = applicationRepository.findById(customerLicense.getApplication().getAppId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        Organization org = organizationRepository.findById(customerLicense.getOrganization().getOrgId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        LicenseType licenseType = licenseTypeRepository.findById(customerLicense.getLicenseType().getLicenseTypeId())
                .orElseThrow(() -> new RuntimeException("License Type not found"));

        customerLicense.setApplication(app);
        customerLicense.setOrganization(org);
        customerLicense.setLicenseType(licenseType);
        return repository.save(customerLicense);
    }

    @Override
    public List<CustomerLicense> getAllCustomerLicenses() {
        return repository.findAll();
    }

    @Override
    public CustomerLicense getCustomerLicenseById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer License not found: " + id));
    }

    @Override
    public List<CustomerLicense> getLicensesByOrg(UUID orgId) {
        return repository.findByOrganization_OrgId(orgId);
    }

    @Override
    public List<CustomerLicense> getLicensesByApp(UUID appId) {
        return repository.findByApplication_AppId(appId);
    }

    @Override
    public CustomerLicense updateCustomerLicense(UUID id, CustomerLicense details) {
        CustomerLicense existing = getCustomerLicenseById(id);

        Application app = applicationRepository.findById(details.getApplication().getAppId())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        Organization org = organizationRepository.findById(details.getOrganization().getOrgId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        LicenseType licenseType = licenseTypeRepository.findById(details.getLicenseType().getLicenseTypeId())
                .orElseThrow(() -> new RuntimeException("License Type not found"));

        existing.setApplication(app);
        existing.setOrganization(org);
        existing.setLicenseType(licenseType);
        existing.setLicenseCount(details.getLicenseCount());
        existing.setIsActive(details.getIsActive());

        return repository.save(existing);
    }

    @Override
    public void deleteCustomerLicense(UUID id) {
        repository.deleteById(id);
    }
}