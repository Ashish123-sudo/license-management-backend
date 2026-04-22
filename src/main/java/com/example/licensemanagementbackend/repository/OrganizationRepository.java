package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByOrgCode(String orgCode);

    Optional<Organization> findByEmail(String email);

    List<Organization> findByIsActive(Boolean isActive);

    List<Organization> findByCountry(String country);

    List<Organization> findByIndustry(String industry);

    boolean existsByOrgCode(String orgCode);

    boolean existsByEmail(String email);
}