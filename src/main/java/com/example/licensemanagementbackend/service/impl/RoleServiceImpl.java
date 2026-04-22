package com.example.licensemanagementbackend.service.impl;

import com.example.licensemanagementbackend.model.Role;
import com.example.licensemanagementbackend.repository.RoleRepository;
import com.example.licensemanagementbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getActiveRoles() {
        return roleRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getRoleById(UUID roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role createRole(Role role) {
        // Check if role with same name already exists
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role with name '" + role.getName() + "' already exists");
        }

        // Set default active status if not provided
        if (role.getIsActive() == null) {
            role.setIsActive(true);
        }

        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(UUID roleId, Role role) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        // Check if name is being changed and if new name already exists
        if (!existingRole.getName().equals(role.getName()) &&
                roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role with name '" + role.getName() + "' already exists");
        }

        // Update fields
        existingRole.setName(role.getName());
        existingRole.setDescription(role.getDescription());

        if (role.getIsActive() != null) {
            existingRole.setIsActive(role.getIsActive());
        }

        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(UUID roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("Role not found with ID: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role activateRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        role.setIsActive(true);
        return roleRepository.save(role);
    }

    @Override
    public Role deactivateRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        role.setIsActive(false);
        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> searchRolesByName(String name) {
        return roleRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}