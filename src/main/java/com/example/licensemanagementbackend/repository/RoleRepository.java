package com.example.licensemanagementbackend.repository;

import com.example.licensemanagementbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {


    Optional<Role> findByName(String name);


    List<Role> findByIsActiveTrue();


    List<Role> findByIsActiveFalse();


    boolean existsByName(String name);


    List<Role> findByNameContainingIgnoreCase(String name);
}