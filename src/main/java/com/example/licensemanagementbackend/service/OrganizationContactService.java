package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.Organization;
import com.example.licensemanagementbackend.model.OrganizationContact;
import com.example.licensemanagementbackend.repository.OrganizationContactRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationContactService {

    private final OrganizationContactRepository organizationContactRepository;
    private final OrganizationService organizationService;

    public OrganizationContact createContact(UUID orgId, OrganizationContact contact) {
        Organization organization = organizationService.getOrganizationById(orgId);

        if (contact.getEmail() != null && organizationContactRepository.existsByEmail(contact.getEmail())) {
            throw new IllegalArgumentException(
                    "Contact with email '" + contact.getEmail() + "' already exists.");
        }

        contact.setOrganization(organization);
        return organizationContactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public OrganizationContact getContactById(UUID contactId) {
        return organizationContactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contact not found with id: " + contactId));
    }

    @Transactional(readOnly = true)
    public List<OrganizationContact> getAllContacts() {
        return organizationContactRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<OrganizationContact> getContactsByOrganization(UUID orgId) {
        return organizationContactRepository.findByOrganization_OrgId(orgId);
    }

    @Transactional(readOnly = true)
    public List<OrganizationContact> getActiveContactsByOrganization(UUID orgId) {
        return organizationContactRepository.findByOrganization_OrgIdAndIsActive(orgId, true);
    }

    public OrganizationContact updateContact(UUID contactId, OrganizationContact updated) {
        OrganizationContact existing = getContactById(contactId);

        if (updated.getEmail() != null &&
                !updated.getEmail().equals(existing.getEmail()) &&
                organizationContactRepository.existsByEmail(updated.getEmail())) {
            throw new IllegalArgumentException(
                    "Contact with email '" + updated.getEmail() + "' already exists.");
        }

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setDesignation(updated.getDesignation());
        existing.setRole(updated.getRole());
        existing.setEmail(updated.getEmail());
        existing.setMobile(updated.getMobile());
        existing.setOfficePhone(updated.getOfficePhone());
        existing.setExt(updated.getExt());
        existing.setFax(updated.getFax());
        existing.setLinkedIn(updated.getLinkedIn());
        existing.setIsActive(updated.getIsActive());

        return organizationContactRepository.save(existing);
    }

    public OrganizationContact toggleActiveStatus(UUID contactId) {
        OrganizationContact contact = getContactById(contactId);
        contact.setIsActive(!contact.getIsActive());
        return organizationContactRepository.save(contact);
    }

    public void deleteContact(UUID contactId) {
        OrganizationContact contact = getContactById(contactId);
        organizationContactRepository.delete(contact);
    }
}