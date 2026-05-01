package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.Application;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    Application createApplication(Application application);
    List<Application> getAllApplications();
    Application getApplicationById(UUID id);
    Application updateApplication(UUID id, Application applicationDetails);
    void deleteApplication(UUID id);
    List<Application> getApplicationsByOwnerId(UUID ownerId);
}