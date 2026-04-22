package com.example.licensemanagementbackend.service.impl;

import com.example.licensemanagementbackend.model.Organization;
import com.example.licensemanagementbackend.repository.OrganizationRepository;
import com.example.licensemanagementbackend.service.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public Organization createOrganization(Organization organization) {
        // Auto-generate org code if not provided
        if (organization.getOrgCode() == null || organization.getOrgCode().isBlank()) {
            String generated = "ORG" + String.format("%03d", organizationRepository.count() + 1);
            organization.setOrgCode(generated);
        }

        if (organizationRepository.existsByOrgCode(organization.getOrgCode())) {
            throw new IllegalArgumentException(
                    "Organization with code '" + organization.getOrgCode() + "' already exists.");
        }
        if (organization.getEmail() != null &&
                organizationRepository.existsByEmail(organization.getEmail())) {
            throw new IllegalArgumentException(
                    "Organization with email '" + organization.getEmail() + "' already exists.");
        }
        return organizationRepository.save(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public Organization getOrganizationById(UUID orgId) {
        return organizationRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Organization not found with id: " + orgId));
    }

    @Override
    @Transactional(readOnly = true)
    public Organization getOrganizationByCode(String orgCode) {
        return organizationRepository.findByOrgCode(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Organization not found with code: " + orgCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Organization> getActiveOrganizations() {
        return organizationRepository.findByIsActive(true);
    }

    @Override
    public Organization updateOrganization(UUID orgId, Organization updated) {
        Organization existing = getOrganizationById(orgId);

        // If orgCode is being changed, check for uniqueness
        if (!existing.getOrgCode().equals(updated.getOrgCode()) &&
                organizationRepository.existsByOrgCode(updated.getOrgCode())) {
            throw new IllegalArgumentException(
                    "Organization with code '" + updated.getOrgCode() + "' already exists.");
        }

        // If email is being changed, check for uniqueness
        if (updated.getEmail() != null &&
                !updated.getEmail().equals(existing.getEmail()) &&
                organizationRepository.existsByEmail(updated.getEmail())) {
            throw new IllegalArgumentException(
                    "Organization with email '" + updated.getEmail() + "' already exists.");
        }

        existing.setOrgCode(updated.getOrgCode());
        existing.setOrgName(updated.getOrgName());
        existing.setIndustry(updated.getIndustry());
        existing.setWebsite(updated.getWebsite());
        existing.setAddress1(updated.getAddress1());
        existing.setAddress2(updated.getAddress2());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setPostalCode(updated.getPostalCode());
        existing.setCountry(updated.getCountry());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setNotes(updated.getNotes());
        existing.setIsActive(updated.getIsActive());

        return organizationRepository.save(existing);
    }

    @Override
    public Organization toggleActiveStatus(UUID orgId) {
        Organization organization = getOrganizationById(orgId);
        organization.setIsActive(!organization.getIsActive());
        return organizationRepository.save(organization);
    }

    @Override
    public void deleteOrganization(UUID orgId) {
        Organization organization = getOrganizationById(orgId);
        organizationRepository.delete(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByOrgCode(String orgCode) {
        return organizationRepository.existsByOrgCode(orgCode);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return organizationRepository.existsByEmail(email);
    }
}