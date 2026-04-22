package com.example.licensemanagementbackend.controller;

import com.example.licensemanagementbackend.model.Role;
import com.example.licensemanagementbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * GET /api/roles - Get all roles
     */
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(@RequestParam(required = false) Boolean active) {
        List<Role> roles;

        if (active != null && active) {
            roles = roleService.getActiveRoles();
        } else {
            roles = roleService.getAllRoles();
        }

        return ResponseEntity.ok(roles);
    }

    /**
     * GET /api/roles/{id} - Get role by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") UUID roleId) {
        return roleService.getRoleById(roleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/roles/name/{name} - Get role by name
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/roles/search - Search roles by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Role>> searchRoles(@RequestParam String name) {
        List<Role> roles = roleService.searchRolesByName(name);
        return ResponseEntity.ok(roles);
    }

    /**
     * POST /api/roles - Create new role
     */
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.createRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /api/roles/{id} - Update role
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id") UUID roleId, @RequestBody Role role) {
        try {
            Role updatedRole = roleService.updateRole(roleId, role);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /api/roles/{id} - Delete role
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") UUID roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PATCH /api/roles/{id}/activate - Activate role
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateRole(@PathVariable("id") UUID roleId) {
        try {
            Role role = roleService.activateRole(roleId);
            return ResponseEntity.ok(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PATCH /api/roles/{id}/deactivate - Deactivate role
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateRole(@PathVariable("id") UUID roleId) {
        try {
            Role role = roleService.deactivateRole(roleId);
            return ResponseEntity.ok(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * GET /api/roles/exists/{name} - Check if role exists by name
     */
    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> checkRoleExists(@PathVariable String name) {
        boolean exists = roleService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}