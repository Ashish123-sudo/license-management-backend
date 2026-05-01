package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByAppName(String appName);
    List<Application> findByAppNameContainingIgnoreCase(String appName);
    @Query("SELECT a FROM Application a WHERE a.productOwner.userId = :ownerId")
    List<Application> findByProductOwnerId(@Param("ownerId") UUID ownerId);
}