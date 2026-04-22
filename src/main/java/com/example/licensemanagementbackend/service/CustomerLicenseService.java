package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.CustomerLicense;
import java.util.List;
import java.util.UUID;

public interface CustomerLicenseService {
    CustomerLicense assignLicense(CustomerLicense customerLicense);
    List<CustomerLicense> getAllCustomerLicenses();
    CustomerLicense getCustomerLicenseById(UUID id);
    List<CustomerLicense> getLicensesByOrg(UUID orgId);
    List<CustomerLicense> getLicensesByApp(UUID appId);
    CustomerLicense updateCustomerLicense(UUID id, CustomerLicense details);
    void deleteCustomerLicense(UUID id);
}