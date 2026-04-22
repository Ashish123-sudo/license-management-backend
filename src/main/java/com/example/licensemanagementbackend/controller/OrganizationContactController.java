package com.example.licensemanagementbackend.controller;

import com.example.licensemanagementbackend.model.OrganizationContact;
import com.example.licensemanagementbackend.service.OrganizationContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organization-contacts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrganizationContactController {

    private final OrganizationContactService organizationContactService;

    // POST /api/v1/organization-contacts?orgId={orgId}
    @PostMapping
    public ResponseEntity<OrganizationContact> createContact(
            @RequestParam UUID orgId,
            @RequestBody OrganizationContact contact) {
        OrganizationContact created = organizationContactService.createContact(orgId, contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/v1/organization-contacts
    @GetMapping
    public ResponseEntity<List<OrganizationContact>> getAllContacts() {
        return ResponseEntity.ok(organizationContactService.getAllContacts());
    }

    // GET /api/v1/organization-contacts/{contactId}
    @GetMapping("/{contactId}")
    public ResponseEntity<OrganizationContact> getContactById(@PathVariable UUID contactId) {
        return ResponseEntity.ok(organizationContactService.getContactById(contactId));
    }

    // GET /api/v1/organization-contacts/organization/{orgId}
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<OrganizationContact>> getContactsByOrganization(
            @PathVariable UUID orgId,
            @RequestParam(required = false) Boolean active) {
        List<OrganizationContact> contacts = (active != null && active)
                ? organizationContactService.getActiveContactsByOrganization(orgId)
                : organizationContactService.getContactsByOrganization(orgId);
        return ResponseEntity.ok(contacts);
    }

    // PUT /api/v1/organization-contacts/{contactId}
    @PutMapping("/{contactId}")
    public ResponseEntity<OrganizationContact> updateContact(
            @PathVariable UUID contactId,
            @RequestBody OrganizationContact contact) {
        return ResponseEntity.ok(organizationContactService.updateContact(contactId, contact));
    }

    // PATCH /api/v1/organization-contacts/{contactId}/toggle-status
    @PatchMapping("/{contactId}/toggle-status")
    public ResponseEntity<OrganizationContact> toggleActiveStatus(@PathVariable UUID contactId) {
        return ResponseEntity.ok(organizationContactService.toggleActiveStatus(contactId));
    }

    // DELETE /api/v1/organization-contacts/{contactId}
    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID contactId) {
        organizationContactService.deleteContact(contactId);
        return ResponseEntity.noContent().build();
    }
}