package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.OrganizationContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationContactRepository extends JpaRepository<OrganizationContact, UUID> {

    List<OrganizationContact> findByOrganization_OrgId(UUID orgId);

    List<OrganizationContact> findByOrganization_OrgIdAndIsActive(UUID orgId, Boolean isActive);

    List<OrganizationContact> findByIsActive(Boolean isActive);

    List<OrganizationContact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    Optional<OrganizationContact> findByEmail(String email);

    boolean existsByEmail(String email);
}