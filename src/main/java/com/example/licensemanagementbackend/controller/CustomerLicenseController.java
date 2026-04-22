package com.example.licensemanagementbackend.controller;

import com.example.licensemanagementbackend.model.CustomerLicense;
import com.example.licensemanagementbackend.service.CustomerLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer-licenses")
@CrossOrigin(origins = "*")
public class CustomerLicenseController {

    @Autowired
    private CustomerLicenseService customerLicenseService;

    @PostMapping
    public ResponseEntity<CustomerLicense> assignLicense(@RequestBody CustomerLicense customerLicense) {
        return ResponseEntity.ok(customerLicenseService.assignLicense(customerLicense));
    }

    @GetMapping
    public ResponseEntity<List<CustomerLicense>> getAllCustomerLicenses() {
        return ResponseEntity.ok(customerLicenseService.getAllCustomerLicenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerLicense> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerLicenseService.getCustomerLicenseById(id));
    }

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<CustomerLicense>> getByOrg(@PathVariable UUID orgId) {
        return ResponseEntity.ok(customerLicenseService.getLicensesByOrg(orgId));
    }

    @GetMapping("/application/{appId}")
    public ResponseEntity<List<CustomerLicense>> getByApp(@PathVariable UUID appId) {
        return ResponseEntity.ok(customerLicenseService.getLicensesByApp(appId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerLicense> updateCustomerLicense(
            @PathVariable UUID id,
            @RequestBody CustomerLicense details) {
        return ResponseEntity.ok(customerLicenseService.updateCustomerLicense(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerLicense(@PathVariable UUID id) {
        customerLicenseService.deleteCustomerLicense(id);
        return ResponseEntity.ok("Customer license deleted successfully.");
    }
}