package com.example.licensemanagementbackend.controller;

import com.example.licensemanagementbackend.model.Organization;
import com.example.licensemanagementbackend.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    // POST /api/v1/organizations
    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        Organization created = organizationService.createOrganization(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/v1/organizations
    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations(
            @RequestParam(required = false) Boolean active) {
        List<Organization> organizations = (active != null && active)
                ? organizationService.getActiveOrganizations()
                : organizationService.getAllOrganizations();
        return ResponseEntity.ok(organizations);
    }

    // GET /api/v1/organizations/{orgId}
    @GetMapping("/{orgId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable UUID orgId) {
        return ResponseEntity.ok(organizationService.getOrganizationById(orgId));
    }

    // GET /api/v1/organizations/code/{orgCode}
    @GetMapping("/code/{orgCode}")
    public ResponseEntity<Organization> getOrganizationByCode(@PathVariable String orgCode) {
        return ResponseEntity.ok(organizationService.getOrganizationByCode(orgCode));
    }

    // PUT /api/v1/organizations/{orgId}
    @PutMapping("/{orgId}")
    public ResponseEntity<Organization> updateOrganization(
            @PathVariable UUID orgId,
            @RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.updateOrganization(orgId, organization));
    }

    // PATCH /api/v1/organizations/{orgId}/toggle-status
    @PatchMapping("/{orgId}/toggle-status")
    public ResponseEntity<Organization> toggleActiveStatus(@PathVariable UUID orgId) {
        return ResponseEntity.ok(organizationService.toggleActiveStatus(orgId));
    }

    // DELETE /api/v1/organizations/{orgId}
    @DeleteMapping("/{orgId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable UUID orgId) {
        organizationService.deleteOrganization(orgId);
        return ResponseEntity.noContent().build();
    }

    // GET /api/v1/organizations/exists/code/{orgCode}
    @GetMapping("/exists/code/{orgCode}")
    public ResponseEntity<Boolean> existsByOrgCode(@PathVariable String orgCode) {
        return ResponseEntity.ok(organizationService.existsByOrgCode(orgCode));
    }

    // GET /api/v1/organizations/exists/email/{email}
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(organizationService.existsByEmail(email));
    }
}