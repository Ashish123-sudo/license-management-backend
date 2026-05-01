package com.example.licensemanagementbackend.service.impl;

import com.example.licensemanagementbackend.model.Application;
import com.example.licensemanagementbackend.model.User;
import com.example.licensemanagementbackend.repository.ApplicationRepository;
import com.example.licensemanagementbackend.repository.UserRepository;
import com.example.licensemanagementbackend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Application createApplication(Application application) {
        User existingUser = userRepository.findById(application.getProductOwner().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        application.setProductOwner(existingUser);
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application getApplicationById(UUID id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    @Override
    public List<Application> getApplicationsByOwnerId(UUID ownerId) {
        return applicationRepository.findByProductOwnerId(ownerId);
    }

    @Override
    public Application updateApplication(UUID id, Application details) {
        Application app = getApplicationById(id);
        app.setAppName(details.getAppName());
        app.setIsActive(details.getIsActive());

        User existingUser = userRepository.findById(details.getProductOwner().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        app.setProductOwner(existingUser);

        return applicationRepository.save(app);
    }

    @Override
    public void deleteApplication(UUID id) {
        Application app = getApplicationById(id);
        try {
            applicationRepository.delete(app);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(app.getAppName());
        }
    }
}