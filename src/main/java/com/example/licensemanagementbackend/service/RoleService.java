package com.example.licensemanagementbackend.service;

import com.example.licensemanagementbackend.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    /**
     * Get all roles
     */
    List<Role> getAllRoles();

    /**
     * Get all active roles
     */
    List<Role> getActiveRoles();

    /**
     * Get role by ID
     */
    Optional<Role> getRoleById(UUID roleId);

    /**
     * Get role by name
     */
    Optional<Role> getRoleByName(String name);

    /**
     * Create new role
     */
    Role createRole(Role role);


    Role updateRole(UUID roleId, Role role);


    void deleteRole(UUID roleId);


    Role activateRole(UUID roleId);


    Role deactivateRole(UUID roleId);

    List<Role> searchRolesByName(String name);


    boolean existsByName(String name);
}