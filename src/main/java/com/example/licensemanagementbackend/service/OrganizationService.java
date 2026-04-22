package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.Organization;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {

    Organization createOrganization(Organization organization);

    Organization getOrganizationById(UUID orgId);

    Organization getOrganizationByCode(String orgCode);

    List<Organization> getAllOrganizations();

    List<Organization> getActiveOrganizations();

    Organization updateOrganization(UUID orgId, Organization organization);

    Organization toggleActiveStatus(UUID orgId);

    void deleteOrganization(UUID orgId);

    boolean existsByOrgCode(String orgCode);

    boolean existsByEmail(String email);
}