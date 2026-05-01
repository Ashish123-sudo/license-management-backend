package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.dto.SearchResult;
import com.example.licensemanagementbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ApplicationRepository applicationRepo;
    private final LicenseTypeRepository licenseTypeRepo;
    private final OrganizationRepository organizationRepo;
    private final CustomerLicenseRepository customerLicenseRepo;
    private final OrganizationContactRepository contactRepo;

    public List<SearchResult> search(String query, String filter) {
        List<SearchResult> results = new ArrayList<>();

        if (filter.equals("ALL") || filter.equals("CONTACT")) {
            contactRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
                    .forEach(c -> results.add(new SearchResult(
                            "CONTACT",
                            c.getContactId().toString(),
                            c.getFirstName() + " " + c.getLastName(),
                            c.getOrganization().getOrgName() + (c.getDesignation() != null ? " — " + c.getDesignation() : "")
                    )));
        }

        if (filter.equals("ALL") || filter.equals("LICENSE_TYPE")) {
            licenseTypeRepo.findByApplication_AppNameContainingIgnoreCase(query)
                    .forEach(l -> results.add(new SearchResult(
                            "LICENSE_TYPE",
                            l.getLicenseTypeId().toString(),
                            l.getApplication().getAppName(),
                            l.getPricingPlan() + " — " + l.getPricingLimit() + " " + l.getLimitUom()
                    )));
        }

        if (filter.equals("ALL") || filter.equals("ORGANIZATION")) {
            organizationRepo.findByOrgNameContainingIgnoreCase(query)
                    .forEach(o -> results.add(new SearchResult(
                            "ORGANIZATION",
                            o.getOrgId().toString(),
                            o.getOrgName(),
                            o.getIndustry() != null ? o.getIndustry() : o.getOrgCode()
                    )));
        }

        if (filter.equals("ALL") || filter.equals("CUSTOMER_LICENSE")) {
            customerLicenseRepo.findByOrganization_OrgNameContainingIgnoreCase(query)
                    .forEach(cl -> results.add(new SearchResult(
                            "CUSTOMER_LICENSE",
                            cl.getCustomerLicenseId().toString(),
                            cl.getOrganization().getOrgName(),
                            cl.getApplication().getAppName() + " — " + cl.getLicenseType().getPricingPlan()
                    )));
        }

        return results;
    }
}